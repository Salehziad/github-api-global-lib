def call(Map config = [: ]) {
  pipeline {
    agent any
    environment {
      BRANCH_NAME = "${GIT_BRANCH.replace('origin/','')}"
    }
    stages {
      stage('Set Environment Variables') {
        steps {
          script {
            if (env.BRANCH_NAME == 'develop') {
              env.ENVIRONMENT = 'dev server'
              env.CHANNEL='#development-jenkins'
            } else if (env.BRANCH_NAME == 'test') {
              env.ENVIRONMENT = 'test server'
            }
          }
        }
      }
      stage("Deliver for deploy")  {
        steps {
          echo("I am in build")
          sshPublisher(
            continueOnError: false, failOnError: true,
            publishers: [
              sshPublisherDesc(
                configName: ENVIRONMENT,
                verbose: true,
                transfers: [
                  sshTransfer(
                    execCommand: " rm -rf /var/www/${config.name}"
                  ),
                  sshTransfer(
                    sourceFiles: "**/*",
                    remoteDirectory: "${config.name}",
                    execCommand: "cd /var/www/${config.name} && sudo npm i"

                  ),
                ])
            ])
          echo("I am in Deploy")
          sshPublisher(
            continueOnError: false, failOnError: true,
            publishers: [
              sshPublisherDesc(
                configName: ENVIRONMENT,
                verbose: true,
                transfers: [
                  sshTransfer(
                    execCommand: "cd /var/www/${config.name} && pm2 start"
                  )

                ])
            ])
        }
      }
    }
    post {
        always {
           slackSend channel: CHANNEL, message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Started . ", color: "good"
        }
        success {
          slackSend channel: CHANNEL, message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Build Finshed Success . ", color: "good"
        }
        unstable {
           slackSend channel: CHANNEL, message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Build have Error  . ", color: "warning"
        }
        failure {
            slackSend channel: CHANNEL, message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Failure Build  . ", color: "danger"
        }
        changed {
             echo 'Things were different before...'
        }
    }
  }

}
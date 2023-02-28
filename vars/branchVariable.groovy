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
            } else if (env.BRANCH_NAME == 'docker') {
              env.ENVIRONMENT = 'dev server'
            }
          }
        }
      }
      stage("Deliver for ${ENVIRONMENT}") {
        steps {
          echo("I am in build")
          sshPublisher(
            continueOnError: false, failOnError: true,
            publishers: [
              sshPublisherDesc(
                configName: "${ENVIRONMENT}",
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
                configName: "${ENVIRONMENT}",
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
  }

}
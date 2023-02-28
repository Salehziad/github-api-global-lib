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
                            sourceFiles: "**/*",
                            remoteDirectory: "ciam",
                            execCommand:"cd /var/www/ciam && docker build . -t saleh99/ciam --no-cache"
                        ),
                        sshTransfer(
                            execCommand: "docker push saleh99/ciam"
                        ),
                ])
            ])
        //   echo("I am in Deploy")
        //   sshPublisher(
        //     continueOnError: false, failOnError: true,
        //     publishers: [
        //       sshPublisherDesc(
        //         configName: ENVIRONMENT,
        //         verbose: true,
        //         transfers: [
        //           sshTransfer(
        //             execCommand: "cd /var/www/${config.name} && pm2 start"
        //           )

        //         ])
        //     ])
        // }
      }
    }
  }

}
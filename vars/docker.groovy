// def call(Map config = [:]) {
//     pipeline {
//     agent any
//       options {
//         ansiColor('xterm')
//     }
//     environment {
//     DOCKERHUB_CREDENTIALS = credentials('dockerHub')
//     }
//     stages {
//         stage('Deliver for development') {
//             when {
//                 branch 'release_4'
//             }
//             steps {
//                 echo("I am in build")
//                 sshPublisher(
//                     continueOnError: false, failOnError: true,
//                     publishers: [
//                     sshPublisherDesc(
//                         configName: "dev server",
//                         verbose: true,
//                         transfers: [
//                         // sshTransfer(
//                         //     execCommand:"docker image rm saleh99/ciam:latest"
//                         // ),
//                         sshTransfer(
//                             sourceFiles: "**/*",
//                             remoteDirectory: "ciam",
//                             execCommand:"cd /var/www/ciam && docker build . -t saleh99/ciam --no-cache"
//                         ),
//                         sshTransfer(
//                             execCommand: "docker push saleh99/ciam"
//                         ),
//                         // sshTransfer(
//                         //     execCommand: "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin && docker push saleh99/ciam"
//                         // ),
//                         // sshTransfer(
//                         //     sourceFiles: "**/*",
//                         //     remoteDirectory: "${config.name}",
//                         //     execCommand:"cd /var/www/${config.name} && sudo npm i"
                           
//                         // ),
//                     ])
//                 ])
//             }
//         }a
//     }
// }
// }


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
            if (env.BRANCH_NAME == 'docker') {
              env.ENVIRONMENT = 'dev server'
            } else if (env.BRANCH_NAME == 'test') {
              env.ENVIRONMENT = 'test server'
            }
          }
        }
      }
      stage("Deliver for deploy")  {
        steps {
          echo("I am in build ${ENVIRONMENT}")
        //   sshPublisher(
        //     continueOnError: false, failOnError: true,
        //     publishers: [
        //       sshPublisherDesc(
        //         configName: ENVIRONMENT,
        //         verbose: true,
        //         transfers: [
        //           sshTransfer(
        //             execCommand: " rm -rf /var/www/${config.name}"
        //           ),
        //           sshTransfer(
        //             sourceFiles: "**/*",
        //             remoteDirectory: "${config.name}",
        //             execCommand: "cd /var/www/${config.name} && sudo npm i"

        //           ),
        //         ])
        //     ])
      }
    }
  }

}
}
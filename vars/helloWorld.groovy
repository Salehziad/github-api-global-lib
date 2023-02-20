// def call(Map config = [:]) {
//     sh "echo Hello ${config.name}. Today is config.day}."
// }

// def call(String name,String day) {
//     sh "echo Hello $name. Today is $day"
// }

// def call(String name) {
//     sh "Hello, $name!"

// }



def call(Map config = [:]) {
    pipeline {
    agent any
      options {
        ansiColor('xterm')
    }
    stages {
        stage('Deliver for development') {
            when {
                branch 'release_4'
            }
            steps {
                echo("I am in Test")
            }
            // steps {
            //     echo("I am in build")
            //     sshPublisher(
            //         continueOnError: false, failOnError: true,
            //         publishers: [
            //         sshPublisherDesc(
            //             configName: "dev server",
            //             verbose: true,
            //             transfers: [
            //             sshTransfer(
            //                 execCommand: " rm -rf /var/www/${config.name}"
            //             ),
            //             sshTransfer(
            //                 sourceFiles: "**/*",
            //                 remoteDirectory: "${config.name}",
            //                 execCommand:"cd /var/www/${config.name} && sudo npm i"
                           
            //             ),
            //         ])
            //     ])
            // }
            // steps {
            //     echo("I am in Deploy")
            //     sshPublisher(
            //         continueOnError: false, failOnError: true,
            //         publishers: [
            //         sshPublisherDesc(
            //             configName: "dev server",
            //             verbose: true,
            //             transfers: [
            //              sshTransfer(
            //                      execCommand: "cd /var/www/${config.name} && pm2 start"
            //              )
                     
            //         ])
            //     ])
            // }
        }
    }
     post {
        always {
           slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Started . ", color: "good"
        }
        success {
          slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Build Finshed Success . ", color: "good"
        }
        unstable {
           slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Build have Error  . ", color: "warning"
        }
        failure {
            slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Failure Build  . ", color: "danger"
        }
        changed {
             echo 'Things were different before...'
        }
    }
}

}
// def call(Map config = [:]) {
//     pipeline {
//     agent any
//       options {
//         ansiColor('xterm')
//     }
//     stages {
//         stage('Build') {
//             steps {
//                 echo("I am in build")
//                 sshPublisher(
//                     continueOnError: false, failOnError: true,
//                     publishers: [
//                     sshPublisherDesc(
//                         configName: "dev server",
//                         verbose: true,
//                         transfers: [
//                         sshTransfer(
//                             execCommand: " rm -rf /var/www/${config.name}"
//                         ),
//                         sshTransfer(
//                             sourceFiles: "**/*",
//                             remoteDirectory: "${config.name}",
//                             execCommand:"cd /var/www/${config.name} && sudo npm i"
                           
//                         ),
//                     ])
//                 ])
//             }
//         }
//         stage('Test') {
//             steps {
//                 echo("I am in Test")
//             }
//         }
//         stage('Deploy') {
//             steps {
//                 echo("I am in Deploy")
//                 sshPublisher(
//                     continueOnError: false, failOnError: true,
//                     publishers: [
//                     sshPublisherDesc(
//                         configName: "dev server",
//                         verbose: true,
//                         transfers: [
//                          sshTransfer(
//                                  execCommand: "cd /var/www/${config.name} && pm2 start"
//                          )
                     
//                     ])
//                 ])
//             }
//         }
//     }
//      post {
//         always {
//            slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Started . ", color: "good"
//         }
//         success {
//           slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Build Finshed Success . ", color: "good"
//         }
//         unstable {
//            slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Build have Error  . ", color: "warning"
//         }
//         failure {
//             slackSend channel: "#development-jenkins", message: "${env.JOB_NAME}  - #${env.BUILD_NUMBER} : Failure Build  . ", color: "danger"
//         }
//         changed {
//              echo 'Things were different before...'
//         }
//     }
// }

// }



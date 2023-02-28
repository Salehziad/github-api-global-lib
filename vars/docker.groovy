// pipeline {
//   agent any
//   options {
//     buildDiscarder(logRotator(numToKeepStr: '5'))
//   }
//   environment {
//     DOCKERHUB_CREDENTIALS = credentials('dockerhub')
//   }
//   stages {
//     stage('Build') {
//       steps {
//         sh 'docker build . -t saleh99/ciam --no-cache'
//       }
//     }
//     stage('Login') {
//       steps {
//         sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
//       }
//     }
//     stage('Push') {
//       steps {
//         sh 'docker push lloydmatereke/jenkins-docker-hub'
//       }
//     }
//   }
//   post {
//     always {
//       sh 'docker logout'
//     }
//   }
// }

def call(Map config = [:]) {
    pipeline {
    agent any
      options {
        ansiColor('xterm')
    }
    environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerHub')
    }
    stages {
        stage('Deliver for development') {
            when {
                branch 'release_4'
            }
            // steps {
            //     script {
            //         def git_tag = sh(returnStdout: true, script: "git describe --abbrev=0 --tags").trim()
            //         echo "Latest tag on the current branch: ${git_tag}"
            //     }
            // }
            steps {
                echo("I am in build")
                                script {
    TAG = sh (
      returnStdout: true,
      script: 'git fetch --tags && git tag --points-at HEAD | awk NF'
    ).trim()
                }
                sshPublisher(
                    continueOnError: false, failOnError: true,
                    publishers: [
                    sshPublisherDesc(
                        configName: "dev server",
                        verbose: true,
                        transfers: [

                        sshTransfer(
                            sourceFiles: "**/*",
                            remoteDirectory: "ciam",
                            execCommand: "cd /var/www/ciam && git rev-list --tags --max-count=1"
                        ),
                        sshTransfer(
                            execCommand:"cd /var/www/ciam && docker build . -t saleh99/ciam --no-cache"
                        ),
                        // sshTransfer(
                        //     execCommand: "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin && docker push saleh99/ciam"
                        // ),
                        // sshTransfer(
                        //     sourceFiles: "**/*",
                        //     remoteDirectory: "${config.name}",
                        //     execCommand:"cd /var/www/${config.name} && sudo npm i"
                           
                        // ),
                    ])
                ])
            }
        }
    }
}
}
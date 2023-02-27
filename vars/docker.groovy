pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr: '5'))
  }
  environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub')
  }
  stages {
    stage('Build') {
      steps {
        sh 'docker build . -t saleh99/ciam --no-cache'
      }
    }
    stage('Login') {
      steps {
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
      }
    }
    stage('Push') {
      steps {
        sh 'docker push lloydmatereke/jenkins-docker-hub'
      }
    }
  }
  post {
    always {
      sh 'docker logout'
    }
  }
}

def call(Map config = [:]) {
    pipeline {
    agent any
      options {
        ansiColor('xterm')
    }
    environment {
    DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }
    stages {
        stage('Deliver for development') {
            when {
                branch 'release_4'
            }
                 steps {
        sh 'docker build . -t saleh99/ciam --no-cache'
      }
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
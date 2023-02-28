def call(Map config = [: ]) {
  pipeline {
    agent {
      label 'docker'
    }
    environment {
      REPO_URL = 'www.my.repo'
    }
    stages {
      stage('build') {
        steps {
          sh 'env | grep REPO_URL'
        }
      }
    }
  }

}
def call(Map config = [: ]) {
  pipeline {
  agent any
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
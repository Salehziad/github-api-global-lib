def call(Map config = [: ]) {
  pipeline {
    agent any
    environment {
      BRANCH_NAME = "${GIT_BRANCH}"
    }
    stages {
      stage('Build') {
        steps {
          echo "The branch name is ${BRANCH_NAME}"
          // Add build steps here
        }
      }
    }
  }

}
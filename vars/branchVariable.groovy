def call(Map config = [: ]) {
pipeline {
    agent any
    environment {
        if (BRANCH_NAME == 'docker') {
            ENVIRONMENT = 'docker'
        } else {
            ENVIRONMENT = 'dev'
        }
    }
    stages {
        stage('Build') {
            steps {
                echo "Building branch ${BRANCH_NAME} for environment ${ENVIRONMENT}"
                // Add build steps here
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying branch ${BRANCH_NAME} to environment ${ENVIRONMENT}"
                // Add deployment steps here
            }
        }
    }
}

}
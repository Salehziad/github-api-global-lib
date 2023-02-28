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
                        env.ENVIRONMENT = 'dev'
                    } else {
                        env.ENVIRONMENT = 'prod'
                    }
                }
            }
        }
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
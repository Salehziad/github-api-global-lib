def call(Map config = [: ]) {
pipeline {
    agent any
    environment {
        BRANCH_NAME = "${GIT_BRANCH.replace('origin/','')}"
    if ( 'origin/production'== 'origin/production') {
        ENVIRONMENT = 'prod';
    } else {
        ENVIRONMENT = 'dev';
    }
    }
    stages {
        stage('Build') {
            steps {
                echo "The branch name is ${ENVIRONMENT}"
                // Add build steps here
            }
        }
    }
}


}
def call() {
pipeline {
    agent any
    stages {
        stage('Checkout repository') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: 'release_4']], userRemoteConfigs: [[url: 'https://github.com/agents-on-cloud/api.ciam.agentsoncloud.com.git']]])
            }
        }
        stage('Get latest tag') {
            steps {
                script {
                    def git_tag = sh(returnStdout: true, script: "git describe --abbrev=0 --tags").trim()
                    echo "Latest tag on the main branch: ${git_tag}"
                }
            }
        }
    }
}
}

def call() {
    pipeline {
    agent any
    stages {
        stage('Get latest tag') {
            steps {
                script {
                    def git_tag = sh(returnStdout: true, script: "git describe --abbrev=0 --tags").trim()
                    echo "Latest tag on the current branch: ${git_tag}"
                }
            }
        }
    }
}
}

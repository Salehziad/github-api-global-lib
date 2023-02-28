def call() {
    TAG = sh (
      returnStdout: true,
      script: 'git fetch --tags && git tag --points-at HEAD | awk NF'
    ).trim()
}

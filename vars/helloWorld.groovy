def call(Map config = [:]) {
    sh "echo Hello ${config.name}. Today is ${config.day}."
}

// def call(String name,String day) {
//     sh "echo Hello $name. Today is $day"
// }

// def call(String name) {
//     sh "Hello, $name!"

// }
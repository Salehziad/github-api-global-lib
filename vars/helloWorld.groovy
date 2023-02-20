// def call(Map config = [:]) {
//     //sh "echo Hello ${config.name}. Today is ${config.dayOfWeek}."
//     sh 'echo hello world'
// }

def call(String name,String day) {
    sh 'echo Hello $name. Today is $day'
}
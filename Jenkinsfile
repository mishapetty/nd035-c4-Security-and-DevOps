pipeline {
    agent { none { image 'maven:3.3.3' } }
    stages {
        stage('build') {
            steps {
                bat 'mvn --version'
            }
        }
    }
}
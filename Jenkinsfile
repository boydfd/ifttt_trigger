pipeline {
    agent {
        docker {
            image 'java:8-jdk-alpine'
        }
    }
    stages {
        stage('test') {
            steps {
                sh 'echo "Hello World"'
                sh './gradlew check'
            }
        }
    }
}
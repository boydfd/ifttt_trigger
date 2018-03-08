pipeline {
    agent {
        docker {
            image 'java:8-jdk-alpine'
            args '-v /home/jenkins/.gradle:/root/.gradle'
        }
    }
    stages {
        stage('Test') {
            steps {
                sh 'echo "Hello World"'
                sh './gradlew check'
            }
            post {
                always {
                    junit 'build/test-results/**/*.xml'
                    checkstyle pattern: 'build/reports/checkstyle/*.xml'
                }
            }
        }
        stage('Build') {
            steps {
                sh './gradlew pushToDockerRegistry'
            }
        }
    }
}
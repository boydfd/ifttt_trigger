#!groovy​
pipeline {
    agent none
    stages {
        stage('Test') {
            agent {
                docker {
                    image 'java:8-jdk-alpine'
                    args '-v /home/jenkins/.gradle:/root/.gradle'
                }
            }
            steps {
                sh 'echo "Hello World"'
                sh './gradlew check'
            }
            post {
                always {
                    junit 'build/test-results/**/*.xml'
                    checkstyle pattern: 'build/reports/checkstyle/*.xml'
                    archiveArtifacts artifacts: './build/libs/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker') {
            agent {
                docker {
                    image 'docker:stable'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                sh 'ls build'
            }
        }
    }
}
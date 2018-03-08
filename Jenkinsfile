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
                sh './gradlew clean build'
            }
            post {
                always {
                    junit 'build/test-results/**/*.xml'
                    checkstyle pattern: 'build/reports/checkstyle/*.xml'
                    archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
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
                step([  $class: 'CopyArtifact',
                        filter: 'build/libs/*.jar',
                        fingerprintArtifacts: true,
                        projectName: '${JOB_NAME}',
                        selector: [$class: 'SpecificBuildSelector', buildNumber: '${BUILD_NUMBER}']
                ])

                sh 'cp archive_new/*.jar docker/app.jar'
                sh 'docker/build.sh app.jar'
            }
        }
    }
}
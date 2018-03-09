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
                step([$class              : 'CopyArtifact',
                      filter              : 'build/libs/*.jar',
                      fingerprintArtifacts: true,
                      projectName         : '${JOB_NAME}',
                      selector            : [$class: 'SpecificBuildSelector', buildNumber: '${BUILD_NUMBER}']
                ])

                sh 'ls -l'
                sh 'ls -l build'
                sh 'cp build/libs/*.jar docker/app.jar'
                sh 'docker/build.sh app.jar'
            }
        }

        stage('Deploy') {
            agent {
                docker {
                    image 'governmentpaas/git-ssh'
                    args '-v /var/jenkins_home/.ssh:/root/.ssh'
                }
            }
            steps {
//                step([
//                        $class : 'SshPublisher',
//                        publishers: [
//                        sshPublisherDesc(
//                                configName: 'swpsci06',
//                                transfers: [sshTransfer(
//                                        execCommand: 'echo 111111111111',
//                                        execTimeout: 120000,
//                                        sourceFiles: '')],
//                                usePromotionTimestamp: false,
//                                useWorkspaceInPromotion: false,
//                                verbose: false)]
//                ])
                sshPublisher(publishers: [
                        sshPublisherDesc(
                                configName: 'swpsci06',
                                transfers: [sshTransfer(
                                        execCommand: 'echo 111111111111',
                                        execTimeout: 120000,
                                        sourceFiles: '')],
                                usePromotionTimestamp: false,
                                useWorkspaceInPromotion: false,
                                verbose: false)])
                sh 'ls ~/.ssh'
                sh 'ssh -o StrictHostKeyChecking=no rlin@192.168.42.10 mkdir 1111111111111'
            }
        }
    }
}
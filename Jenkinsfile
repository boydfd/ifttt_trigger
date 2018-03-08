pipeline {
    agent {
        docker {
            image 'java:8-jdk-alpine'
            args ['-v $HOME/.m2:/root/.m2']
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
pipeline {
    agent any

    tools {
        maven 'Maven 3'   // Make sure this matches the Maven installation in Jenkins
        jdk 'JDK 17'      // Adjust based on your JDK version
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://your-git-repo-url.git', branch: 'main'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                archiveArtifacts artifacts: 'target/site/jacoco/*', fingerprint: true
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}


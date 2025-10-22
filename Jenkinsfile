pipeline {
    agent any
    triggers {
        githubPush()  // Triggers build on push events
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'webhook',
                    url: 'https://github.com/pooja22796/devops.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Running build for webhook branch...'
                sh 'ls -l'
            }
        }
    }
}


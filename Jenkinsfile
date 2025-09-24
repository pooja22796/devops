pipeline {
    agent any
    tools {
        git 'Default' 
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/pooja22796/devops.git'
            }
        }
        stage('Build') {
            steps {
                echo "First time build. Skipping changelog."
            }
        }
    }
}


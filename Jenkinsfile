pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev',
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


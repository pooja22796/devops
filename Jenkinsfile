pipeline {
    agent { label 'linuxgit' }

    options {
        timestamps()
    }

    environment {
        GIT_REPO = 'https://github.com/pooja22796/devops.git'
        BRANCH = 'cmake'
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo '[🧹] Cleaning workspace...'
                deleteDir()
            }
        }

        stage('Clone Repo') {
            steps {
                echo "[📥] Cloning branch '${BRANCH}' from GitHub..."
                git(branch: "${BRANCH}", url: "${GIT_REPO}")
            }
        }

        stage('Build Firmware') {
            steps {
                echo "[⚙️] Preparing build script..."
                sh 'dos2unix build.sh || true'
                sh 'chmod +x build.sh'
                echo "[🚀] Running build.sh..."
                sh './build.sh'
                echo "[✅] build.sh finished"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {  // This name must match the one in Manage Jenkins > Configure System > SonarQube Servers
                    sh 'sonar-scanner'
                }
            }
        }
    }
}

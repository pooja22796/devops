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

        // 🔽 New Stage: Upload .bin to Artifactory
        stage('Push .bin to Artifactory') {
            steps {
                script {
                    def server = Artifactory.server('trial-artifactory') // Match the Server ID
                    def uploadSpec = """{
                        "files": [
                            {
                                "pattern": "build/output/*.bin",
                                "target": "generic-local/builds/${env.JOB_NAME}/${env.BUILD_NUMBER}/"
                            }
                        ]
                    }"""
                    sh 'ls -lh build/output || echo "No .bin files found!"'
                    server.upload(uploadSpec)
                    echo "[📦] Uploaded .bin files to Artifactory."
                }
            }
        }

        stage('Check sonar-scanner') {
            steps {
                echo '[🔍] Checking sonar-scanner availability...'
                sh 'which sonar-scanner || echo "sonar-scanner NOT found in PATH!"'
                sh 'sonar-scanner --version || echo "Failed to run sonar-scanner"'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                echo '[📊] Running SonarQube analysis...'
                withSonarQubeEnv('SonarQube') {
                    sh '/opt/sonar-scanner/bin/sonar-scanner || echo "SonarQube scan failed!"'
                }
            }
        }
    }

    post {
        success {
            echo '[✅] Pipeline completed successfully.'
        }
        failure {
            echo '[❌] Pipeline failed. Check logs for errors.'
        }
        always {
            echo '[ℹ️] Pipeline execution finished.'
        }
    }
}

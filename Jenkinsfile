pipeline {
    agent { label 'linuxgit' }

    environment {
        GIT_REPO = 'https://github.com/pooja22796/devops.git'
        BRANCH = 'cmake'
    }

    stages {
        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace'
                deleteDir()
            }
        }

        stage('Clone Repo') {
            steps {
                echo "Cloning the repo from GitHub..."
                git(
                    branch: "${BRANCH}",
                    url: "${GIT_REPO}"
                )
            }
        }

        stage('Build Firmware') {
            steps {
                sh 'dos2unix build.sh'
                sh 'chmod +x build.sh'
                sh './build.sh'
            }
        }
    }

    post {
        success {
            echo '✅ Build succeeded!'
        }

        unstable {
            echo '⚠️ Build marked as UNSTABLE!'
            emailext(
                subject: "Build Unstable: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """
                    <p>Build marked <b>UNSTABLE</b> for <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>
                    <p>Check console output: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'yourteam@example.com, qa@example.com'
            )
        }

        failure {
            echo '❌ Build failed!'
            emailext(
                subject: "Build Failed: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """
                    <p><b>Build FAILED</b> in job <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>
                    <p>See console output: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'yourteam@example.com, dev@example.com'
            )
        }

        always {
            echo '🧹 Final cleanup (if needed)'
        }
    }
}


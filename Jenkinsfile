pipeline {
    // 🖥️ Define which Jenkins agent (node) will run this pipeline
    agent { label 'linuxgit' }

    // ⏱️ Enable timestamps in the console output for easier debugging
    options {
        timestamps()
    }

    // 🌐 Define environment variables used throughout the pipeline
    environment {
        GIT_REPO = 'https://github.com/pooja22796/devops.git'  // GitHub repository URL
        BRANCH = 'cmake'  // Branch to clone
    }

    stages {
        // 🧹 Clean the workspace before starting
        stage('Clean Workspace') {
            steps {
                echo '[🧹] Cleaning workspace...'
               //deleteDir()  // Deletes all files in the workspace (fresh start)
            }
        }

        // 📥 Clone the repository from GitHub
        stage('Clone Repo') {
            steps {
                echo "[📥] Cloning branch '${BRANCH}' from GitHub..."
                git(
                    branch: "${BRANCH}",  // Use specified branch
                    url: "${GIT_REPO}"    // Use specified repository
                )
            }
        }

        // ⚙️ Prepare and build the firmware using build.sh
        stage('Build Firmware') {
            steps {
                echo "[⚙️] Preparing build script..."

                // 🔄 Convert Windows line endings to Unix format (just in case)
                sh 'dos2unix build.sh'

                // ✅ Make the build script executable
                sh 'chmod +x build.sh'

                // 🚀 Run the build script and measure how long it takes
                echo "[🚀] Starting build.sh"
                sh 'time ./build.sh'
                echo "[✅] Finished build.sh"
            }
        }
    }

    // 📧 Post-build actions (notifications and logs)
    post {
        // ✅ If the build was successful
        success {
            echo '✅ Build succeeded!'
            emailext(
                subject: "✅ Build Success: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """
                    <p><b>Build SUCCESSFUL</b> for job <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>
                    <p>Check console output: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'pooja232shetty@gmail.com'  // 📧 Recipient email
            )
        }

        // ⚠️ If the build is unstable (e.g., test failures but not a crash)
        unstable {
            echo '⚠️ Build marked as UNSTABLE!'
            emailext(
                subject: "⚠️ Build Unstable: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """
                    <p>Build marked <b>UNSTABLE</b> for <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>
                    <p>Check console output: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'pooja232shetty@gmail.com'
            )
        }

        // ❌ If the build failed completely
        failure {
            echo '❌ Build failed!'
            emailext(
                subject: "❌ Build Failed: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """
                    <p><b>Build FAILED</b> in job <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>
                    <p>See console output: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                """,
                to: 'pooja232shetty@gmail.com'
            )
        }

        // 🔚 Always runs regardless of success/failure
        always {
            echo '🧼 Final cleanup done.'
        }
    }
}

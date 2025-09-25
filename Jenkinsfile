pipeline {
    agent { label 'linuxgit' }

    environment {
        GIT_REPO = 'https://github.com/pooja22796/devops.git'
        BRANCH = 'cmake'
    }
    
    stages {
        stage('Clean Workspace') {
            steps {
                echo 'CLeaning workspace'
                deleteDir()
            }
        }
        stage('Lint') {
            steps {
                echo "Cloning the repo from Github ........."
                git branch: "${BRANCH}",
                    url: "${GIT_REPO}"
            }
        }
        stage('Build') {
            steps {
                sh 'dos2unix build.sh'
                sh 'chmod +x build.sh'
                sh 'bash build.sh'
            }
        }
    }
}
post {
        always {
        unstable {
            echo 'Build marked as UNSTABLE!'
            emailext (
                subject: "Build Unstable: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """<p>Build became <b>UNSTABLE</b> in job <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        failure {
            echo 'Build failed!'
            emailext (
                subject: "Build Failed: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]",
                body: """<p>Build failed in job <b>${env.JOB_NAME}</b> [#${env.BUILD_NUMBER}]</p>""",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }

pipeline {
    agent none
    parameters {
        string(name: 'BRANCH', defaultValue: 'dev', description: 'Git branch to build')
    }
    stages {
        stage('Clone') {
            agent { label 'linuxgit' }
            steps {
                git branch: "${params.BRANCH}", url: 'https://github.com/pooja22796/devops.git'
            }
        }
        stage('Build') {
            agent { label 'linuxgit' }
            steps {
                 sh 'mvn clean install'
            }
        }
    }
}  

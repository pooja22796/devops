pipeline {
    agent { label 'linuxgit' }

    environment {
        GIT_REPO = 'https://github.com/pooja22796/devops.git'
        BRANCH = 'cmake'

        // SonarCloud Configuration
        SONARQUBE_ENV = 'SonarCloud'
        SONAR_ORGANIZATION = 'pooja22796'
        SONAR_PROJECT_KEY = 'pooja22796_devops'
    }

    stages {
        stage('Prepare Tools') {
            steps {
                echo 'Installing required tools...'
                sh '''
                    if ! command -v pip3 &>/dev/null; then
                        sudo yum install -y python3 python3-pip || true
                    fi

                    pip3 install --quiet cmakelint

                    if ! command -v dos2unix &>/dev/null; then
                        sudo yum install -y dos2unix || true
                    fi

                    if ! command -v cmake &>/dev/null; then
                        sudo yum install -y epel-release || true
                        sudo yum install -y cmake || true
                    fi

                    if ! command -v gcc &>/dev/null; then
                        sudo yum install -y gcc gcc-c++ || true
                    fi
                '''
            }
        }

        stage('Lint') {
            steps {
                echo 'Running lint checks on main.c...'
                sh '''
                    if [ -f src/main.c ]; then
                        cmakelint src/main.c > lint_report.txt
                    else
                        echo "main.c not found!"
                        exit 1
                    fi
                '''
            }
            post {
                always {
                    archiveArtifacts artifacts: 'lint_report.txt', fingerprint: true
                    fingerprint 'main.c'
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Running build with CMake...'
                sh '''
                    if [ -f CMakeLists.txt ]; then
                        mkdir -p build
                        cd build
                        cmake -DCMAKE_EXPORT_COMPILE_COMMANDS=ON ..
                        make -j$(nproc)
                        cp compile_commands.json ..
                    else
                        echo "CMakeLists.txt not found!"
                        exit 1
                    fi
                '''
            }
        }
        stage('Upload to Artifactory') {
            steps {
                withCredentials([string(credentialsId: 'jfrogjenkins', variable: 'ACCESS_TOKEN')]) {
                    sh '''
                        ARTIFACT_PATH="build/myfirmware.bin"
                        ARTIFACT_NAME=$(basename $ARTIFACT_PATH)

                        if [ ! -f "$ARTIFACT_PATH" ]; then
                            echo " Artifact file not found at $ARTIFACT_PATH"
                            exit 1
                        fi

                        # Deploy to repo root (works reliably)
                        UPLOAD_URL="https://trial9qz1lf.jfrog.io/artifactory/generic-local/build/$ARTIFACT_NAME"
                        echo "Uploading $ARTIFACT_NAME to $UPLOAD_URL ..."

                        HTTP_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" \
                            -H "Authorization: Bearer ${ACCESS_TOKEN}" \
                            -T "$ARTIFACT_PATH" \
                            "$UPLOAD_URL")

                        if [ "$HTTP_RESPONSE" -ge 200 ] && [ "$HTTP_RESPONSE" -lt 300 ]; then
                            echo "Upload successful!"
                        else
                            echo "Upload failed with HTTP code $HTTP_RESPONSE"
                            exit 1
                        fi
                    '''
                }
            }
        }
        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
                sh '''
                    if [ -d build ]; then
                        cd build
                        # Run all registered CTest tests
                        ctest --output-on-failure
                    else
                        echo "Build directory not found!"
                        exit 1
                    fi
                '''
            }
        }
        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube (SonarCloud) analysis...'
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    sh '''
                        sonar-scanner \
                          -Dsonar.organization=${SONAR_ORGANIZATION} \
                          -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                          -Dsonar.sources=src \
                          -Dsonar.cfamily.compile-commands=compile_commands.json \
                          -Dsonar.host.url=https://sonarcloud.io \
                          -Dsonar.sourceEncoding=UTF-8
                    '''
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        success {
            echo 'Build, lint, and SonarCloud analysis completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Check logs or SonarCloud dashboard.'
        }
    }
}

pipeline{
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIAL')
        VERSION = "${env.BUILD_ID}"
    }
    tools {
        maven "Maven"
    }
    stages {
        stage('Maven Build') {
            steps {
                sh 'mvn clean package  -DskipTests'
            }
        }
        stage('Run Tests'){
            steps {
                sh 'mvn test'
            }
        }
        stage('SonarQube Analysis'){
    steps {
         sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://34.224.3.237:9000/ -Dsonar.login=squ_ffa37b07632fe1f053b488e3c45f6bd4c92b8966'
    }
        }
        stage('Check code coverage'){
            steps{
                script{
                    def token = "squ_ffa37b07632fe1f053b488e3c45f6bd4c92b8966"
                    def sonarQubeUrl = "http://34.224.3.237:9000/api"
                    def componentKey = "com.cotiviti:userinfo"
                    def coverageThreshold = 50.0
                    def response = sh (
                                            script: "curl -H 'Authorization: Bearer ${token}' '${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage'",
                                            returnStdout: true
                                        ).trim()
                    def coverage = sh (
                                            script: "echo '${response}' | jq -r '.component.measures[0].value'",
                                            returnStdout: true
                                        ).trim()
                    def cov = 0.0
                                        if (coverage.isFloat()) {
                                            cov = coverage.toDouble()
                                        } else {
                                            println "Coverage value is not a valid number: $coverage"
                                            // Handle the case when coverageString is not a valid number
                                        }
                    echo "Coverage: ${cov}"

                    if (cov < coverageThreshold) {
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }
        stage('Docker Build and Push'){
            steps {
                      sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                      sh 'docker build -t chelseanirajan/userinfo-service:${VERSION} .'
                      sh 'docker push chelseanirajan/userinfo-service:${VERSION}'
                  }
        }
        stage('Cleanup Workspace') {
              steps {
                deleteDir()
              }
        }

        stage('Update Image Tag in GitOps') {
              steps {
                 checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'git@github.com:chelseanirajan/deployment-folder.git']])
                script {
               sh '''
                  sed -i "s/image:.*/image: chelseanirajan\\/userinfo-service:${VERSION}/" aws/userinfo-manifest.yml
                '''
                  sh 'git checkout master'
                  sh 'git add .'
                  sh 'git commit -m "Update image tag"'
                sshagent(['git-ssh'])
                    {
                          sh('git push')
                    }
                }
              }
        }


    }

}
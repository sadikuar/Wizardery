pipeline {
    options {
        disableConcurrentBuilds()
    }
    
    agent { docker { image 'maven:3.6.3-jdk-11-slim' } }
    
    environment {
        SPRING_DATASOURCE_USERNAME = credentials('SPRING_DATASOURCE_USERNAME')
        SPRING_DATASOURCE_PASSWORD = credentials('SPRING_DATASOURCE_PASSWORD')
    }
    
    stages {
        stage('IntegrationTest') {
            agent {
                docker {
                  image 'lucienmoor/katalon-for-jenkins:latest'
                  args '-p 8888:8080'
                }
            }
            steps {
            	cleanWs()
                unstash "app"
                sh 'java -jar ./Wizardery/target/Wizardery-0.0.1-SNAPSHOT.jar -Dspring.profiles.active=prod >/dev/null 2>&1 &'
                sh 'sleep 30'
                sh 'chmod +x ./runTest.sh'
                sh './runTest.sh'

                cleanWs()
            }
        }
    }
    post {
        always {
          echo 'Clean up'
          deleteDir()
        }
    }
}

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
    	stage('Build') {
            steps {
            	sh 'ls'
                sh 'mvn clean package -Dspring.profiles.active=prod -Dmaven.test.skip=true'
                stash name: "app", includes: "**"
            }
        }
        
        stage('SonarCloud analysis') {
            steps {
            	unstash "app"
                sh 'mvn sonar:sonar -Dspring.profiles.active=prod -Dmaven.test.skip=true'
            }
        }
        
        stage('Unit tests') {
        	steps {
        		unstash "app"
        		sh 'mvn test -Dspring.profiles.active=prod'
    		}
        }
        
        stage('IntegrationTest') {
            agent {
                docker {
                  image 'lucienmoor/katalon-for-jenkins:latest'
                  args '-p 8888:8080'
                }
            }
            steps {
                unstash "app"
                sh 'java -jar ./Wizardery/target/Wizardery-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &'
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

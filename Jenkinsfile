pipeline {
    agent { docker { image 'maven:3.6.3-jdk-11-slim' } }
    
    environment {
        SPRING_DATASOURCE_USERNAME = credentials('SPRING_DATASOURCE_USERNAME')
        SPRING_DATASOURCE_PASSWORD = credentials('SPRING_DATASOURCE_PASSWORD')
    }
    
    stages {
    	stage('Checkout') {
    		steps {
    			git 'https://github.com/sadikuar/Wizardery'
    			sh 'printenv'
    		}
    	}
    	
    	stage('Build') {
            steps {
                sh 'mvn clean package -Dspring.profiles.active=prod -Dmaven.test.skip=true'
            }
        }
        
        stage('SonarCloud analysis') {
            steps {
                sh 'mvn sonar:sonar -Dspring.profiles.active=prod -Dmaven.test.skip=true'
            }
        }
        
        stage('Unit tests') {
        	steps {
        		sh 'mvn clean test -Dspring.profiles.active=prod'
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
                echo "Running integration tests"
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

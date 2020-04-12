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
        
    	stage('Build && SonarQube analysis') {
            steps {
                withSonarQubeEnv('My SonarQube Server') {
                    sh 'mvn clean package sonar:sonar -Dspring.profiles.active=prod'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
                    // pipeline will pause/stop if quality gate fails
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Unit tests') {
        	steps {
        		sh 'mvn clean test -Dspring.profiles.active=prod'
    		}
        }
    }
}
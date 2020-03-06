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
                sh 'mvn -B -V -U -e -Dspring.profiles.active=prod clean package'
            }
        }
        
        stage('Unit tests') {
        	steps {
        		sh 'mvn clean test -Dspring.profiles.active=prod'
    		}
        }
        
        stage('Deploy') {
        	steps {
        		sh 'echo deploying'
        	}
        }
    }
}
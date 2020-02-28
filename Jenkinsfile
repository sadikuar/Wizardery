pipeline {
    agent { docker { image 'maven:3.3.3' } }
    
    environment {
        SPRING_DATASOURCE_USERNAME = credentials('SPRING_DATASOURCE_USERNAME')
        SPRING_DATASOURCE_PASSWORD = credentials('SPRING_DATASOURCE_PASSWORD')
        SPRING_PROFILES_ACTIVE = 'prod'
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
                sh 'mvn -B -V -U -e clean package'
            }
        }
        
        stage('Deploy') {
        	steps {
        		sh 'echo deploying'
        	}
        }
    }
}
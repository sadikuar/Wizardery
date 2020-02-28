pipeline {
    agent { docker { image 'maven:3.3.3' } }
    stages {
    	stage('Checkout') {
    		steps {
    			git 'https://github.com/sadikuar/Wizardery'
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
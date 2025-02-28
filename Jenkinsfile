pipeline {
    agent any
    stages {
        stage('Run tests with Maven') {
            steps {
                sh 'mvn clean verify package -Pintegration-tests -Dspring.profiles.active=qa'
            }
        }        

        stage('Login, Build and Push image to DockerHub') {
            steps {
                docker.withRegistry('https://registry.hub.docker.com','dockerhub') {
	                sh 'mvn jib:dockerBuild && mvn jib:build'
                }
            }
            
        }
    }
}
pipeline {
    agent any
    stages {
        stage('Run tests with Maven') {
            steps {
                echo 'Executando os testes'
            }
        }

        stage('Login to Docker Hub') {
            steps {
                echo 'Logando no Docker Hub'
            }
        }

        stage('Build and Push image to DockerHub') {
            steps {
                echo 'Buildando imagem e Enviando para o DockerHub'
            }
            
        }
    }
}
#!/bin/bash
#instalar docker
    sudo apt-get update -y &&
    sudo apt-get install -y \
        apt-transport-https \
        ca-certificates \
        curl \
        gnupg-agent \
        software-properties-common &&
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add - &&
    sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" &&
    sudo apt-get update -y &&
    sudo sudo apt-get install docker-ce docker-ce-cli containerd.io -y &&
    sudo usermod -aG docker ubuntu
    sudo systemctl enable docker.service
    sudo systemctl enable containerd.service

#instalar docker compose
    sudo apt install docker-compose -y

#mudar para o usuário root
    sudo su

#criar a pasta do projeto
    mkdir ~/workspace
    cd ~/workspace

#clonar o projeto do github
    git clone https://github.com/hiranneri/dockert2s.git
    cd DockerT2S
    git checkout master
    git pull
    cd src/main/resources/

#executar o arquivo docker-compose
    docker compose up -f docker-compose.yaml -d
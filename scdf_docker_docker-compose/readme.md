## A docker image with spring data flow docker-compose
### Commands

    docker build -t ubuntu_dataflow:v1 .
    docker run -p 22:22 -v /var/run/docker.sock:/var/run/docker.sock --name ubuntu_dataFlow ubuntu_dataflow:v1
    docker exec -it ubuntu_dataFlow /bin/bash 
    ssh remote_user@172.17.0.1 22
    docker-compose -f ./docker-compose.yml -f ./docker-compose-rabbitmq.yml up
    docker rm $(docker ps -qa)


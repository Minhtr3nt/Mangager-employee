## Build docker image
docker build -t <account>/identity-service:0.9.0 .

## Push docker image to Docker Hub
docker image push <account>/identity-service:0.9.0

## Create network:
docker network create minh-network

## Start MySQL in minh-network
docker run --network minh-network --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.38-debian

## Run your application in minh-network
docker run --name identity-service --network minh-network -p 8080:8080 -e DBMS_CONNECTION=jdbc:mysql://mysql:3306/identity_service identity-service:0.0.1

## CHECK LIST NETWORK
docker network ls

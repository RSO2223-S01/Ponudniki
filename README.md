# RSO: Ponudniki microservice

## Prerequisites

```bash
docker run -d --name pg-ponudniki -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ponudniki -p 5432:5432 postgres:13
```

## Build and run commands
```bash
mvn clean package
cd api/target
java -jar ponudniki-1.0.0-SNAPSHOT.jar
```
Available at: localhost:8080/v1/providers

## Run in IntelliJ IDEA
Add new Run configuration and select the Application type. In the next step, select the module api and for the main class com.kumuluz.ee.EeApplication.

Available at: localhost:8080/v1/providers

## Docker commands
```bash
docker build -t novaslika .   
docker images
docker run novaslika    
docker tag novaslika prporso/novaslika   
docker push prporso/novaslika
docker ps
```

```bash
docker network ls  
docker network rm rso
docker network create rso
docker run -d --name pg-ponudniki -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=ponudniki -p 5432:5432 --network rso postgres:13
docker inspect pg-image-metadata
docker run -p 8080:8080 --network rso -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-ponudniki:5432/image-metadata prporso/ponudniki
```

## Consul
```bash
consul agent -dev
```
Available at: localhost:8500

Key: environments/dev/services/ponudniki-service/1.0.0/config/rest-properties/maintenance-mode

Value: true or false

## Kubernetes
```bash
kubectl version
kubectl --help
kubectl get nodes
kubectl create -f ponudniki-deployment.yaml 
kubectl apply -f ponudniki-deployment.yaml 
kubectl get services 
kubectl get deployments
kubectl get pods
```
Secrets: https://kubernetes.io/docs/concepts/configuration/secret/


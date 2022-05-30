Update:
docker-compose pull

Start:
docker-compose up -d

Down:
docker-compose down

Logs:
docker logs <container>

tailing:
docker logs -f --tail=100 <container>

Convert docker to kubernetes
https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/

kubectl docs
https://kubernetes.io/ru/docs/reference/kubectl/cheatsheet/

kompose convert

kubectl get nodes

kubectl describe svc

kubectl apply -f zookeeper-1-service.yaml,kafka-1-service.yaml,microservice-1-tcp-service.yaml,microservice-2-service.yaml,zookeeper-1-deployment.yaml,kafka-1-deployment.yaml,init-kafka-deployment.yaml,microservice-1-deployment.yaml,microservice-2-deployment.yaml


https://docs.docker.com/desktop/kubernetes/#:~:text=The%20Kubernetes%20server%20runs%20within,not%20affect%20your%20other%20workloads.
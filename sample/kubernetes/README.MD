minikube start --vm-driver=none
minikube stop

sudo chmod 777 /var/run/docker.sock

kubectl exec -it postgres-5d7b8bbf78-w7qb7    --  psql -h localhost -U admin --password -p 5432 micro

kubectl apply -f

kubectl -n kubernetes-dashboard create token admin-user

kubectl proxy --address='0.0.0.0' --port=8002 --accept-hosts='.*'

kubectl autoscale deployment application --cpu-percent=50 --min=1 --max=10
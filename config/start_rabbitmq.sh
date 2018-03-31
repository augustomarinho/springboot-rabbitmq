#!/bin/bash

exist="$(sudo docker ps -a | grep rabbitmq-management | wc -l)"

if [ $exist = '0' ]; then
	echo "Container com RabbitMQ nao existe. Criando: "
        docker run -d --hostname rabbit-nextel --name rabbitmq-management -p 15672:15672 -p 5672:5672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management
else
	echo "Container com RabbitMQ ja existe"
	isRunning="$(sudo docker inspect --format='{{.State.Running}}' rabbitmq-management)"
	if [ $isRunning = 'true' ]; then
		echo "Container ja esta rodando"
	else
		echo "Iniciando container:"
		hostName="$(sudo docker inspect --format='{{.Config.Hostname}}' rabbitmq-management)"
		sudo docker start $hostName
	fi	
fi
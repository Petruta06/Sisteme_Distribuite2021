if [ $1 == 'rmcontainers' ]
then
	echo "remove docker containers"
	docker stop student_mservice_1
	docker stop student_mservice_2
	docker stop student_mservice_3
	docker stop teacher_mservice
	docker stop msgmanager_mservice
	
	docker rm student_mservice_1
	docker rm student_mservice_2
	docker rm student_mservice_3
	docker rm teacher_mservice
	docker rm msgmanager_mservice
	
	docker ps -a
elif [ $1 == 'rmimages' ]
then
	echo "remove docker images"
	docker rmi -f localhost:5000/student_mservice:tip1
	docker rmi -f localhost:5000/student_mservice:tip2
	docker rmi -f localhost:5000/student_mservice:tip3
	docker rmi -f localhost:5000/teacher_mservice:v1
	docker rmi -f localhost:5000/msgmanager_mservice:v1 

	docker images ls
elif [ $1 == 'buildimages' ]
then
	echo "build docker images"
	cd StudentMicroservice
	docker build -t localhost:5000/student_mservice:tip1 .
	docker push localhost:5000/student_mservice:tip1
	
	docker build -t localhost:5000/student_mservice:tip2 .
	docker push localhost:5000/student_mservice:tip2

	docker build -t localhost:5000/student_mservice:tip3 .
	docker push localhost:5000/student_mservice:tip3

	cd -
	cd TeacherMicroservice/TeacherMicroservice
	docker build -t localhost:5000/teacher_mservice:v1 .
	docker push localhost:5000/teacher_mservice:v1	
	
	cd -
	cd MessageManagerMicroservice
	docker build -t localhost:5000/msgmanager_mservice:v1 .
	docker push localhost:5000/msgmanager_mservice:v1
	cd -
	docker images
elif [ $1 == 'run' ]
then
	echo "run docker containers"
	docker run -d -p 1500:1500 --name msgmanager_mservice --network=ms-net localhost:5000/msgmanager_mservice:v1
	docker run -d -p 1600:1600 -e MESSAGE_MANAGER_HOST='message_manager' --name teacher_mservice --network=ms-net localhost:5000/teacher_mservice:v1
	docker run -d -e MESSAGE_MANAGER_HOST='message_manager' --name student_mservice_1 --network=ms-net localhost:5000/student_mservice:tip1
	docker run -d -e MESSAGE_MANAGER_HOST='message_manager' --name student_mservice_2 --network=ms-net localhost:5000/student_mservice:tip2
	docker run -d -e MESSAGE_MANAGER_HOST='message_manager' --name student_mservice_3 --network=ms-net localhost:5000/student_mservice:tip3
fi

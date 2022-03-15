clear

#sudo mkdir /var/etcd-data /var/etcd-data.tmp
#sudo chgrp docker /var/etcd-data /var/etcd-data.tmp
#sudo chmod g=rwx /var/etcd-data /var/etcd-data.tmp


idcontainer=`docker run -d -p 2379:2379 -p 2380:2380 \
--mount type=bind,source=/var/etcd-data.tmp,destination=/var/etcd-data \
--name etcd-gcr-v3.4.7 gcr.io/etcd-development/etcd:v3.4.7 /usr/local/bin/etcd \
--name s1 \
--data-dir /var/etcd-data \
--listen-client-urls http://0.0.0.0:2379 \
--advertise-client-urls http://0.0.0.0:2379 \
--listen-peer-urls http://0.0.0.0:2380 \
--initial-advertise-peer-urls http://0.0.0.0:2380 \
--initial-cluster s1=http://0.0.0.0:2380 \
--initial-cluster-token tkn \
--initial-cluster-state new \
--log-level info \
--logger zap \
--log-outputs stderr`


echo -e "Id container : $idcontainer\n"
docker logs $idcontainer

echo -e "\n"
docker ps

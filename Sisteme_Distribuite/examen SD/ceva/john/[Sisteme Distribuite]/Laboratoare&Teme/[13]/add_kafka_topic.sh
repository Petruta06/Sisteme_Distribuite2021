echo "---Pornire server Kafka"
#sudo /opt/kafka/bin/kafka-server-start.sh -daemon /opt/kafka/config/server.properties

echo "---Verificare pornire corecta server kafka"
#ps -ef | grep kafka


# --------------------------------------------------------------------------

echo "---Testarea crearii unui subiect (topic)"
#sudo /opt/kafka/bin/kafka-topics.sh --create --zookeeper etcd://localhost:2379 --replication-factor 1 --partitions 1 --topic test

echo "---Verificare topic"
#sudo /opt/kafka/bin/kafka-topics.sh --zookeeper etcd://localhost:2379 --describe --topic test

echo "---Testarea producerii unor mesaje dummy pentru topicul creat"
#sudo /opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test


echo "---Testarea consumarii mesajelor de test produse mai sus"
#sudo /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

echo "---Sterge topic Kafka"
#sudo /opt/kafka/bin/kafka-topics.sh --delete --zookeeper etcd://localhost:2379 --topic test

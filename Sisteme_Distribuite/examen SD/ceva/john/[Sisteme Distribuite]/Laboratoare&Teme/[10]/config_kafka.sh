clear

#sudo apt-get install gradle

#git clone https://github.com/banzaicloud/apache-kafka-on-k8s.git

# se modifica fisierul build.gradle :  in vectorul scalaCompileOptions.additionalParameters se adauga "-target:jvm-1.8"

#cd apache-kafka-on-k8s

#prin absurd, daca nu e setat JAVA_HOME
#export JAVA_HOME=/usr/lib/jvm/jdk1.8.0_251 

#gradle

#./gradlew releaseTarGz -x signArchives

#cd core/build/distributions

#tar -xzvf kafka_2.11-2.0.0-SNAPSHOT.tgz

#sudo mv kafka_2.11-2.0.0-SNAPSHOT /opt/kafka

#sudo chown -R $USER:$(groups | awk '{ print $1 }') /opt/kafka

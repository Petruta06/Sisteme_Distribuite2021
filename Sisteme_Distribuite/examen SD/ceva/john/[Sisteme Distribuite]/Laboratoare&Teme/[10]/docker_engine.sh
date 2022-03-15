clear

echo "---Actualizare indecsi gestionar de pachete apt"
#sudo apt update

echo "---Instalare pachete aditionale necesare"
#sudo apt-get install\
# apt-transport-https\
# ca-certificates\
# curl\
# gnupg2\
# software-properties-common

echo "---Adaugare cheie GPG a Docker in inelul de chei al gestionarului de pachete apt"
#curl -fsSL https://download.docker.com/linux/debian/gpg | sudo apt-key add -

echo "---Adaugare depozit de pachete oficial Docker in lista sistemului"
#sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/debian $(lsb_release -cs) stable"

echo "---Reactualizare indecsi gestionar de pachete apt"
#sudo apt update

echo "---Instalare Docker Engine Community Edition, impreuna cu utilitarul pentru linia de comanda Docker"
#sudo apt install -y docker-ce docker-ce-cli

echo "---Creare grup Docker pe sistemul gazda"
#sudo groupadd docker

echo "---Adaugare utilizator privilegiat"
#sudo usermod -aG docker $USER

echo "---Permitere utilizatori neprivilegiati"
#sudo echo {"insecure-registries" : ["localhost:5000"] >> /etc/docker/daemon.json

echo "---Repornire Docker"
#sudo service docker restart

echo "---Verificare functionare Docker Engine"
#docker info



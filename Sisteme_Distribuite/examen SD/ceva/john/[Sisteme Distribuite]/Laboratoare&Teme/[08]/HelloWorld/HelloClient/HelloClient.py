import socket

if __name__ == "__main__":
    HOST, PORT = "localhost", 1234
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        sock.connect((HOST, PORT))
    except:
        print("Eroare de conectare la microserviciu!")
        exit(1)

    print("Trimit mesaj catre microserviciu...")
    sock.send(bytes("Hello from client!", "utf-8"))

    received = str(sock.recv(1024), "utf-8")
    print(f"Raspuns de la HelloMicroservice : {received}")
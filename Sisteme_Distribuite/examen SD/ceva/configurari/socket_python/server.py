

import socket
import time

def main():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.bind(("localhost", 1234))
    sock.listen(5)
    print("astept")
    conn, addr = sock.accept()
    while True:
        print("trimit")
        conn.send(bytes("question_text" + "\n", "utf-8"))
        time.sleep(5)

if __name__ == '__main__':
    main()

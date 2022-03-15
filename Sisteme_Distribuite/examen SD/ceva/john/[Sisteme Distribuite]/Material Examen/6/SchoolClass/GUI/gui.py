from tkinter import *
from tkinter import ttk
import threading
import socket

HOST = "localhost"
TEACHER_PORT = 1600

def resolve_question(question_text):
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        sock.connect((HOST, TEACHER_PORT))
        sock.send(bytes(question_text + "\n", "utf-8"))
        response_text = str(sock.recv(1024), "utf-8")
    except ConnectionError:
        response_text = "Eroare de conectare la microserviciul Teacher!"

    response_widget.insert(END, response_text)

def ask_question():
    question_text = question.get()
    threading.Thread(target=resolve_question, args=(question_text,)).start()


if __name__ == '__main__':
    root = Tk()
    root.columnconfigure(0, weight=1)
    root.rowconfigure(0, weight=1)

    content = ttk.Frame(root)
    response_widget = Text(content, height=10, width=50)
    question_label = ttk.Label(content, text="Profesorul intreaba:")
    question = ttk.Entry(content, width=50)
    ask = ttk.Button(content, text="Intreaba", command=ask_question)
    exitbtn = ttk.Button(content, text="Iesi", command=root.destroy)
    content.grid(column=0, row=0)
    response_widget.grid(column=0, row=0, columnspan=3, rowspan=4)
    question_label.grid(column=3, row=0, columnspan=2)
    question.grid(column=3, row=1, columnspan=2)
    ask.grid(column=3, row=3)
    exitbtn.grid(column=4, row=3)

    root.mainloop()
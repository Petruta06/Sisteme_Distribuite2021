from CheltuieliController import *
from flask import Flask, redirect, url_for, request, abort, Response

app = Flask(__name__)

@app.route('/')
def hello():
    return "Acesta este o aplicatie care evidenta cheltuielilor unei familii!"


@app.route('/afisare')
def afisare():
    return CheltuieliController.afisare()


@app.route('/cautare/<int:id>', methods=['GET'])
def cautare_indice(id):
    print(id)
    s = CheltuieliController.get_detaliu(id)
    print(s)
    return s

@app.route('/cautare/<string:nume>', methods=['GET'])
def cautare(nume):
    s = CheltuieliController.cautare(nume)
    return s

@app.route('/stergere/<int:id>', methods=['DELETE'])
def stergere(id):
    s = CheltuieliController.stergere_detaliu(id)
    return s

@app.route('/update/<int:id>', methods=['PUT'])
def actulizare(id):
    s = CheltuieliController.actualizare_detaliu(id, None)
    return s


@app.route('/total')
def total():
    s = CheltuieliController.cheltuieli_total()
    return s

if __name__ == '__main__':
   app.run(host='0.0.0.0', port=8080)
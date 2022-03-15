from flask import Flask, jsonify, abort
from flask import json
from CheltuieliService import *

class CheltuieliController:

    agendaC = CheltuieliService()

    @staticmethod
    def creare_detalii(detaliu):
        CheltuieliController.agendaC.creare_detalii(detaliu)

    @staticmethod
    def get_detaliu(id):
        c = CheltuieliController.agendaC.get_detalii(id)
        if( c != None):
            return jsonify(c.to_json())
        else:
           abort(404,f'Id-ul nu a fost gasit' )

    @staticmethod
    def actualizare_detaliu(id, d2):
        if(CheltuieliController.agendaC.get_detalii(id)):
            CheltuieliController.agendaC.actualizare_detalii(id, d2)
            return f'Datele au fost actualizate  cu succes!'
        else:
            abort(404, f'Datele nu au fost gasite in agenda!')

    @staticmethod
    def stergere_detaliu(id):
        if (CheltuieliController.agendaC.get_detalii(id)):
            CheltuieliController.agendaC.stergere_detalii(id)
            return (f'Datele au sterse cu succes!')
        else:
           abort(404, f'Datele nu au fost gasite in agenda de cheltuieli!')

    @staticmethod
    def cautare(nume):
        obiect = None
        pret = None
        c =  CheltuieliController.agendaC.cautare_detalii(nume, obiect, pret)
        if c != None:
            s = c.to_sir()
            return c.to_json()
        else: abort(404, f'Nu exista nicio inregistrare care sa corespunda acestui indice!!')

    @staticmethod
    def afisare():
        return CheltuieliController.agendaC.afisare()

    @staticmethod
    def cheltuieli_total():
        suma = CheltuieliController.agendaC.total_cheltuieli()
        if suma !=0:
            return f'Cheltuielile familie insumeaza {suma} lei.'
        else:
            abort(404, f'Nu avem cheltuieli inregistrate!')

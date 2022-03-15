from CheltuieliInterface import *
from Detalii import *


class CheltuieliService(CheltuieliInterface):
    detalii_initiale = [Detalii(1, "mama", "paine", 3.4, "luni"),
                Detalii(2, "tata", "ciocolata", 5, "marti")]


    def __init__(self):
        self.agenda =  CheltuieliService.detalii_initiale


    def afisare(self):
        s = ""
        for c in self.agenda:
            s = s + str(c.id)+'\t'+c.persoana +'\t'+c.obiect + '\t'+ str(c.pret) +'\t'+ c.ziua_sapt+'\n'
           # print(str(c.id)+'\t'+c.persoana +'\t'+c.obiect)
        return s



    def get_detalii(self, id):
        rezultat = None
        for c in self.agenda:
            if c.id == id:
                rezultat = c
        return rezultat

    def creare_detalii(self, d):
        self.agenda += [d]

    def stergere_detalii(self, id):
        for c in self.agenda:
            if c.id == id:
                self.agenda.remove(c)

    def actualizare_detalii(self, id, detalii):
        for c in self.agenda:
            if c.id == id:
                self.agenda.remove(c)
                self.agenda.append(detalii)


    def cautare_detalii(self, nume_persoana=None, obiect=None, pret= None):
        for c in self.agenda:
            if c.persoana == nume_persoana or c.persoana == obiect or c.pret == pret:
                return c
        return None

    def total_cheltuieli(self):
        suma = 0
        for c in self.agenda:
            suma = suma + c.pret

        return suma
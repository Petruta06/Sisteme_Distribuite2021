import json

class Detalii:
    def __init__(self):
        self.id = 0
        self.persoana = ""
        self.obiect = ""
        self.pret = ""
        self.ziua_sapt =""

    def __init__(self, id, persoana, obiect, pret, ziua):
        self.id = id
        self.persoana = persoana
        self.obiect = obiect
        self.pret = pret
        self.ziua_sapt = ziua

    def to_sir(self):
        s = str(self.id) + '\t' +self.persoana + '\t' + self.obiect


    def to_json(self):
        jsonStr = json.dumps(self.__dict__)
        return jsonStr
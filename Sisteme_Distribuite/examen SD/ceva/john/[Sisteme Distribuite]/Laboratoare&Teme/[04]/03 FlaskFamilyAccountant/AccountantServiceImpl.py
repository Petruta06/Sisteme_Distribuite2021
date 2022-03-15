from AccountantService import AccountantService
from Member import Member

class AccountantServiceImpl(AccountantService):
    initialFamily = [
        Member(1, "Vasile",   12000.0, 500.0),
        Member(2, "Elena",    3500.0,  800.0),
        Member(3, "Cristian", 8000.0,  2500.0),
        Member(4, "Roxana",   1200.0,  500.2),
        Member(5, "Stefan",   2800.0,  1200.0)
    ]
    __family = dict({value.id : value for value in initialFamily})

    def getMember(self, id) -> Member:
        return self.__family[id].getMember()

    def createMember(self, member : Member):
        self.__family[member.id] = member

    def deleteMember(self, id : int):
        self.__family.pop(id)

    def updateMember(self, id : int, member : Member):
        self.deleteMember(id)
        self.createMember(member)

    def calculateBudget(self) -> float:
        budget = 0.0
        for member in self.__family.values():
            budget += member.budget
        return budget

    def calculateOutGo(self) -> float:
        outgo = 0.0
        for member in self.__family.values():
            outgo += member.outgo
        return outgo

    def searchFamily(self):
        return self.__family

from flask import Flask, jsonify, abort
from AccountantService import AccountantService
from AccountantServiceImpl import AccountantServiceImpl
from Member import Member
from Member import MemberEncoder
import json

class Controller:
    accountantService = AccountantServiceImpl()

    # value = ["/member/{id}"], method = [RequestMethod.GET]
    @staticmethod
    def getMember(id):
        member = Controller.accountantService.getMember(id)
        if member == None:
            abort(404, f"Member {id} not found!")
        return jsonify(member)

    # value = ["/family"], method = [RequestMethod.GET]
    @staticmethod
    def getFamily():
        memberList = Controller.accountantService.searchFamily()
        if memberList == None:
            abort(404, "Family not found!")
        else:
            return jsonify(MemberEncoder().encode(memberList))

    # value = ["/family/budget"], method = [RequestMethod.GET]
    @staticmethod
    def getBudget():
        budget = Controller.accountantService.calculateBudget()
        if budget == None:
            abort(404, "Budget not found!")
        else:
            return f"Bugetul familiei insumeaza {budget} lei."

    # value = ["/family/outgo"], method = [RequestMethod.GET]
    @staticmethod
    def getOutgo():
        outgo = Controller.accountantService.calculateOutGo()
        if outgo == None:
            abort(404, "Budget not found!")
        else:
            return f"Cheltuielile familiei insumeaza {outgo} lei."

    # value = ["/member/{id}"], method = [RequestMethod.DELETE]
    @staticmethod
    def deleteMember(id):
        if Controller.accountantService.getMember(id) != None:
            Controller.accountantService.deleteMember(id)
            return f"Membrul cu id {id} a fost inlaturat cu succes."
        else:
            abort(404,"Membrul nu a fost gasit")

    # value = ["/member"], method = [RequestMethod.POST]
    @staticmethod
    def createMember(member : Member):
        Controller.accountantService.createMember(member)
        return "Membrul a fost adaugat cu succes"

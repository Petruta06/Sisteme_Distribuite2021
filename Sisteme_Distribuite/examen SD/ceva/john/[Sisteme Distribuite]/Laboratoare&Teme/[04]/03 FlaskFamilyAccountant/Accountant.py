#!flask/bin/python
from flask import Flask, jsonify, render_template
from AccountantController import Controller

app = Flask(__name__)

@app.route('/')
def mainLoop():
    return render_template("index.html")

@app.route('/member/<int:id>', methods=['GET'])
def getTasks(id):
    member = Controller.getMember(id)
    return member

@app.route('/family', methods=['GET'])
def getFamilty():
    family = Controller.getFamily()
    return family

@app.route('/family/budget', methods=['GET'])
def getBudget():
    budget = Controller.getBudget()
    return budget

@app.route('/family/outgo', methods=['GET'])
def getOutGo():
    outgo = Controller.getOutgo()
    return outgo

@app.route('/member/<int:id>', methods=['DELETE'])
def deleteMember(id):
    deleteMember = Controller.deleteMember(id)
    return deleteMember

@app.route('/member', methods=['POST'])
def createMember():
    member = None
    create = Controller.createMember(member)
    return create
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
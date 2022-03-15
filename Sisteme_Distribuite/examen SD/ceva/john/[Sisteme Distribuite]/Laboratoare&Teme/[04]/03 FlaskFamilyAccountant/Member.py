import json

class MemberEncoder(json.JSONEncoder):
    def default(self, o):
        return o.__dict__

class Member:
    id : int = 0
    name : str = ""
    budget : float = 0.0
    outgo : float = 0.0

    def __init__(self,_id,_name,_budget,_outgo):
        self.id = _id
        self.name = _name
        self.budget = _budget
        self.outgo = _outgo

    def getMember(self):
        return MemberEncoder().encode(self)




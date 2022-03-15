import pika

config = {
    "host" : "localhost",
    "port" : 5672,
    "username" : "user",
    "password" : "user",
    "exchange" : "okazii.BAe",
    "routing_key" : "okazii.Bbrk",
    "queue" : "okazii.Bbq"
    #"queue" : "okazii.Awq"
}

credentials = pika.PlainCredentials(username=config["username"],
                                    password=config["password"])
parameters = (pika.ConnectionParameters(config["host"]),
              pika.ConnectionParameters(config["port"]),
              credentials)

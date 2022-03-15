from pyspark import SparkContext, SparkConf, StorageLevel

import os
import re

def getLength(string : str) -> int :
    return len(string)

def getNumbersSum(a : int, b : int) -> int:
    return a + b

if __name__ == "__main__":
    # configurare variabila de mediu cu versiunea python utilizata pentru spark'''
    os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'

    # configurarea spark
    spark_conf = SparkConf().setMaster("local").setAppName("Spark Example")

    # initializarea contextului Spark
    spark_context = SparkContext(conf=spark_conf)

    items = ["123/643/7563/2134/ALPHA","2343/6356/BETA/2342/12", "23423/656/343"]
    # paralelizarea colectiilor
    distributed_dataset = spark_context.parallelize(items) # RDD

    # 1). spargerea fiecarui string din lista intr-o lista de substring-uri si reunirea intr-o singura lista
    # 2). filtrarea cu regex pentru a pastra doar numerele
    # 3). conversia string-urilor filtrate la int prin functia de mapare
    # 4). sumarea tuturor numerelor prin functia de reducere
    sum_of_numbers = distributed_dataset\
        .flatMap(lambda item : item.split("/"))\
        .filter(lambda item : re.match("[0-9]+", item))\
        .map(lambda item : int(item))\
        .reduce(lambda total, next_item : total + next_item)
    print(f"Sum of numbers = ${sum_of_numbers}")

    # seturi de date externe : setul de date nu este inca incarcat in memorie (si nu se actioneaza inca asupra lui)
    textFileName = 'resources/data.txt'
    lines = spark_context.textFile(textFileName)

    # pentru utilizarea unui RDD de mai multe ori, trebuie apelata metoda persist
    lines.persist(StorageLevel.MEMORY_ONLY)

    # functia de mapare reprezinta o transformare a setului de date initial (nu este calculat imediat)
    # abia cand se ajunge la functia reduce Spark imparte operatiile in task-uri pentru a putea fi rulate pe masini separate
    # fiecare masina executa o parte din mao si reduce
    total_length0 = lines.map(lambda line : len(line)).reduce(lambda acc, i : acc + i)
    total_length1 = lines.map(getLength).reduce(getNumbersSum)

    print(f"Total length0 : ${total_length0}")
    print(f"Total length1 : ${total_length1}")

    # variabila partajata de tip broadcast : trimiterea unui set de date ca input catre fiecare nod intr-o maniera eficienta
    broadcast_var = spark_context.broadcast([1,2,3])
    total_length2 = lines.map(lambda line : len(line) + broadcast_var.value[0]).reduce(lambda acc, i : acc + i)
    print(f"Sum(line_length + broadcast_value) = ${total_length2}")

    # variabila partajata de tip acumulator
    acumulator = spark_context.accumulator()
    spark_context.parallelize([1,2,3,4,5]).foreach(lambda x : acumulator.add(x))
    print(f"Acumulator = ${acumulator}")

    spark_context.stop()

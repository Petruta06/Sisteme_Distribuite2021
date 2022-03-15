from pyspark import SparkContext, SparkConf, StorageLevel
from pyspark.streaming import StreamingContext
import os

pozitiv =["ana", "python", "mere"]
negativ =["date", "time"]

def is_pozitif(words):
    if(words in pozitiv):
        return True
    return False

def is_negatif(words):
    if (words in negativ):
        return True
    return False

def read_list():
    pass
def prelucrare(word):
    '''if(is_pozitif(word)):
        print( "\tpozitiv\n")
    elif(is_negatif(word)):
        print( "\tnegativ\n")
    else:
        print( "\tneutru\n")'''
    print("catel")

def main():
    os.environ["PYSPARK_PYTHON"] = '/usr/bin/python3'
    #spark_conf = SparkConf.setMaster(value="local").setAppName("News")
    spark_context = SparkContext(master="local[*]")
    print("Ma pregatesc sa primesc date!")
    spark_stream = StreamingContext(spark_context, 2)
    stream_data = spark_stream.socketTextStream("localhost",1234 )
    print("trimesc date")
    stream_data.foreachRDD(lambda rdd:print(rdd.collect()))

    print("am terminat de primit date!")

    spark_stream.start()
    spark_stream.awaitTermination()
    spark_stream.stop()


if __name__ == '__main__':
        main()
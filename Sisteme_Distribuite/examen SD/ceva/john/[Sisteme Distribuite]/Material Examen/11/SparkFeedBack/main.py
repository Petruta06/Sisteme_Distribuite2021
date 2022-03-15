from pyspark import SparkContext, SparkConf, StorageLevel
from pyspark.streaming import StreamingContext
import os
import re
import threading


os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'

sparkConf = SparkConf().setMaster("local[2]").setAppName("Text File Streaming")
sc = SparkContext(conf=sparkConf)




def countWords(string):
    return len(string.split(" "))

def newsInterpret(rdd):
    new = rdd.collect()
    if new != []:
        new = new[-1]
        print(f"Stirea gasita : {new}")

        wordFileBase = "resources/words/{}-words.txt"
        goodWordsFileName = wordFileBase.format("positive")
        badWordsFileName = wordFileBase.format("negative")

        goodWords = sc.textFile(goodWordsFileName)
        badWords = sc.textFile(badWordsFileName)

        goodWords.persist(StorageLevel.MEMORY_ONLY)
        badWords.persist(StorageLevel.MEMORY_ONLY)

        goodAccumulator = sc.accumulator(0)
        badAccumulator = sc.accumulator(0)

        goodWords.foreach(lambda word : goodAccumulator.add(1) if re.match(f'\s?{word}\s?', new) else None)
        #badWords.foreach(lambda word : badAccumulator.add(1) if re.match(f'\s?{word}\s?', new) else None)

        print(f"Good accumulator : ${goodAccumulator}")
        print(f"Bad accumulator : ${badAccumulator}")

if __name__ == '__main__':


    ssc = StreamingContext(sc, 1)
    ROOT_DIR = os.path.abspath(os.path.dirname(__file__))
    textFileName = "file:///" + os.path.join(ROOT_DIR, 'resources/news')
    stream = ssc.textFileStream(textFileName)
    stream.foreachRDD(newsInterpret)

    ssc.start()
    ssc.awaitTermination()
from pyspark import SparkContext, SparkConf
from pyspark.streaming import StreamingContext
import os

if __name__ == '__main__':
    os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'

    sparkConf = SparkConf().setMaster("local[2]").setAppName("Text File Streaming")
    sc = SparkContext(conf=sparkConf)

    ssc = StreamingContext(sc, 1)
    ROOT_DIR = os.path.abspath(os.path.dirname(__file__))
    textFileName = "file:///" + os.path.join(ROOT_DIR, 'resources/text')
    stream = ssc.textFileStream(textFileName)
    stream.foreachRDD(lambda rdd : print(rdd.collect()) if rdd.collect() != [] else None)

    ssc.start()
    ssc.awaitTermination()
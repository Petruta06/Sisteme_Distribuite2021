from pyspark import SparkContext
from pyspark.streaming import StreamingContext
import os
if __name__ == '__main__':
	os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'
	sc = SparkContext("local", "Text File Streaming")
	ssc = StreamingContext(sc, 1)
	ROOT_DIR = os.path.abspath(os.path.dirname(__file__))
	path = '/home/ana/Desktop/examen SD/laboratoare/lab14/'+ 'resources.txt'
	stream = ssc.textFileStream(path)
	stream.foreachRDD(lambda rdd: print(rdd.collect()))
	ssc.start()
	ssc.awaitTermination()
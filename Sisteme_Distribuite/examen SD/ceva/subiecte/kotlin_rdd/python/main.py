
from pyspark import SparkContext, SparkConf, StorageLevel
from pyspark.streaming import StreamingContext
import os

def fct(s):
	print(s)

def main():
	os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'
	spark_conf = SparkConf().setMaster("local").setAppName("Incercare")
# initializarea contextului Spark
	spark_context = SparkContext(conf=spark_conf)
	spark_stream = StreamingContext(spark_context, 3)
	stream_data = spark_stream.socketTextStream("localhost", 1500)

	 
	stream_data.foreachRDD(lambda rdd: fct(rdd.collect()))
	
	spark_stream.start()
	spark_stream.awaitTermination()
	spark_stream.stop()

if __name__ == '__main__':
	main()
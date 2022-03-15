from pyspark.streaming.context import StreamingContext
from pyspark.streaming.kafka import KafkaUtils
from pyspark.context import SparkContext
import os


if __name__ == '__main__':
    os.environ['PYSPARK_SUBMIT_ARGS'] = '--jars ./spark-streaming-kafka-0-8-assembly_2.11-2.4.5.jar pyspark-shell'
    sparkContext = SparkContext(master="local", batchSize=0)
    ssc = StreamingContext(sparkContext, batchDuration=1)

    kafkaParams = {"bootstrap.servers": "localhost:9092"}
    kafkaStream = KafkaUtils.createDirectStream(ssc=ssc, topics=["blog"], kafkaParams=kafkaParams)
    kafkaStream.foreachRDD(lambda rdd: print(rdd.collect()))

    ssc.start()
    ssc.awaitTermination()

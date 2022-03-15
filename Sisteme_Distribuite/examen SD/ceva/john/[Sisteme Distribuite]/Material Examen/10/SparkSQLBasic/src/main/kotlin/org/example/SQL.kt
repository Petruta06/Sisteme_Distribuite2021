package org.example

import org.apache.spark.sql.SparkSession

fun main(args: Array<String>) {
    val spark = SparkSession.builder()
        .appName("Java Spark SQL example")
        .config("spark.master","local[4]")
        .orCreate

    // crearea unui DataFrame pe baza unei tabele Person stocata intr-o baza de date MySql
    val url = "jdbc:mysql://localhost:3306/sd_database?user=spark&password=sparksql&serverTimezone=UTC"
    val df = spark.sqlContext()
        .read()
        .format("jdbc")
        .option("url",url)
        .option("dbtable", "Person")
        .load()

    // afisare schemei dataframe-ului
    df.printSchema()

    // numararea persoanelor dupa varsta
    val countsByAge = df.groupBy("age").count()
    countsByAge.show()

    // salvarea countsByAge in src/main/resources/sql_output in format JSON
    countsByAge.write().format("json").save("src/main/resources/sql_output")

}


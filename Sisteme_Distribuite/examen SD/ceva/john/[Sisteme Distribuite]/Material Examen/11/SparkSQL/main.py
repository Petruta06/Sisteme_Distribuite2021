from pyspark.sql import SparkSession, Row
import os

if __name__ == "__main__":
    # configurare variabila de mediu cu versiunea python utilizata pentru spark
    os.environ['PYSPARK_PYTHON'] = '/usr/bin/python3'

    spark_session = SparkSession.builder\
        .appName("Python Spark SQL example")\
        .config("spark.master","local")\
        .getOrCreate()

    # initializarea unui DataFrame prin citirea unui JSON
    peopleFileName = "resources/people.json"
    df = spark_session.read.json(peopleFileName)

    # afisarea continutului din DataFrame la consola
    df.show()

    # afisarea schemei din DataFrame la consola
    df.printSchema()

    # selectarea coloanei nume si afisarea acesteia
    df.select("name").show()

    # selectarea tuturor datelor si incrementarea varstei cu 1
    df.select(df["name"], df["age"], df["age"] + 1).show()

    # selectarea persoanelor cu varsta > 21 ani
    df.filter(df["age"] > 21).show()

    # numararea persoanelor dupa varsta
    df.groupBy("age").count().show()

    # intregistrarea unui dataframe ca un sql view temporar
    df.createOrReplaceTempView("people")

    # utilizarea unei interogari sql pentru a selecta datele
    sqlDF = spark_session.sql("SELECT * FROM people")
    sqlDF.show()

    # inregistrarea unui dataframe ca un sql view global temporar
    df.createGlobalTempView("people")

    # un sql view temporar este legat de o baza de date a sistemului : global_temp
    spark_session.sql("SELECT * FROM global_temp.people").show()

    # un view global temporar este vizibil intre sesiuni
    spark_session.newSession().sql("SELECT * FROM global_temp.people").show()

    # interoperabilitate cu RDD
    # crearea unui RDD de obiecte Person dintr-un fisier text
    peopleFileName = peopleFileName.split(".")[0] + ".txt"
    lines = spark_session.sparkContext.textFile(peopleFileName)
    people = lines.map(lambda line : line.split(",")).map(lambda p : Row(name = p[0], age = int(p[1])))

    # aplicarea unei scheme pe un RDD de bean-uri pentru a obtine DataFrame
    peopleDF = spark_session.createDataFrame(people)
    peopleDF.show()

    # inregistrea dataframe-ului ca un view temporar
    peopleDF.createOrReplaceTempView("people")

    # selectarea persoanelor cu varsta cuprinsa intre 13 si 19 cu sql
    teenagersDF = spark_session.sql("SELECT * FROM people WHERE age BETWEEN 13 AND 19")
    teenagersDF.show()
    peopleDF.filter(peopleDF["age"] >= 13).filter( peopleDF["age"] <= 19).show()

    for name in teenagersDF.rdd.map(lambda p : f"Name : {p.name}").collect():
        print(name)

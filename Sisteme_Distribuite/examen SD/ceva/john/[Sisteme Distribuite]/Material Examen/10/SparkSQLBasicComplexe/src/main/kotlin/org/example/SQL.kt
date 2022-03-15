package org.example

import org.apache.spark.api.java.JavaRDD
import org.apache.spark.api.java.function.MapFunction
import org.apache.spark.sql.*
import org.apache.spark.sql.functions.col
import java.io.Serializable
import javax.xml.crypto.Data

class Person : Serializable{
    var name : String? = null
    var age = 0
}



fun main(args: Array<String>) {
    // configurarea si crearea sesiunii Spark SQL
    val sparkSession = SparkSession.builder()
        .appName("Java Spark SQL Example")
        .config("spark.master","local")
        .orCreate

    // initializarea unui DataFrame prin citirea unui JSON
    val df : Dataset<Row> = sparkSession.sqlContext().read().json("src/main/resources/people.json")

    // afisarea continutului DataFrame la consola
    df.show()

    // afisare schema DataFrame intr-o forma arborescenta
    df.printSchema()

    // selectarea coloanei nume si afisarea acesteia
    df.select("name").show()

    // selectarea tuturo datelor si incrementarea varstei cu 1
    df.select(col("name"), col("age").plus(1)).show()

    // selectarea coloanelor cu varsta > 21 ani
    df.filter(col("age").gt(21)).show()

    // numararea persoanelor dupa varsta
    df.groupBy("age").count().show()

    // Inregistrarea unui DataFrame ca un SQL View Temporar
    df.createOrReplaceTempView("people")

    // Utilizarea unei interogari sql pentru a selecta datele
    val sqlDF : Dataset<Row> = sparkSession.sql("SELECT * FROM people")
    sqlDF.show()

    // Inregistrarea unui DataFrame ca un SQL View global temporar
    df.createGlobalTempView("people")

    // Un SQL View global temporar este legat de o baza de date a sistemului : global_temp
    sparkSession.sql("SELECT * FROM global_temp.people").show()

    // Un view global temporar este vizibil intre sesiuni
    sparkSession.newSession().sql("SELECT * FROM global_temp.people").show()

    // Crearea seturilor de date (Dataset)
    val person0 = Person()
    person0.name = "Mihai"
    person0.age = 20
    val person1 = Person()
    person0.name = "Age"
    person0.age = 19

    // Este creat un codificator pentru bean-ul Java
    val personEncoder : Encoder<Person> = Encoders.bean(Person::class.java)

    // Se creeaza Dataset-ul de persoane
    val javaBeansDS : Dataset<Person> = sparkSession.createDataset(listOf(person0, person1), personEncoder)
    javaBeansDS.show()


    // Codificatoarele pentru tipurile primitive sunt furnizate de clasa Encoders
    val integerEncoder = Encoders.INT()
    val primiteDS : Dataset<Int> = sparkSession.createDataset(listOf(1, 2, 3), integerEncoder)
    val transformedDS = primiteDS.map(
        MapFunction{
            value : Int -> value + 1
        } as MapFunction<Int, Int>,
        integerEncoder
    )
    transformedDS.collect()
    transformedDS.show()


    // Dataframe-urile pot fi convertite intr-un Dataset
    val path = "src/main/resources/people.json"
    val peopleDS : Dataset<Person> = sparkSession.read().json(path).`as`(personEncoder)
    peopleDS.show()

    // Interoperabilitate cu RDD
    // Crearea unui RDD de obiecte Person dintr-un fisier text
    val peopleRDD : JavaRDD<Person> = sparkSession
        .read()
        .textFile("src/main/resources/people.txt")
        .javaRDD()
        .map{line ->
            val parts : List<String> = line.split(",")
            val person = Person()
            person.name = parts[0]
            person.age = parts[1].trim{it <= ' '}.toInt()
            person
        }

    // aplicarea unei scheme pe un RDD de Java Bean-uri pentru a obtine DataFrame
    val peopleDF : Dataset<Row> = sparkSession.createDataFrame(peopleRDD,Person::class.java)

    // inregistrarea DataFrame-ului ca un view temporar
    peopleDF.createOrReplaceTempView("people")

    // Selectarea persoanelor cu varsta intre 13 si 19 ani cu o interogare SQL
    val teenagerDF : Dataset<Row> = sparkSession.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19")

    // Coloanele dintr-un Row din rezultat pot fi accesate dupa indexul coloanei
    val stringEncoder = Encoders.STRING()
    val teenagerNamesByIndexDF = teenagerDF.map(
        MapFunction { row: Row -> "Name: " + row.getString(0) } as
                MapFunction<Row, String>,
        stringEncoder
    )
    teenagerNamesByIndexDF.show()

    // sau dupa numele coloanei
    val teenagerNamesByFieldDF = teenagerDF.map(
        MapFunction { row: Row -> "Name: " + row.getAs("name") } ,
        stringEncoder
    )
    teenagerNamesByFieldDF.show()
}


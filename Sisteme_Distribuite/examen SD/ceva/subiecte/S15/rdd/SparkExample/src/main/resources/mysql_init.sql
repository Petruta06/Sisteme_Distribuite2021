CREATE DATABASE IF NOT EXISTS sd_database;
USE sd_database;
CREATE TABLE IF NOT EXISTS Person (ID int PRIMARY KEY, LastName varchar(255), FirstName varchar(255), Age int);
INSERT INTO Person(ID, LastName, FirstName, Age) VALUES(1, "Popescu", "Ion", 20);
INSERT INTO Person(ID, LastName, FirstName, Age) VALUES(2, "Ionescu", "Mariana", 20);
INSERT INTO Person(ID, LastName, FirstName, Age) VALUES(3, "Codreanu", "Cristian", 20);
INSERT INTO Person(ID, LastName, FirstName, Age) VALUES(4, "Cantemir", "Roxana", 21);
INSERT INTO Person(ID, LastName, FirstName, Age) VALUES(5, "Anghel", "Vlad", 21);
INSERT INTO Person(ID, LastName, FirstName, Age) VALUES(6, "Bujor", "Florina", 22);
COMMIT;
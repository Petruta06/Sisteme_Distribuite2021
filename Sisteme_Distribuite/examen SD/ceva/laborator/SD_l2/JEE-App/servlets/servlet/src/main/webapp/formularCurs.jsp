<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Formular curs</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Formular student</h3>
		Introduceti datele despre student:
		<form action="./process-curs" method="post">
			Denumire curs: <input type="text" name="denumire" />
			<br />
			Profesor: <input type="text" name="profesor" />
			<br />
			Numar Ore: <input type="number" name="numarOre" />
			<br />
			<br />
			<button type="submit" name="submit">Trimite</button>
		</form>
	</body>
</html>
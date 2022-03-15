<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Actualizare informatii student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Actualizare informatii student</h3>
		Introduceti datele despre student:
		<form action="./update-curs" method="get">
			Denumire: <input type="text" name="denumire" />
			<br />
			Profesor: <input type="text" name="profesor" />
			<br />
			Numar de ore: <input type="number" name="numarOre" />
			<br />
			<br />
			<button type="submit" name="submit">Actualizare</button>
		</form>
	</body>
</html>
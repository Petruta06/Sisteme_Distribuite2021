<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Actualizare informatii student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Actualizare informatii student</h3>
		Introduceti datele despre student:
		<form action="./update-student" method="get">
			Nume: <input type="text" name="nume" />
			<br />
			Prenume: <input type="text" name="prenume" />
			<br />
			Varsta: <input type="number" name="varsta" />
			<br />
			<br />
			<button type="submit" name="submit">Actualizare</button>
		</form>
	</body>
</html>
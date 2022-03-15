<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Cautare student dupa nume</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Cautare student</h3>
		Introduceti datele despre student:
		<form action="./search-student" method="get">
			Nume: <input type="text" name="nume" />
			<button type="submit" name="submit">Cautare</button>
		</form>
	</body>
</html>
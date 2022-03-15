<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Cautare student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Cautare student</h3>
		Introduceti numele studentului student:
		<form action="./search-student" method="get">
			Nume: <input type="text" name="nume" />
			<br />
			<button type="submit" name="submit">Cautare</button>
		</form>
	</body>
</html>
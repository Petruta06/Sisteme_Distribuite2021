<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Stergere student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Stergere student</h3>
		Introduceti numele studentului student:
		<form action="./delete-student" method="get">
			Nume: <input type="text" name="nume" />
			<br />
			<button type="submit" name="submit">Stergere</button>
		</form>
	</body>
</html>
<html xmlns:jsp="http://java.sun.com/JSP/Page">
	<head>
		<title>Stergere student</title>
		<meta charset="UTF-8" />
	</head>
	<body>
		<h3>Stergere curs</h3>
		Introduceti numele studentului student:
		<form action="./delete-curs" method="get">
			Nume: <input type="text" name="denumire" />
			<br />
			<button type="submit" name="submit">Stergere</button>
		</form>
	</body>
</html>
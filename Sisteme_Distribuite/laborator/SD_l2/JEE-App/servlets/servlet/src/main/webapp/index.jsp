<%@ page contentType="text/html; charset=UTF-8" %>
<html>
	<head>
		<title>Meniu principal</title>
		<meta charset="utf-8" />
	</head>
	<body>
		<h1>Meniu principal</h1>
		<hr />
		<h3>Gestiune cont bancar</h3>
		<form action="./process-bank-operation" method="post">
			<fieldset label="operatiuni">
				<legend>Alegeti operatiunea dorita:</legend>
				<select name="operation">
					<option value="deposit">Depunere numerar</option>
					<option value="withdraw">Retragere numerar</option>
					<option value="balance">Interogare sold</option>
				</select>
				<br />
				<br />
				Introduceti suma: <input type="number" name="amount" />
				<br />
				<br />
				<button type="submit">Efectuare</button>
			</fieldset>
		</form>
		<hr />
		<h3>Baza de date cu studenti</h3>
		<a href="./formular.jsp">Adaugare student</a>
		<br />
		<a href="./fetch-student-list">Afisare lista studenti</a>
		<br>
		<a href="./search.jsp">Cautare student</a><br>
		<a href="./update.jsp">Actualizare inf dupa nume</a><br>
		<a href ="./delete.jsp">Stergere student </a> <br><br>

		<a href ="./formularCurs.jsp"> Adaugare curs </a><br>
		<a href ="./afisare-curs"> Vizualizare cursuri </a> <br>
		<a href ="./delete-curs.jsp"> Stergere curs </a> <br>
		<a href = "./update-curs.jsp"> Actualizare curs </a> <br>
	</body>
</html>
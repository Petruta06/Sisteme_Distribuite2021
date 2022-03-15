<html xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <title>Eliminare student</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <h2>Eliminare student</h2>

        <form action="./stergere-student" method="post">
        	<strong>Criteriu eliminare : </strong>
          	<select name = "cautator">
          		<option value="nume">Nume</option>
          		<option value="prenume">Prenume</option>
              	<option value="varsta">Varsta</option>
              	<option value="specializare">Specializare</option>
              	<option value="media">Media</option>
          	</select>
        	<br /><br/>

          	<strong>Valoare corespunzatoare :  </strong>
          	<input type="text" name="valoare" />
        	<br /><br/>

          	<button type="submit" name="submit">Cauta studentul</button>
        </form>
    </body>
</html>
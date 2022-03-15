<html xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <title>Cautare student</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <h2>Actualizare student</h2>
      	<br/>
      	<h3>Cautati studentul</h3>

        <form action="./update-student" method="post">
        	<strong>Criteriu cautare : </strong>
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
        	<br /><br/><br/><br>


        	<h3>Introduceti noile informatii</h3>


          	<strong>Element modificat : </strong>

          	<select name = "modificator">
          		<option value="_nume">Nume</option>
          		<option value="_prenume">Prenume</option>
              	<option value="_varsta">Varsta</option>
              	<option value="_specializare">Specializare</option>
              	<option value="_media">Media</option>
          	</select>
        	<br /><br/>

          <strong>Valoare corespunzatoare :  </strong>
          	<input type="text" name="valoare_noua" />


			<br/><br/><br/>
          	<button type="submit" name="submit">Modifica studentul</button>
        </form>
    </body>
</html>
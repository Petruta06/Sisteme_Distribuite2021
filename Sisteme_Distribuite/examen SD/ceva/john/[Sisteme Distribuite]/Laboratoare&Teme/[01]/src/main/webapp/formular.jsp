<html xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <title>Formular inscriere</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <h2>Formular inscriere</h2>
        <h3>Introduceti datele despre student</h3>

        <form action="./process-student" method="post">
        	<strong>Nume   :</strong>
          	<input type="text" name="nume" />
        	<br /><br/>

          	<strong>Prenume  :  </strong>
          	<input type="text" name="prenume" />
        	<br /><br/>

        	<strong>Varsta : </strong>
          	<input type="number" name="varsta" />
        	<br /><br />

          	<strong>Specializare : </strong>
          	<select name = "specializare">
          		<option value="Calculatoare">Calculatoare</option>
          		<option value="Tehnologia Informatiei">Tehnologia Informatiei</option>
              	<option value="Ingineria Sistemelor">Ingineria Sistemelor</option>
          	</select>
        	<br/><br/>

          	<strong>Media : </strong>
          	<input type="number" step ="0.1" name="media"/>
          	<br/><br/>

          	<button type="submit" name="submit">Trimite inscriere</button>
        </form>
    </body>
</html>
<html xmlns:jsp="http://java.sun.com/JSP/Page">
   <head>
      <title>Formular curs</title>
      <meta charset="UTF-8" />
   </head>
   <body>
      <h3>Formular curs</h3>
     <br/>
      <p>Introduceti datele despre curs:</p>
      <form action="./process-curs" method="post">
         <strong>Nume :</strong>&nbsp;
        	<input type="text" name="nume" /><br />
         <strong>Titular : <strong>&nbsp;
           	<input type="text" name="titular" /><br />
         <strong>Numar studenti: <strong>&nbsp;
           	<input type="number" name="nr_studenti" /><br />
           <strong>Evaluare finala </strong>: <strong>&nbsp;
           	<input type="number" name="evaluare" step = "0.1"/><br /><br />
         <button type="submit" name="submit">Trimite</button>
      </form>
   </body>
</html>
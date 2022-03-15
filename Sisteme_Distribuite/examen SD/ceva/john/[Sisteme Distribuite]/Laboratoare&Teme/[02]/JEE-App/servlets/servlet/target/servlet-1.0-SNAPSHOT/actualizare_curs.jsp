<html xmlns:jsp="http://java.sun.com/JSP/Page">
   <head>
      <title>Actualizare curs</title>
      <meta charset="UTF-8" />
   </head>
   <body>
      <h3>Actualizare curs</h3>
     <br/>
     <p><strong>Cautare Curs</strong></p>
      <form action="./update-curs" method="post">
         Selectati criteriul de cautare &nbsp;&nbsp;
         <select name="criterion_selector">
            <option value="c-id_curs">ID</option>
            <option value="c-nume_curs">Nume</option>
            <option value="c-titular_curs">Titular</option>
         </select>
         <br /> <br />
         Introduceti valoarea corespunzatoare
         <input type = "text" name = "c_input">
         <br /> <br /> <br/>
         <p><strong>Date de actualizare</strong></p>

         Selectati criteriul de actualizare &nbsp;&nbsp;
        <select name="criterion_updater">
            <option value="c-id_curs">ID</option>
            <option value="c-nume_curs">Nume</option>
            <option value="c-titular_curs">Titular</option>
          	<option value="c-numar_studenti">Numar studenti</option>
          	<option value="c-evaluare_medie">Evaluare medie</option>
         </select>
        <br/><br/>
         Introduceti valoarea corespunzatoare
         <input type = "text" name = "u_input">
         <br /> <br />
         <button type="submit" name="submit">Actualizare</button>
      </form>
   </body>
</html>
<html xmlns:jsp="http://java.sun.com/JSP/Page">
   <head>
      <title>Stergere curs</title>
      <meta charset="UTF-8" />
   </head>
   <body>
      <h3>Stergere curs</h3>
      <br />
      <p><strong>Cautare Curs</strong></p>
      <form action="./stergere-curs" method="post">

         Selectati criteriul de cautare &nbsp;&nbsp;
         <select name="criterion_selector">
            <option value="c-id_curs">ID</option>
            <option value="c-nume_curs">Nume</option>
            <option value="c-titular_curs">Titular</option>
         </select>
         <br /> <br />
         Introduceti valoarea corespunzatoare &nbsp;
         <input type = "text" name = "c_input">
         <br /><br />
         <button type="submit" name="submit">Eliminare curs</button>
      </form>
   </body>
</html>
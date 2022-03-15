<html xmlns:jsp="http://java.sun.com/JSP/Page">
   <head>
      <title>Actualizare student</title>
      <meta charset="UTF-8" />
   </head>
   <body>
      <h3>Actualizare student</h3>
     <br/>
     <p><strong>Cautare Student</strong></p>
      <form action="./actualizare-student" method="post">
         Selectati criteriul de cautare &nbsp;&nbsp;
         <select name="criterion_selector">
            <option value="c_id">ID</option>
            <option value="c_nume">Nume</option>
            <option value="c_prenume">Prenume</option>
         </select>
         <br /> <br />
         Introduceti valoarea corespunzatoare
         <input type = "text" name = "c_input">
         <br /> <br /> <br/>
         <p><strong>Date de actualizare</strong>
        </p>

         Selectati criteriul de actualizare &nbsp;&nbsp;
        <select name="criterion_updater">
            <option value="c_id">ID</option>
            <option value="c_nume">Nume</option>
            <option value="c_prenume">Prenume</option>
          	<option value="c_varsta">Varsta</option>
         </select>
        <br/><br/>
         Introduceti valoarea corespunzatoare
         <input type = "text" name = "u_input">
         <br /> <br />
         <button type="submit" name="submit">Actualizare</button>
      </form>
   </body>
</html>
<html xmlns:jsp="http://java.sun.com/JSP/Page">
   <head>
      <title>Stergere student</title>
      <meta charset="UTF-8" />
   </head>
   <body>
      <h3>Stergere student</h3>
      <br />
      <p><strong>Cautare Student</strong></p>
      <form action="./stergere-student" method="post">

         Selectati criteriul de cautare &nbsp;&nbsp;
         <select name="criterion_selector">
            <option value="c_id">ID</option>
            <option value="c_nume">Nume</option>
            <option vlaue="c_prenume">Prenume</option>
         </select>
         <br /> <br />
         Introduceti valoarea corespunzatoare &nbsp;
         <input type = "text" name = "c_input">
         <br /><br />
         <button type="submit" name="submit">Eliminare student</button>
      </form>
   </body>
</html>
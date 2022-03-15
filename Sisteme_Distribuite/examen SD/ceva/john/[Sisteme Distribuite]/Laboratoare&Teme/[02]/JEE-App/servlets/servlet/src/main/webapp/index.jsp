<html>
   <head>
      <title>Meniu principal</title>
      <meta charset="utf-8" />
   </head>
   <body>
      <h1>Meniu principal</h1>
      <h3>Gestiune cont bancar</h3>
      <form action="./process-bank-operation" method="post">
         <fieldset label="operatiuni" style = "width:300px">
            <legend>Alegeti operatiunea dorita:</legend>
            <select name="operation">
               <option value="deposit">Depunere numerar</option>
               <option value="withdraw">Retragere
                  numerar
               </option>
               <option value="balance">Interogare sold</option>
            </select>
            <br />
            <br />
            Introduceti suma: <input type="number" name="amount"
               />
            <br />
            <br />
            <button type="submit">Efectuare</button>
         </fieldset>
        <br/>
      </form>
      <hr width="25%" align="left">
      <h3>Baza de date cu studenti</h3>
      <p><a href="./formular.jsp">Adaugare student</a></p>
      <p><a href="./actualizare.jsp">Actualizare student</a></p>
      <p><a href="./stergere.jsp">Stergere student</a></p>
      <p><a href="./fetch-student-list">Afisare lista studenti</a></p>
      <br /><hr width="25%" align="left">
     <h3>Baza de date cu cursurile</h3>
      <p><a href="./formular_curs.jsp">Adaugare curs</a></p>
      <p><a href="./actualizare_curs.jsp">Actualizare curs</a></p>
      <p><a href="./stergere_curs.jsp">Stergere curs</a></p>
      <p><a href="./fetch-curs-list">Afisare lista cursuri</a></p>
   </body>
</html>
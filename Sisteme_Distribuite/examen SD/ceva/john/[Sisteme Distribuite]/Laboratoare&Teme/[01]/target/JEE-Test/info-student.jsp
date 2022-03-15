<html xmlns:jsp="http://java.sun.com/JSP/Page">

<head>
    <title>Cautare student</title>
</head>

<body>
    <h2>Informatii inscriere</h2>

    <!-- populare bean cu informatii din cererea HTTP -->
    <jsp:useBean id="studentBean" class="beans.Students" />

    <jsp:setProperty name="studentBean" property="nume" value='<%=request.getAttribute("nume") %>' />
    <jsp:setProperty name="studentBean" property="prenume" value='<%=request.getAttribute("prenume") %>' />
    <jsp:setProperty name="studentBean" property="varsta" value='<%=request.getAttribute("varsta") %>' />
    <jsp:setProperty name="studentBean" property="media" value='<%=request.getAttribute("media") %>' />
    <jsp:setProperty name="studentBean" property="specializare" value='<%=request.getAttribute("specializare") %>' />

    <!-- folosirea bean-ului pentru afisarea informatiilor -->
    <h3><strong>Urmatoarele informatii au fost introduse:</strong></h3>
        <ul type="bullet">
            <li>Nume:
                <jsp:getProperty name="studentBean" property="nume" />
            </li>
            <li>Prenume:
                <jsp:getProperty name="studentBean" property="prenume" />
            </li>
            <li>Varsta:
                <jsp:getProperty name="studentBean" property="varsta" />
            </li>
            <li>Anul nasterii:
                <%= request.getAttribute("anNastere")%>
            </li>
            <li>Medie:
                <jsp:getProperty name="studentBean" property="media" />
            </li>
            <li>Specializarea:
                <jsp:getProperty name="studentBean" property="specializare" />
            </li>
        </ul>

        <textarea property="textarea" cols = 60 rows = 40></textarea>
</body>

</html>
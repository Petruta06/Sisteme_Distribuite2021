import ejb.CursuriEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AfisareCursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pregatire EntityManager
        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        StringBuilder responseText = new StringBuilder();
        responseText.append("<h2>Lista cursuri</h2>");
        responseText.append("<table border='1'><thead><tr><th>ID</th><th>Denumire curs</th><th>Profesor</th><th>Numar Ore</th></thead>");
        responseText.append("<tbody>");

        // preluare date studenti din baza de date
        TypedQuery<CursuriEntity> query = em.createQuery("select curs from CursuriEntity curs", CursuriEntity.class);
        List<CursuriEntity> results = query.getResultList();
        for (CursuriEntity curs : results) {
            // se creeaza cate un rand de tabel HTML pentru fiecare student gasit
            responseText.append("<tr><td>" + curs.getId() + "</td><td>" +
                    curs.getDenumire() + "</td><td>" + curs.getProfesor() +
                    "</td><td>" + curs.getNumarOre() + "</td></tr>");
        }

        responseText.append("</tbody></table><br /><br /><a href='./'>Inapoi la meniul principal</a>");

        // inchidere EntityManager
        em.close();
        factory.close();

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());
    }
}

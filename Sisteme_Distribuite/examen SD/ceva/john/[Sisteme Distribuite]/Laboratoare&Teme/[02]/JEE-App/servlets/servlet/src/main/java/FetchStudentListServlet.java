import ejb.StudentEntity;
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

import static javax.xml.ws.RespectBindingFeature.ID;

public class FetchStudentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("StudentiDB");
        EntityManager em = factory.createEntityManager();
        StringBuilder responseText = new StringBuilder();
        responseText.append("<h2>Lista studenti</h2>");
        responseText.append("<table border='1'><thead><tr><th>ID</th><th>Nume</th><th>Prenume</th><th>Varsta</th></thead>");
        responseText.append("<tbody>");

        TypedQuery<StudentEntity> query = em.createQuery("select student from StudentEntity student", StudentEntity.class);
        List<StudentEntity> results = query.getResultList();
        for (StudentEntity student : results) {
            responseText.append("<tr><td>" + student.getId() + "</td><td>" +
                    student.getNume() + "</td><td>" +
                    student.getPrenume() +
                    "</td><td>" + student.getVarsta() + "</td></tr>");
        }
        responseText.append("</tbody></table><br /><br /><a href='./'>Inapoi la meniul principal</a>");

        em.close();
        factory.close();

        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());
    }
}
import ejb.CourseEntity;
import ejb.StudentEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

public class ProcessCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String nume = request.getParameter("nume");
        String titular = request.getParameter("titular");
        int nr_studenti = Integer.parseInt(request.getParameter("nr_studenti"));
        float evaluare = Float.parseFloat(request.getParameter("evaluare"));

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CursuriDB");
        EntityManager em = factory.createEntityManager();

        CourseEntity course = new CourseEntity();
        course.setId_curs(new Random().nextInt(1000));
        course.setNume_curs(nume);
        course.setTitular_curs(titular);
        course.setNumar_student(nr_studenti);
        course.setEvaluare_medie(evaluare);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(course);
        transaction.commit();

        em.close();
        factory.close();

        response.setContentType("text/html");
        response.getWriter().println("Datele au fost adaugate in baza de date." +
                "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
    }
}
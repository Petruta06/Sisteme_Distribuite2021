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

public class ProcessStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int varsta = Integer.parseInt(request.getParameter("varsta"));

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("StudentiDB");
        EntityManager em = factory.createEntityManager();

        StudentEntity student = new StudentEntity();
        student.setId(new Random().nextInt(1000));
        student.setNume(nume);
        student.setPrenume(prenume);
        student.setVarsta(varsta);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(student);
        transaction.commit();

        em.close();
        factory.close();

        response.setContentType("text/html");
        response.getWriter().println("Datele au fost adaugate in baza de date." +
                "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
    }
}
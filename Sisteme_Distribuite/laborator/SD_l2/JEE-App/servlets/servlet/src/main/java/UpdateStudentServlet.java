import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UpdateStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pregatire EntityManager
        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();
        //scot numele si prenumele introduse de utilizator
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int varsta = Integer.parseInt(request.getParameter("varsta"));

        StringBuilder responseText = new StringBuilder();
        /*scot toti studenti din BD*/
        TypedQuery<StudentEntity> query = em.createQuery(
                "select student from StudentEntity student", StudentEntity.class);
        List<StudentEntity> results = query.getResultList();
        int id =-1;
        for (StudentEntity student : results) {
            // se creeaza cate un rand de tabel HTML pentru fiecare student gasit
            if(nume.equals(student.getNume()))
            {
                id = student.getId();

            }
        }
        if(id == -1)
        {
            responseText.append("<h4>Studentul nu exista in BD!</h4><br>");
        }
        else
        {
            em.getTransaction().begin();
            StudentEntity s = em.find(StudentEntity.class, id);
            s.setPrenume(prenume);
            s.setVarsta(varsta);
            em.getTransaction().commit();
            responseText.append("<h4>Datele au fost actualizate in BD!</h4><br>");

        }


        responseText.append("<a href =index.jsp> Meniu principal </a> ");

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());
        /*inchid conexiunea*/
        em.close();
        factory.close();

    }
}
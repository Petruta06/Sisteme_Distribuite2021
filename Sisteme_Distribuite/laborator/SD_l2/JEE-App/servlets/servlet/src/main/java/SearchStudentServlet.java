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

public class SearchStudentServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pregatire EntityManager
        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();
        //scot numele si prenumele introduse de utilizator
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        // preluare date studenti din baza de date
        /*TypedQuery<StudentEntity> query = em.createQuery(
                "select student from StudentEntity student " +
                        "where student.getNume() = "+ nume, StudentEntity.class);*/

        StringBuilder responseText = new StringBuilder();
        TypedQuery<StudentEntity> query = em.createQuery("select student from StudentEntity student", StudentEntity.class);
        List<StudentEntity> results = query.getResultList();
        Boolean ok = true;
        int id;
        StudentEntity s;
         for (StudentEntity student : results) {
            // se creeaza cate un rand de tabel HTML pentru fiecare student gasit
            if(nume.equals(student.getNume()))
            {
                ok = false;
                responseText.append("<h2>Detalii despre" + nume+ "<h2>");
                responseText.append("Nume : "+student.getNume() + "<br>" +
                        "Prenume:"+ student.getPrenume() +
                        "<br>" +"Varsta:" + student.getVarsta() +"<br>"  );

            }
        }

        if(ok)
        {
            /*daca lista e vida, inseamna ca nu am studenti cu acest nume*/
           // response.sendError(404, "Nu exista student cu acest nume!");
            responseText.append("Studentul  " + nume + " nu  este in baza de date!<br>");
        }
        responseText.append("<a href =index.jsp> Meniu principal </a> ");

        // trimitere raspuns la client
        response.setContentType("text/html");
        response.getWriter().print(responseText.toString());
        em.close();
        factory.close();

    }
}
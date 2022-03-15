import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DeleteStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String search_criterion = request.getParameter("criterion_selector").split("_")[1];
        String search_value = request.getParameter("c_input");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("StudentiDB");
        EntityManager em = factory.createEntityManager();
        String sql_query = String.format("SELECT student FROM StudentEntity student WHERE %s = ?1","student." + search_criterion);

        TypedQuery<StudentEntity> query = em
                .createQuery(sql_query,StudentEntity.class)
                .setParameter(1,search_criterion.equals("id") ? Integer.parseInt(search_value) : search_value);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for(StudentEntity student : query.getResultList()){
            sql_query = "DELETE FROM StudentEntity student WHERE student.id = ?1";

            TypedQuery<StudentEntity> update_query = em.createQuery(sql_query,StudentEntity.class)
                    .setParameter(1, student.getId());
            try {
                int rowsUpdated = update_query.executeUpdate();
            }catch (javax.persistence.PersistenceException ex){
                response.getWriter().println("Persistency error!");}
        }

        transaction.commit();

        String result = "Inregistrarea a fost eliminata cu succes.<br /><br />" +
                "<a href='./'>Inapoi la meniul principal</a>";
        response.getWriter().println(result);
        em.close();
        factory.close();

    }
}
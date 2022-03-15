import ejb.CourseEntity;
import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String search_criterion = request.getParameter("criterion_selector").split("-")[1];
        String search_value = request.getParameter("c_input");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CursuriDB");
        EntityManager em = factory.createEntityManager();
        String sql_query = String.format("SELECT curs FROM CourseEntity curs WHERE %s = ?1","curs." + search_criterion);

        TypedQuery<CourseEntity> query = em
                .createQuery(sql_query, CourseEntity.class)
                .setParameter(1,search_criterion.equals("id_curs") ? Integer.parseInt(search_value) : search_value);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for(CourseEntity course : query.getResultList()){
            sql_query = "DELETE FROM CourseEntity curs WHERE curs.id_curs = ?1";

            TypedQuery<CourseEntity> update_query = em.createQuery(sql_query,CourseEntity.class)
                    .setParameter(1, course.getId_curs());
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
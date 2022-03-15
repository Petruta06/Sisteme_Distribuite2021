
import ejb.CourseEntity;
import ejb.StudentEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String search_criterion = request.getParameter("criterion_selector").split("-")[1];
        String search_value = request.getParameter("c_input");
        String update_criterion = request.getParameter("criterion_updater").split("-")[1];
        String update_value = request.getParameter("u_input");


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CursuriDB");
        EntityManager em = factory.createEntityManager();
        String sql_query = String.format("SELECT curs FROM CourseEntity curs WHERE %s = ?1","curs." + search_criterion);

        TypedQuery<CourseEntity> query = em
                .createQuery(sql_query, CourseEntity.class)
                .setParameter(1,search_criterion.equals("id_curs") ? Integer.parseInt(search_value) : search_value);

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for(CourseEntity course : query.getResultList()){
            sql_query = String.format("UPDATE CourseEntity curs SET %s= ?1 WHERE curs.id_curs = ?2","curs." + update_criterion);

            Boolean numeric_selection = update_criterion.equals("id_curs") || update_criterion.equals("numar_studenti") || update_criterion.equals("evaluare_medie");
            TypedQuery<CourseEntity> update_query = em.createQuery(sql_query,CourseEntity.class)
                    .setParameter(1, numeric_selection ? Integer.parseInt(update_value) : update_value)
                    .setParameter(2, course.getId_curs());
            try {
                int rowsUpdated = update_query.executeUpdate();
            }catch (javax.persistence.PersistenceException ex){
                response.getWriter().println("Persistency error!");
            }
        }

        transaction.commit();


        String result = "Datele au fost actualizate cu succes.<br /><br />" +
                "<a href='./'>Inapoi la meniul principal</a>";

        response.getWriter().println(result);
        em.close();
        factory.close();
    }
}
import ejb.CursuriEntity;
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


public class DeleteCursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pregatire EntityManager
        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();
        String denumire = request.getParameter("denumire");
        StringBuilder responseText = new StringBuilder();
        TypedQuery<CursuriEntity> q = em.createQuery(
                "select curs from CursuriEntity curs", CursuriEntity.class
        );
        List<CursuriEntity> lista = q.getResultList();
        /*verific daca cursul este in BD si retin id*/
        int id = -1;
        for(CursuriEntity c: lista)
        {
            if(denumire.equals(c.getDenumire()))
            {
                id = c.getId();
            }
        }
        if(id == -1)
        {
            /*mesaj in cazul in care nu este in BD*/
            responseText.append("<h3> Cursul nu este in BD! <h3>");
        }
        else
        {
            /*incep actualizare*/
            em.getTransaction().begin();
            CursuriEntity c = em.find(CursuriEntity.class, id);
            em.remove(c);
            em.getTransaction().commit();
            responseText.append("<h4>Datele au fost sterse din BD!</h4><br>");


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


import ejb.CursuriEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



public class UpdateCursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // pregatire EntityManager
        EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
        EntityManager em = factory.createEntityManager();

        /*scot datele introduse de utilizator*/
        String denumire = request.getParameter("denumire");
        String profesor = request.getParameter("profesor");
        int numarOre =Integer.parseInt(request.getParameter("numarOre"));

        /*folosit pentru scrierea datelor*/
        StringBuilder responseText = new StringBuilder();
        /*caut sa vad daca cursul identificat prin numele denumire este in BD*/
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
            /*actualizare inf*/
            CursuriEntity cursuriEntity = new CursuriEntity();
            cursuriEntity.setProfesor(profesor);
            cursuriEntity.setDenumire(denumire);
            cursuriEntity.setNumarOre(numarOre);
            /*incep actualizare*/
            em.getTransaction().begin();
            CursuriEntity c = em.find(CursuriEntity.class, id);
            c.setDenumire(denumire);
            c.setNumarOre(numarOre);
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
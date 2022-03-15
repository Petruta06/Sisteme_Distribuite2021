
import ejb.CursuriEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class ProcessCursServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // se citesc parametrii din cererea de tip POST
           String denumire = request.getParameter("denumire");
           String profesor = request.getParameter("profesor");
           int ore = Integer.parseInt(request.getParameter("numarOre"));

            // pregatire EntityManager
            EntityManagerFactory factory =   Persistence.createEntityManagerFactory("bazaDeDateSQLite");
            EntityManager em = factory.createEntityManager();

            CursuriEntity curs = new CursuriEntity();
            curs.setDenumire(denumire);

            curs.setProfesor(profesor);
            curs.setNumarOre(ore);

            // adaugare entitate in baza de date (operatiune de persistenta)
            // se face intr-o tranzactie
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(curs);
            transaction.commit();

            // inchidere EntityManager
            em.close();
            factory.close();

            // trimitere raspuns inapoi la client
            response.setContentType("text/html");
            response.getWriter().println("Datele au fost adaugate in baza de date." +
                    "<br /><br /><a href='./'>Inapoi la meniul principal</a>");
        }
    }



import beans.StudentBean;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class SearchStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException
    {
        /*citesc datele din fisier*/
        File file = new File("/home/ana/Desktop/SD_l1/student.xml");
// se returneaza un raspuns HTTP de tip 404 in cazul in carenu se gaseste fisierul cu date
        if (!file.exists()) {
            response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
            return;
        }
        XmlMapper xmlMapper = new XmlMapper();
        StudentBean bean = xmlMapper.readValue(file, StudentBean.class);

        /*extrag numele introdus de utilizator*/
        String nume = request.getParameter("nume");
        /*verific daca cele 2 stringuri sunt egale*/
        if(nume.equals(bean.getNume()))
        {
            /*afisez studentul*/
            request.getRequestDispatcher("./read-student").forward(request, response);
        }
        else
        {
            response.sendError(404, "Nu exista student cu acest nume!");
        }


    }
}

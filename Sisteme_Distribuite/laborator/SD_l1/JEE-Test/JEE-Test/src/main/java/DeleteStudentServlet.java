import beans.StudentBean;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteStudentServlet  extends HttpServlet {

    @Override
    protected  void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException
    {
        File f = new File("/home/ana/Desktop/SD_l1/student.xml");


        if(f.exists())
        {
            /*daca exista fisierul, acesta este sters*/
            f.delete();
            /*afisez mesaj*/
            request.getRequestDispatcher("./date_sterse.jsp").forward(request, response);
        }
        else
        {
            response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
            return;
        }
    }
}

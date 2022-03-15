import beans.StudentBean;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class UpdateStudentServlet extends HttpServlet {

    @Override
    protected  void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException
    {
        /*citesc datele modificate de utilizator*/
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        int varsta = Integer.parseInt(request.getParameter("varsta"));
        /*creez bean si adaug elementele*/
        StudentBean bean = new StudentBean();
        bean.setNume(nume);
        bean.setPrenume(prenume);
        bean.setVarsta(varsta);
        /*creez mapper*/
        XmlMapper mapper = new XmlMapper();
        /*sterg fisierul care era inainte*/
        File f = new File("/home/ana/Desktop/SD_l1/student.xml");
        f.delete();
        /*creez din nou*/
        //f.createNewFile();

        // serializare bean sub forma de string XML

        mapper.writeValue(new File("/home/ana/Desktop/SD_l1/student.xml"), bean);
       request.getRequestDispatcher("./date_actualizate.jsp").forward(request, response);
    }
}

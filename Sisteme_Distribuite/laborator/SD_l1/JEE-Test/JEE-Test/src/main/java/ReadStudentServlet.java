import beans.StudentBean;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class ReadStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // deserializare student din fisierul XML de pe disc
        File file = new File("/home/ana/Desktop/SD_l1/student.xml");

        if (!file.exists()) {
            response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
            return;
        }

        XmlMapper xmlMapper = new XmlMapper();
        ClassOfStudents bean = xmlMapper.readValue(file, ClassOfStudents.class);

        for(int i=0; i<2;i++)
        {
            request.setAttribute("nume", bean.getStudent(i).getNume());
            request.setAttribute("prenume", bean.getStudent(i).getPrenume());
            request.setAttribute("varsta", bean.getStudent(i).getVarsta());
        }


        // redirectionare date catre pagina de afisare a informatiilor studentului
        //request.getRequestDispatcher("./info-student.jsp").forward(request, response);
    }
}

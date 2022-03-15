import beans.Students;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.List;


public class ProcessStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String nume =         request.getParameter("nume");
        String prenume =      request.getParameter("prenume");
        int varsta =          Integer.parseInt(request.getParameter("varsta"));
        float media =         Float.parseFloat(request.getParameter("media"));
        String specializare = request.getParameter("specializare");


        int anCurent =  2020;
        int anNastere = anCurent - varsta;


        request.setAttribute("nume", nume);
        request.setAttribute("prenume", prenume);
        request.setAttribute("varsta", varsta);
        request.setAttribute("anNastere", anNastere);
        request.setAttribute("media",media);
        request.setAttribute("specializare",specializare);

        Students.StudentBean bean = new Students.StudentBean();

        bean.setNume(nume);
        bean.setPrenume(prenume);
        bean.setVarsta(varsta);
        bean.setMedia(media);
        bean.setSpecializare(specializare);

        XmlMapper mapper = new XmlMapper();
        File file = new File("/Users/ionut-alexandrubaltariu/student.xml");


        List<Students.StudentBean> students = mapper.readValue(file, new TypeReference<List<Students.StudentBean>>() {});

        students.add(bean);
        mapper.writeValue(file,students);

        request.setAttribute("textarea",this.TextFormat(students));

        request.getRequestDispatcher("./info-student.jsp").forward(request, response);
    }

    public String TextFormat(List<Students.StudentBean> students){
        String result = "";
        for(Students.StudentBean student : students){
            result += "Student\n";
            result += "Nume : " + student.getNume() + " | ";
            result += "Prenume : " + student.getPrenume() + " | ";
            result += "Varsta : " + student.getVarsta() + " | ";
            result += "Specializare : " + student.getSpecializare() + " | ";
            result += "Media : " + student.getMedia();
            result += "\n\n";
        }
        return result;
    }
}
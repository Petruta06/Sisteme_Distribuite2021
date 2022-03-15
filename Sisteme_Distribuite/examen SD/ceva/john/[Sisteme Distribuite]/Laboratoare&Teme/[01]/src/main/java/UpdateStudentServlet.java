import beans.Students;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateStudentServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String criterion = request.getParameter("cautator");
        String search    = request.getParameter("valoare");
        String element_to_modify = request.getParameter("modificator");
        String new_value = request.getParameter("valoare_noua");


        XmlMapper mapper = new XmlMapper();
        File file = new File("/Users/ionut-alexandrubaltariu/student.xml");

        List<Students.StudentBean> students = mapper.readValue(file, new TypeReference<List<Students.StudentBean>>() {});
        List<Students.StudentBean> valid_searches = new ArrayList<>();

        Students.StudentBean valid = null;
        element_to_modify = element_to_modify.toLowerCase();


        for (Students.StudentBean student : students){
            String candidate = this.SearchByField(student,criterion).toLowerCase();
            if(candidate != null) {
                if (candidate.equals(search.toLowerCase())) {
                    valid = student;
                    valid.GetTheFunction(element_to_modify).setter(new_value);
                }
                valid_searches.add(student);
            }
        }

        request.setAttribute("nume", valid.getNume());
        request.setAttribute("prenume", valid.getPrenume());
        request.setAttribute("varsta", valid.getVarsta());
        request.setAttribute("anNastere", 2020 - valid.getVarsta());
        request.setAttribute("media",valid.getMedia());
        request.setAttribute("specializare",valid.getSpecializare());

        mapper.writeValue(file,valid_searches);
        request.getRequestDispatcher("./info-student.jsp").forward(request, response);
    }

    String SearchByField(Students.StudentBean student, String criterion){
        criterion = criterion.toLowerCase();
        if(criterion.equals("nume")){
            return student.getNume();
        }
        if(criterion.equals("prenume")){
            return student.getPrenume();
        }
        if(criterion.equals("varsta")){
            return Integer.toString(student.getVarsta());
        }
        if(criterion.equals("media")){
            return Float.toString(student.getMedia());
        }
        if(criterion.equals("specializare")){
            return student.getSpecializare();
        }
        return null;
    }

}
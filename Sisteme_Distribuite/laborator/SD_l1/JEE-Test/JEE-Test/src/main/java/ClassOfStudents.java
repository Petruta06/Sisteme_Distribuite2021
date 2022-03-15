import beans.StudentBean;

public class ClassOfStudents implements java.io.Serializable{
    /* un array in care sa retin studentii introdusi*/
    private StudentBean []students = new StudentBean[100];
    /*numarul de studenti*/
    private int numberOfStudents = 0;

    public void addStudent(StudentBean s)
    {
        /*introduc studentul in array si imc nr de studenti*/
        students[numberOfStudents] = s;
        numberOfStudents++;
    }
    public StudentBean [] getArray()
    {
        return this.students;
    }
    public int getNumberOfStudents()
    {
        return numberOfStudents;
    }
    public StudentBean getStudent(int i)
    {
        if(i<numberOfStudents)
        {
            return students[i];
        }

        else
        {
            return null;
        }

    }

}

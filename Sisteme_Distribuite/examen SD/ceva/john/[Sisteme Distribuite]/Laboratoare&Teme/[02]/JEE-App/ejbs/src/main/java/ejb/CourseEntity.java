package ejb;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cursuri")
public class CourseEntity {
    @Column(name = "id_curs")
    @Id
    private int id_curs;

    @Column(name = "nume_curs")
    private String nume_curs;


    @Column(name = "titular_curs")
    private String titular_curs;

    @Column(name = "numar_studenti")
    private int numar_student;

    @Column(name = "evaluare_medie")
    private float evaluare_medie;

    public CourseEntity(){

    }

    public int getId_curs(){ return id_curs; }
    public String getNume_curs(){ return nume_curs; }
    public String getTitular_curs() { return titular_curs; }
    public int getNumar_student() { return numar_student; }
    public float getEvaluare_medie() { return evaluare_medie; }

    public void setId_curs(int id) { this.id_curs = id; }
    public void setNume_curs(String nume){ this.nume_curs = nume; }
    public void setTitular_curs(String titular){ this.titular_curs = titular; }
    public void setNumar_student(int numar_student) {this.numar_student = numar_student; }
    public void setEvaluare_medie(float evaluare_medie) { this.evaluare_medie = evaluare_medie; }

}

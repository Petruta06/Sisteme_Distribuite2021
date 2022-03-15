package ejb;


import javax.persistence.*;

@Entity
@Table(name = "studenti")
public class StudentEntity {

    @Column(name = "id")
    @Id
    private int id;

    @Column(name = "nume")
    private String nume;

    @Column(name = "prenume")
    private String prenume;

    @Column(name = "varsta")
    private int varsta;

    public StudentEntity(){

    }

    public int getId(){ return id; }
    public String getNume(){ return nume; }
    public String getPrenume() { return prenume; }
    public int getVarsta() { return varsta; }

    public void setNume(String nume){ this.nume = nume; }
    public void setPrenume(String prenume){ this.prenume = prenume; }
    public void setId(int id) { this.id = id; }
    public void setVarsta(int varsta) {this.varsta = varsta; }


}

package ejb;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class CursuriEntity {
    @Id
    @GeneratedValue
    private int id;
    private String denumire;
    private String profesor;
    private int numarOre;

    public CursuriEntity()
    {

    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getDenumire()
    {
        return denumire;
    }
    public void setDenumire(String denumire)
    {
        this.denumire = denumire;
    }

    public String getProfesor()
    {
        return profesor;
    }
    public void setProfesor(String profesor)
    {
        this.profesor = profesor;
    }

    public int getNumarOre()
    {
        return numarOre;
    }

    public void setNumarOre(int numarOre)
    {
        this.numarOre = numarOre;
    }

}

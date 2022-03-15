package beans;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "studenti")
public class Students implements Serializable {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "student")
    private List<StudentBean> studenti = new ArrayList<>(0);

    public void setStudenti(List<StudentBean> students){
        this.studenti = students;
    }

    public List<StudentBean> getStudenti(){
        return this.studenti;
    }

    @JacksonXmlRootElement(localName = "student")
    static public class StudentBean implements java.io.Serializable {
        @JacksonXmlElementWrapper(useWrapping = true)
        private String nume = null;
        private String prenume = null;
        private int varsta = 0;
        private float media = 0;
        private String specializare = null;

        public StudentBean(){ }

        public String getNume(){
            return this.nume;
        }
        public String getPrenume() { return this.prenume; }
        public int getVarsta() {
            return this.varsta;
        }
        public float getMedia() { return this.media; }
        public String getSpecializare() { return this.specializare; }


        public void setNume(String _nume) { this.nume = _nume; }
        public void setPrenume(String _prenume) {
            this.prenume = _prenume;
        }
        public void setVarsta(int _varsta) {
            this.varsta = _varsta;
        }
        public void setMedia(float _media) { this.media = _media; }
        public void setSpecializare(String _specializare) {this.specializare = _specializare; }


        @FunctionalInterface
        public interface PointerToFunction{
            void setter(String _value);
        }

        public PointerToFunction GetTheFunction(String _type){
            if(_type.equals("_nume".toLowerCase())){
                return (String value) -> this.setNume(value);
            }
            if(_type.equals("_prenume".toLowerCase())){
                return (String value) -> this.setPrenume(value);
            }
            if(_type.equals("_varsta".toLowerCase())){
                return (String value) -> this.setNume(value);
            }
            if(_type.equals("_media".toLowerCase())){
                return (String value) -> this.setMedia(Float.parseFloat(value));
            }
            if(_type.equals("_specializare".toLowerCase())){
                return (String value) -> this.setSpecializare(value);
            }
            return null;
        }
    }
}

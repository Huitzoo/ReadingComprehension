package beans;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="alumno")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id","idMaestro","grupo"})
public class Alumno {
        
        private int id;
	private int idMaestro;
        private String grupo;
                
        public Alumno(){
        }

        public Alumno(int id, int idMaestro, String grupo) {
            super();
            this.id = id;
            this.idMaestro = idMaestro;
            this.grupo = grupo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdMaestro() {
            return idMaestro;
        }

        public void setIdMaestro(int idMaestro) {
            this.idMaestro = idMaestro;
        }

        public String getGrupo() {
            return grupo;
        }

        public void setGrupo(String grupo) {
            this.grupo = grupo;
        }

        @Override
        public String toString() {
            return idMaestro + " " + grupo ;
        }
 
}
    


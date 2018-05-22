package beans;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="maestro")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id","email","escuela","asignatura"})

public class Maestro {

        private int id;
        private String email;
        private String escuela;
        private String asignatura;


        public Maestro(){

        }

        public Maestro(int id, String[] datos){
            super();
            this.id = id;
            this.email = datos[6];
            this.escuela = datos[7];
            this.asignatura = datos[8];
            
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getEscuela() {
            return escuela;
        }

        public void setEscuela(String escuela) {
            this.escuela = escuela;
        }

        public String getAsignatura() {
            return asignatura;
        }

        public void setAsignatura(String asignatura) {
            this.asignatura = asignatura;
        }
        
        @Override
        public String toString() {
            return email + " " + asignatura;
            //String[] salida = out.split(" ");
            //return salida;
        }
        
}

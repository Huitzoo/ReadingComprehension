package beans;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="maestro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Maestro {

        private int id;
        private String email;
        private String escuela;
        private String especialidad;

        public Maestro(){

        }

        public Maestro(int id, String[] datos){
            super();
            this.id = id;
            this.email = datos[0];
            this.escuela = datos[2];
            this.especialidad = datos[1];
            
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

        public String getEspecialidad() {
            return especialidad;
        }

        public void setEspecialidad(String especialidad) {
            this.especialidad = especialidad;
        }
        
        @Override
        public String toString() {
            return email + " " + especialidad;
            //String[] salida = out.split(" ");
            //return salida;
        }
        
}

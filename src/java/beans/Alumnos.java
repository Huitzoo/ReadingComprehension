package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "alumnos")
public class Alumnos {
        
        private List<Alumno> alumnos;
        
        @XmlElement(name="alumno")
        public List<Alumno> getAlumno(){
            return alumnos;
        }

        public void setAlumno(List<Alumno> alumnos){
            this.alumnos = alumnos;
        }
        //diego riuz, victor eduard munjica, gerardo montoya, 
        @Override
        public String toString() {
            return alumnos.toString();
        }
}



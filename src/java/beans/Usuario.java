package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "usuarios")
public class Usuario {
    
        private List<Persona> usuarios;
 
        @XmlElement(name="persona")
        public List<Persona> getUsuarios(){
            return usuarios;
        }

        public void setUsuarios(List<Persona> usuarios){
            this.usuarios = usuarios;
        }

        @Override
        public String toString() {
            return usuarios.toString();
        }
}



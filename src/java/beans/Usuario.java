package beans;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import beans.Persona;

@XmlRootElement
public class Usuario {
        Persona persona;
        
        public Persona getPersona(){
            return persona;
        }
        @XmlElement
        public void setPersona(Persona persona){
            this.persona = persona;
        }
}



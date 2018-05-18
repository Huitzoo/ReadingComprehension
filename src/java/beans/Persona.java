package beans;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Persona {
        
        int id;
        int rol;
	String nombre;
        String contra;
        String apellidos;
        String nombre_de_usuario;
        
        public int getId() {
		return id;
	}
        
        @XmlElement
        public void setId(int id) {
		this.id = id;
	}
        
        public int getRol() {
		return id;
	}
        
        @XmlElement
        public void setRol(int rol) {
               this.rol=rol;
        }
        
        public int getNombre() {
		return id;
	}
        
        @XmlElement
        public void setNombre(String nombre) {
             this.nombre = nombre;
        }

        public int getContra() {
		return id;
	}
        
        @XmlElement
        public void setContra(String contra) {
            this.contra = contra;
	}
        
        public int getApellidos() {
		return id;
        }
        
        @XmlElement
        public void setApellidos(String apellidos) {
            this.apellidos=apellidos;
        }
        
        public String getNombre_de_usuario() {
		return nombre_de_usuario;
        }
        
        @XmlElement
        public void setNombre_de_usuario(String nombre_de_usuario) {
            this.nombre_de_usuario=nombre_de_usuario;
        }
}



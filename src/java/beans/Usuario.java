package beans;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
        
        int id;
        int rol;
	String name;
        String contra;
        String apellidos;
        String nombre_de_usuario;
        
        public int getId() {
		return id;
	}
        
	@XmlElement
	public void setRol(int age) {
		this.age = rol;
	}
        public int getAge() {
		return age;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}
        
        public int getAge() {
		return age;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}
}



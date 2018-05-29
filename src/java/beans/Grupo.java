package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="grupo")
public class Grupo {
    
    int id;
    int maestro;
    String nombre;
    int alumnos;

    public Grupo(int id, int maestro, String nombre, int alumnos) {
        this.id = id;
        this.maestro = maestro;
        this.nombre = nombre;
        this.alumnos = alumnos;
    }

    public Grupo() {
        
    }

    public int getId() {
        return id;
    }
    
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public int getMaestro() {
        return maestro;
    }
    
    @XmlElement
    public void setMaestro(int maestro) {
        this.maestro = maestro;
    }

    public String getNombre() {
        return nombre;
    }
    
    @XmlElement
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAlumnos() {
        return alumnos;
    }
    
    @XmlElement
    public void setAlumnos(int alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        return "Grupo{" + "id=" + id + ", maestro=" + maestro + ", nombre=" + nombre + ", alumnos=" + alumnos +'}';
    }
    
    
}

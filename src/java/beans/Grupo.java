package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="grupo")
public class Grupo {
    
    int id;
    String maestro;
    String nombre;
    String asignatura;
    int alumnos;

    public Grupo(int id, String maestro, String nombre, int alumnos, String asignatura) {
        this.id = id;
        this.maestro = maestro;
        this.nombre = nombre;
        this.alumnos = alumnos;
        this.asignatura = asignatura;
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

    public String getMaestro() {
        return maestro;
    }
    
    @XmlElement
    public void setMaestro(String maestro) {
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

    public String getAsignatura() {
        return asignatura;
    }
    
    @XmlElement
    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }
    
    
    @Override
    public String toString() {
        return id + " " + maestro + " " + nombre + " " + alumnos + " " + asignatura;
    }
    
    
}

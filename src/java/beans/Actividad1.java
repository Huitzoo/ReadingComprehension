/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author huitz
 */
@XmlRootElement(name="actividad1")

public class Actividad1 {
    
    int id;
    int idMaestro;
    String nombre;
    String pathTexto;
    String pathAudio;
    String pathImagen;
    Preguntas preguntas;

    

    public Actividad1(int id,List<String> datos, Preguntas preguntas) {
        this.id = id;
        this.idMaestro = Integer.parseInt(datos.get(2));
        this.pathTexto = datos.get(0);
        this.pathAudio = datos.get(1);
        this.pathImagen = datos.get(3);
        this.preguntas = preguntas;
        this.nombre = datos.get(4);
    }
    
    public Actividad1() {
    
    }

    public String getNombre() {
        return nombre;
    }
    
    @XmlElement
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public String getPathImagen() {
        return pathImagen;
    }
    
    @XmlElement
    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }
    
    public int getId() {
        return id;
    }
    @XmlElement
    public void setId(int id) {
        this.id = id;
    }

    public int getIdMaestro() {
        return idMaestro;
    }
    
    @XmlElement
    public void setIdMaestro(int idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getPathTexto() {
        return pathTexto;
    }

    
    @XmlElement
    public void setPathTexto(String pathTexto) {
        this.pathTexto = pathTexto;
    }

    public String getPathAudio() {
        return pathAudio;
    }

    
    @XmlElement
    public void setPathAudio(String pathAudio) {
        this.pathAudio = pathAudio;
    }

    public Preguntas getPreguntas() {
        return preguntas;
    }

    
    @XmlElement
    public void setPreguntas(Preguntas preguntas) {
        this.preguntas = preguntas;
    }

    @Override
    public String toString() {
        return "Actividad1{" + "id=" + id + ", idMaestro=" + idMaestro + ", pathTexto=" + pathTexto + ", pathAudio=" + pathAudio + ", pathImagen=" + pathImagen + ", preguntas=" + preguntas + '}';
    }
    
}

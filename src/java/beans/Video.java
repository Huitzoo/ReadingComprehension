/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author huitz
 */
public class Video {
    
    int id;
    int maestro;
    String pathVideo;
    int numeroActividad;

    public Video(int id, int maestro, String pathVideo, int numeroActividad) {
        this.id = id;
        this.maestro = maestro;
        this.pathVideo = pathVideo;
        this.numeroActividad = numeroActividad;
    }

    public Video() {
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

    public String getPathVideo() {
        return pathVideo;
    }
    
    @XmlElement
    public void setPathVideo(String pathVideo) {
        this.pathVideo = pathVideo;
    }

    public int getNumeroActividad() {
        return numeroActividad;
    }
    
    @XmlElement
    public void setNumeroActividad(int numeroActividad) {
        this.numeroActividad = numeroActividad;
    }

    @Override
    public String toString() {
        return "Video{" + "id=" + id + ", maestro=" + maestro + ", pathVideo=" + pathVideo + ", numeroActividad=" + numeroActividad + '}';
    }
    
    
}

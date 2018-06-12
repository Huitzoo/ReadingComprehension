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
public class Actividad2 {
    int id;
    int idMaestro;
    String name;
    Palabras palabras;

    public Actividad2(int id, int idMaestro, String name, Palabras palabras) {
        this.id = id;
        this.idMaestro = idMaestro;
        this.name = name;
        this.palabras = palabras;
    }

    public Actividad2() {
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

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public Palabras getPalabras() {
        return palabras;
    }

    @XmlElement
    public void setPalabras(Palabras palabras) {
        this.palabras = palabras;
    }
    
    
}

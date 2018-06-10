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
@XmlRootElement(name="actividades1")
public class Actividades1 {
    List<Actividad1> actividades;

    public Actividades1() {
    }

    public Actividades1(List<Actividad1> actividades) {
        this.actividades = actividades;
    }
    @XmlElement(name="actividad1")
    public List<Actividad1> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad1> actividades) {
        this.actividades = actividades;
    }
    
}

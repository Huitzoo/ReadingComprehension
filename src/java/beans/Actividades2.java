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
@XmlRootElement(name = "actividades2")
public class Actividades2 {
    List<Actividad2> actividades;

    public Actividades2() {
    }

    public Actividades2(List<Actividad2> actividades) {
        this.actividades = actividades;
    }
    
    @XmlElement(name="actividad2")
    public List<Actividad2> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad2> actividades) {
        this.actividades = actividades;
    }
    
}

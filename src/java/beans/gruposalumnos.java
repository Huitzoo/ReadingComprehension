/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="gruposalumnos")
public class gruposalumnos {
    List<grupos_alumnos> gruposAlumnos;

    public gruposalumnos(List<grupos_alumnos> gruposAlumnos) {
        this.gruposAlumnos = gruposAlumnos;
    }

    public gruposalumnos() {
    }

    @XmlElement
    public List<grupos_alumnos> getGruposAlumnos() {
        return gruposAlumnos;
    }

    public void setGruposAlumnos(List<grupos_alumnos> gruposAlumnos) {
        this.gruposAlumnos = gruposAlumnos;
    }   
    
}

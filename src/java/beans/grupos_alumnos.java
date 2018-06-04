/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="grupo_alumnos")
public class grupos_alumnos {
    int idGrupo;
    List<Integer> idAlumnos;

    public grupos_alumnos() {
    }
    
    public grupos_alumnos(int idGrupo, List<Integer> idAlumnos) {
        this.idGrupo = idGrupo;
        this.idAlumnos = idAlumnos;
    }

    public int getIdGrupo() {
        return idGrupo;
    }
    
    @XmlElement
    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }
    
    public List<Integer> getIdAlumnos() {
        return idAlumnos;
    }

    @XmlElement
    public void setIdAlumnos(List<Integer> idAlumnos) {
        this.idAlumnos = idAlumnos;
    }

    @Override
    public String toString() {
        return "grupos_alumnos{" + "idGrupo=" + idGrupo + ", idAlumnos=" + idAlumnos + '}';
    }
    
}

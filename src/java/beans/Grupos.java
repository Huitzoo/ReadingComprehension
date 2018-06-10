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
@XmlRootElement(name="grupos")
public class Grupos {
    
    List<Grupo> grupo;

    public Grupos(List<Grupo> grupo) {
        this.grupo = grupo;
    }

    public Grupos() {
    }
    
    @XmlElement(name="grupo")
    public List<Grupo> getGrupo() {
        return grupo;
    }
    
    public void setGrupo(List<Grupo> grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return grupo.toString();
    }
    
}

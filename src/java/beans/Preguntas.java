/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="preguntas")
public class Preguntas {
    List<String> preguntas;

    public Preguntas() {
    }

    public Preguntas(List<String> preguntas) {
        this.preguntas = preguntas;
    }
    
    @XmlElement(name="pregunta")
    public List<String> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<String> preguntas) {
        this.preguntas = preguntas;
    }
              
}

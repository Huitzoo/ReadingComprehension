/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="palabras")
public class Palabras {
    List<String> palabras;

    public Palabras(List<String> palabras) {
        this.palabras = palabras;
    }
    
    public Palabras() {
        
    }
    
    @XmlElement(name="palabra")
    public List<String> getPalabras() {
        return palabras;
    }

    public void setPalabras(List<String> palabras) {
        this.palabras = palabras;
    }
    
}

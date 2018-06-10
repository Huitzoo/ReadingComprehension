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

@XmlRootElement(name = "maestros")
public class Maestros {

        private List<Maestro> maestros;

        
        @XmlElement(name="maestro")
        public List<Maestro> getMaestros(){
            return maestros;
        }
        
        public void setMaestros(List<Maestro> maestros){
            this.maestros = maestros;
        }
        
        @Override
        public String toString() {
            return maestros.toString();
        }
}

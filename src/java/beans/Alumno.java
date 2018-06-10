package beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="alumno")

public class Alumno {
        
        private int id;
	private int idGrupo;
	private String nombreMaestro;
        private String grupo;
        private String ano;
        
        public Alumno(){
        }

        public Alumno(int id, String[] datos) {
            super();
            this.id = id;
            this.idGrupo = Integer.parseInt(datos[3]);
            this.nombreMaestro = datos[2];
            this.grupo = datos[1];
            this.ano = datos[0];
        }

        public int getId() {
            return id;
        }
        
        @XmlElement
        public void setId(int id) {
            this.id = id;
        }

        public int getIdGrupo() {
            return idGrupo;
        }
        
        @XmlElement
        public void setIdGrupo(int idGrupo) {
            this.idGrupo = idGrupo;
        }

        
        public String getNombreMaestro() {
            return nombreMaestro;
        }
        
        @XmlElement
        public void setNombreMaestro(String nombreMaestro) {
            this.nombreMaestro = nombreMaestro;
        }

        public String getGrupo() {
            return grupo;
        }
        
        @XmlElement
        public void setGrupo(String grupo) {
            this.grupo = grupo;
        }

        public String getAno() {
            return ano;
        }
        
        @XmlElement
        public void setAno(String ano) {
            this.ano = ano;
        }

        @Override
        public String toString() {
            return  nombreMaestro + " " + grupo + " "+ idGrupo +" "+ ano;
        }
}
    


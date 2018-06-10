package beans;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="persona")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id","rol","nombre","contra","apellidos","nombre_de_usuario","nacimiento","sexo","edad"})
public class Persona {

        private int id;
        private int rol;
        private String nombre;
        private String contra;
        private String apellidos;
        private String nombre_de_usuario;
        private String nacimiento;
        private String sexo;
        private int edad;

        public Persona(){

        }
        public Persona(int id, int rol, String[] datos,int edad){
            //String nombre, String apellidos, String nombre_de_usuario, String contra, String sexo, String nacimiento
            super();
            this.id = id;
            this.rol = rol;
            this.nombre = datos[0];
            this.apellidos = datos[1];
            this.contra = datos[2];
            this.nombre_de_usuario = datos[3];
            this.edad = edad;
            this.sexo = datos[4];
            this.nacimiento = datos[5];

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRol() {
            return rol;
        }

        public void setRol(int rol) {
            this.rol = rol;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getContra() {
            return contra;
        }

        public void setContra(String contra) {
            this.contra = contra;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getNombre_de_usuario() {
            return nombre_de_usuario;
        }

        public void setNombre_de_usuario(String nombre_de_usuario) {
            this.nombre_de_usuario = nombre_de_usuario;
        }

        public String getNacimiento() {
            return nacimiento;
        }

        public void setNacimiento(String nacimiento) {
            this.nacimiento = nacimiento;
        }

        public String getSexo() {
            return sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        
        @Override
        public String toString(){
            return id + " " + nombre + " " + apellidos + " " + nombre_de_usuario + " "+ nacimiento +" "+ edad;
            //String[] salida = out.split(" ");
            //return salida;
        }


}

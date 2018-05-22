package funciones;

import beans.Alumno;
import beans.Alumnos;
import beans.Persona;
import beans.Usuario;
import java.util.List;
import static funciones.obtenerXML.obtenerListaUsuarios;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class crudAlumno {
    
    public static boolean crearAlumno(String path,int maestro,int id,String grupo) throws JAXBException{
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos alumnos = (Alumnos)unmarshaller.unmarshal(configFile);
        Alumno alumno = new Alumno(id,maestro,grupo);
        List<Alumno> personaList = alumnos.getAlumno();
        personaList.add(alumno);
        alumnos.setAlumno(personaList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(alumnos, configFile);
        return true;
    }    
    
    public static boolean eliminarAlumno(String path,List<Alumno> alumnosList) throws JAXBException{
        path = path + "/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos alumnos = (Alumnos)unmarshaller.unmarshal(configFile);
        alumnos.setAlumno(alumnosList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(alumnos, configFile);
        return true;
    }    
    
}

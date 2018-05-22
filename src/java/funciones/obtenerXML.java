package funciones;

import beans.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public final class obtenerXML {
    
    public static List<Persona> obtenerListaUsuarios(String path) throws JAXBException{
        path = path+"/personas.xml";
        File configFile = new File(path);
        System.out.println(configFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Usuario usuario = (Usuario) unmarshaller.unmarshal(configFile);
        List<Persona> personaList = usuario.getUsuarios();
        return personaList;
    }
    
    public static List<Maestro> obtenerListaMaestros(String path) throws JAXBException{
        path = path+"/maestro.xml";
        File configFile = new File(path);
        System.out.println(configFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(Maestros.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Maestros maestros = (Maestros) unmarshaller.unmarshal(configFile);
        List<Maestro> maestrosList = maestros.getMaestros();
        return maestrosList;
    }
    
    public static List<Alumno> obtenerListaAlumnos(String path) throws JAXBException{
        path = path+"/alumno.xml";
        File configFile = new File(path);
        System.out.println(configFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos usuario = (Alumnos) unmarshaller.unmarshal(configFile);
        List<Alumno> alumnosList = usuario.getAlumno();
        return alumnosList;
    }
    
    public static List<Alumno> obtenerListaAlumnos(String path, int idMaestro) throws JAXBException{
        path = path+"/alumno.xml";
        File configFile = new File(path);
        System.out.println(configFile);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos usuario = (Alumnos) unmarshaller.unmarshal(configFile);
        List<Alumno> alumnosList = usuario.getAlumno();
        //Ahora se busca por maestros a los alumnos
        List<Alumno> alumnos = (List<Alumno>) alumnosList.stream().filter((alumno) -> (alumno.getIdMaestro() == idMaestro));
        return alumnos;
    }
    
}

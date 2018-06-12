 package cruds;

import beans.Alumno;
import beans.Alumnos;
import beans.Persona;
import beans.Usuario;
import consultas.obtenerXML;
import java.util.List;
import static consultas.obtenerXML.obtenerListaUsuarios;
import java.io.File;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class crudAlumno {
    
    public static boolean crearAlumno(String path,String[] datos, int id) throws JAXBException{
        path = path + "/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos alumnos = (Alumnos)unmarshaller.unmarshal(configFile);
        Alumno alumno = new Alumno(id,datos);
        List<Alumno> personaList = alumnos.getAlumno();
        if(personaList == null){
            personaList = new ArrayList<>();
        }
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

    public static void editarAlumno(String path, String[] datosA, int id) throws JAXBException {
        //ciclo,grupo,request.getParameter("idMaestroAlumnos"),request.getParameter("grupo")
        List<Alumno> alumnos = obtenerXML.obtenerListaAlumnos(path);
        path = path + "/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos alumno = (Alumnos) unmarshaller.unmarshal(configFile);
        for(Alumno a : alumnos){
            if(a.getId() == id){
                a.setAno(datosA[0]);
                a.setGrupo(datosA[1]);
                a.setNombreMaestro(datosA[2]);
                a.setIdGrupo(Integer.parseInt(datosA[3]));
            }
        }
        alumno.setAlumno(alumnos);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(alumno, configFile); 
    }
    
    public static void editarAlumno(String path, List<String> datosA, int id,List<Alumno> alumnos,int bandera) throws JAXBException {
        //ciclo,grupo,request.getParameter("idMaestroAlumnos"),request.getParameter("grupo")
        path = path + "/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos alumno = (Alumnos) unmarshaller.unmarshal(configFile);
        for(Alumno a : alumnos){
            if(a.getIdGrupo() == id){
                a.setGrupo(datosA.get(0));
                if(bandera == 1){
                    a.setNombreMaestro(datosA.get(1));
                }
           }
        }
        alumno.setAlumno(alumnos);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(alumno, configFile); 
    }
    public static void editarAlumno(String path,List<Alumno> alumnos) throws JAXBException {
        //ciclo,grupo,request.getParameter("idMaestroAlumnos"),request.getParameter("grupo")
        path = path + "/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos alumno = (Alumnos) unmarshaller.unmarshal(configFile);
        alumno.setAlumno(alumnos);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(alumno, configFile); 
    }
}

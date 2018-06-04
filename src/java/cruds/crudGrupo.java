package cruds;

import consultas.principales;
import beans.Alumno;
import beans.Alumnos;
import beans.Grupo;
import beans.Grupos;
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


public class crudGrupo {
    
    public static boolean crearGrupo(String path,int id, String maestro,String nombre, String asignatura) throws JAXBException{
        path = path + "/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupos = (Grupos)unmarshaller.unmarshal(configFile);
        Grupo grupo = new Grupo(id,maestro,nombre,0,asignatura);
        List<Grupo> grupoList = grupos.getGrupo();
        if(grupoList == null){
            grupoList = new ArrayList<>();
        }
        grupoList.add(grupo);
        grupos.setGrupo(grupoList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(grupos, configFile);
        return true;
    }    
    
    public static boolean eliminarGrupo(String path,List<Grupo> grupoList) throws JAXBException{
        path = path + "/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupos = (Grupos)unmarshaller.unmarshal(configFile);
        grupos.setGrupo(grupoList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(grupos, configFile);
        return true;
    }
    public static boolean editarGrupo(String path,int id, List<String> datos) throws JAXBException{
           //ciclo,grupo,request.getParameter("idMaestroAlumnos"),request.getParameter("grupo")
        List<Grupo> grupos = obtenerXML.obtenerListaGrupos(path);
        path = path + "/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupo = (Grupos) unmarshaller.unmarshal(configFile);
        for(Grupo g : grupos){
            if(g.getId() == id){
                g.setMaestro(datos.get(1));
                g.setNombre(datos.get(0));
                g.setAsignatura(datos.get(2));
            }
        }
        
        grupo.setGrupo(grupos);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(grupo, configFile); 
        return true;
    }
    public static boolean editarGrupo(String path,List<Grupo> grupos) throws JAXBException{
        path = path + "/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupo = (Grupos) unmarshaller.unmarshal(configFile);
        grupo.setGrupo(grupos);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(grupo, configFile); 
        return true;
    }
}

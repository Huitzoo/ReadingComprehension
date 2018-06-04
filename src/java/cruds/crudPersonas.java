package cruds;

import consultas.obtenerXML;
import beans.Persona;
import beans.Usuario;
import java.util.List;
import static consultas.principales.getIdPersona;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class crudPersonas {
    //crea persona
    public static int crearPersona(String path,String[] datos,int edad, int rol) throws JAXBException{
        int id = getIdPersona(path);
        path = path + "/personas.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Usuario usuario = (Usuario) unmarshaller.unmarshal(configFile);
        Persona persona = new Persona(id,rol,datos,edad);
        List<Persona> personaList = usuario.getUsuarios();
        personaList.add(persona);
        usuario.setUsuarios(personaList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(usuario, configFile);
        return id;
    }    
    //Elimina persona
    public static boolean eliminarPersona(String path,List<Persona> personaList) throws JAXBException{
        //int id = getIdPersona(path);
        path = path + "/personas.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Usuario usuario = (Usuario) unmarshaller.unmarshal(configFile);
        usuario.setUsuarios(personaList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(usuario, configFile);    
        return true;
    }

    public static boolean editarPersona(String path,List<String> datos,int edad, int id)throws JAXBException{
        List<Persona> personas = obtenerXML.obtenerListaUsuarios(path);
        path = path + "/personas.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Usuario usuario = (Usuario) unmarshaller.unmarshal(configFile);
        for(int i = 0; i<personas.size();i++){
            if(personas.get(i).getId()==id){
                personas.get(i).setNombre(datos.get(0));
                personas.get(i).setApellidos(datos.get(1));
                personas.get(i).setNombre_de_usuario(datos.get(2));
                personas.get(i).setEdad(edad);
            }
        }
        usuario.setUsuarios(personas);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(usuario, configFile);    
        return true;
    }
}

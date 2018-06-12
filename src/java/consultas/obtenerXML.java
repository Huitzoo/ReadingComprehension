package consultas;

import beans.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public final class obtenerXML {
    
    /*Funcion que me regresa a las pesonas que tengo registradas hasta el momento*/
    public static List<Persona> obtenerListaUsuarios(String path) throws JAXBException{
        path = path+"/personas.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Usuario usuario = (Usuario) unmarshaller.unmarshal(configFile);
        List<Persona> personaList = usuario.getUsuarios();
        return personaList;
    }
    
    public static List<Video> obtenerListaVideos(String path) throws JAXBException{
        path = path+"/videos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Videos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Videos usuario = (Videos) unmarshaller.unmarshal(configFile);
        List<Video> videoList = usuario.getVideos();
        return videoList;
    }
    public static List<Video> obtenerListaVideos(String path, int actividad) throws JAXBException{
        path = path+"/videos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Videos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Videos usuario = (Videos) unmarshaller.unmarshal(configFile);
        List<Video> videoList = usuario.getVideos();
        List<Video> videofinal = videoList.stream().filter(e -> e.getNumeroActividad() == actividad).collect(Collectors.toList());
        
        return videofinal;
    }
    /*Funcion que me regresa la lista de alumnos segun sea el maestro*/
    public static List<Alumno> obtenerListaAlumnos(String path, int idMaestro) throws JAXBException{
        path = path+"/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos usuario = (Alumnos) unmarshaller.unmarshal(configFile);        
        List<Alumno> alumnosList = usuario.getAlumno();
        //Ahora se busca por maestros a los alumnos
        if(!alumnosList.isEmpty()){
            String m = Integer.toString(idMaestro);
            List<Alumno> alumnos = alumnosList.stream().filter(a -> a.getNombreMaestro().split(" ")[0] == m).collect(Collectors.toList());
            return alumnos;
        }else{
            return null;
        }
    }
    
    public static List<Persona> obtenerListaAlumnosMestro(String path, int idMaestro) throws JAXBException{
        System.out.println(path);
        List<Alumno> alumnos = obtenerListaAlumnos(path,idMaestro);
        List<Persona> personaList = obtenerListaUsuarios(path);
        List<Persona> salida = new ArrayList<>();
        for(Persona p : personaList){
            for(Alumno a : alumnos){
                if(a.getId() == p.getId()){
                    salida.add(p);
                    break;
                }
            }
        }
        if(salida.isEmpty()){
            salida = null;
        }
        return salida;
    }
   
    
    /*Funcion que me regresa a los maestros que tengo registrados
    Se usa en ObtenerXML en la funcion obtenerMaestrosYAlumnos*/
    public static List<Maestro> obtenerListaMaestros(String path) throws JAXBException{
        path = path+"/maestro.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Maestros.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Maestros maestros = (Maestros) unmarshaller.unmarshal(configFile);
        List<Maestro> maestrosList = maestros.getMaestros();
        if(maestrosList == null){
            return null;
        }
        return maestrosList;
    }
    
    /*Funcion que me regresa la lista de alumnos que tengo registrados
    Se usa en ObtenerXML en la funcion obtenerMaestrosYAlumnos*/
    public static List<Alumno> obtenerListaAlumnos(String path) throws JAXBException{
        path = path+"/alumno.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Alumnos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Alumnos usuario = (Alumnos) unmarshaller.unmarshal(configFile);
        List<Alumno> alumnosList = usuario.getAlumno();
        if(alumnosList == null){
            return null;
        }
        return alumnosList;
   
    }
        
    /*Esta función es para obtener los grupos de un maestro*/ 
    /*Esta funcion la uso en el ajax de agregar alumno en adminDashboradServlet*/
    public static List<Grupo> obtenerListaGrupos(String path, String idMaestro) throws JAXBException{
        path = path+"/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupos = (Grupos) unmarshaller.unmarshal(configFile);
        List<Grupo> grupoList = grupos.getGrupo();
        List<Grupo> grupo= new ArrayList<>();
        if(grupoList == null){
           return null;
        }else{
            for(Grupo g : grupoList){
                if(g.getMaestro().split(" ")[0].equals(idMaestro.split(" ")[0])){
                    grupo.add(g);
                }
            }
        }//Ahora se busca por maestros a los alumnos
        return grupo;
    }   
    
    /*Esta función es la uso en adminDashboradServlet, me regresa la lista de grupos registrados*/
    public static List<Grupo> obtenerListaGrupos(String path) throws JAXBException { //To change body of generated methods, choose Tools | Templates.
         /*Esta función es para obtener los grupos*/
        path = path+"/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupos = (Grupos) unmarshaller.unmarshal(configFile);
        List<Grupo> grupoList = grupos.getGrupo();
        if(grupoList == null){
           return null;
        }//Ahora se busca por maestros a los alumnos
        return grupoList;
    }  
    
    /*Esta función sirve para aumentar el numero de alumnos en un grupo, 
    Se usa al momento de crear un alumno en adminDashBoardServlet*/
    public static String obtenerGrupoYAgregar(String path,int id,String viejo,int bandera) throws JAXBException{
        path = path + "/grupos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Grupos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Grupos grupos = (Grupos)unmarshaller.unmarshal(configFile);
        List<Grupo> grupoList = grupos.getGrupo();
        String grupo = "";
        if(bandera == 1){ //solo estoy consultando
            for(Grupo g : grupoList){
                if(g.getId()==id){
                    grupo = g.getNombre();
                    break;
                }
            }
            System.out.println("solo consulto");
        }else if(bandera == 2){//Estoy agregando
            for(Grupo g : grupoList){
                if(g.getId()==id){
                    grupo = g.getNombre();
                    int agregar = g.getAlumnos();
                    agregar = agregar + 1;
                    g.setAlumnos(agregar);
                    break;
                }
            }
        }else if(bandera == 3){//Estoy eliminando y agregando
            for(Grupo g : grupoList){
                if(g.getId()==id){
                    grupo = g.getNombre();
                    int agregar = g.getAlumnos();
                    agregar = agregar + 1;
                    g.setAlumnos(agregar);
                    break;
                }
            }
            for(Grupo g : grupoList){
                if(g.getId()==Integer.parseInt(viejo)){
                    grupo = g.getNombre();
                    int quitar = g.getAlumnos();
                    quitar = quitar - 1;
                    g.setAlumnos(quitar);
                    break;
                }
            }
        }else{//Estoy eliminando
            for(Grupo g : grupoList){
                if(g.getId()==Integer.parseInt(viejo)){
                    //grupo = g.getNombre();
                    int quitar = g.getAlumnos();
                    quitar = quitar - 1;
                    g.setAlumnos(quitar);
                    break;
                }
            }
        }
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(grupos, configFile);  
        return grupo;
    }
    
    /*Esta función separa a las personas en alumnos y maestros
    Se usa para pintar el html en adminDashBoardServlet*/
    public static List<List<String>> obtenerMaestrosYAlumnos(String path, List<Persona> personas) throws JAXBException{
        List<List<String>> maestros_alumnos = new ArrayList<>();
        List<Alumno> alumnos = obtenerXML.obtenerListaAlumnos(path);
        List<Maestro> maestros = obtenerXML.obtenerListaMaestros(path);
        List<String> alumnosList = new ArrayList<>();
        List<String> maestrosList = new ArrayList<>();
        if(alumnos != null){
            for(Persona p : personas){
                if(p.getId() != 1){
                    for(Alumno a : alumnos){
                        if(a.getId() == p.getId()){
                            String datos = p.toString()+" "+a.toString();
                            alumnosList.add(datos);
                        }
                    }
                }
            }
        }else{
            alumnosList.add("No tienes alumnos registrados");
        }
        if(maestros != null){
            for(Persona p : personas){
                if(p.getId() != 1){
                    for(Maestro m : maestros){
                        if(m.getId() == p.getId()){
                            String datos = p.toString()+" "+m.toString();
                            maestrosList.add(datos);
                        }
                    }
                }
            }
        }else{
            maestrosList.add("No tienes maestros registrados");
        }
        maestros_alumnos.add(alumnosList);
        maestros_alumnos.add(maestrosList);
        return maestros_alumnos;
    }
    
    /*Esta función esta en un ajax, el cual me ayuda a ni repetir nombres de usuario
    el ajax esta en adminDashBoardServlet*/
    public static List<String> obtenerUsernames(String path) throws JAXBException{
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(usernames.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        usernames grupos = (usernames) unmarshaller.unmarshal(configFile);
        List<String> username = grupos.getUsername();
        return username;
    }
    
    public static List<Actividad1> obtenerListaDeActividades1(String path) throws JAXBException{
        path = path+"/actividades1.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades1.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades1 actividades1 = (Actividades1) unmarshaller.unmarshal(configFile);
        List<Actividad1> actividades1List = actividades1.getActividades();
        return actividades1List;
    }
    
    public static List<Actividad2> obtenerListaDeActividades2(String path) throws JAXBException{
        path = path+"/actividades2.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades2.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades2 actividades2 = (Actividades2) unmarshaller.unmarshal(configFile);
        List<Actividad2> actividades1List = actividades2.getActividades();
        return actividades1List;
    }
    
    public static List<Video> obtenerListaDeVideos(String path) throws JAXBException{
        path = path+"/videos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Videos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Videos videos = (Videos) unmarshaller.unmarshal(configFile);
        List<Video> videosList = videos.getVideos();
        return videosList;
    } 
    
    /*Obtener los Id de los archivos xml*/
    
    public static int ultimoIdActividades2(String path) throws JAXBException{
        List<Actividad2> actividades1= obtenerListaDeActividades2(path);
        int id = 0;
        if(actividades1 != null){
            id = actividades1.get(actividades1.size()-1).getId();
        }
        id = id + 1;
        return id;
    }
 
    public static int ultimoIdVideos(String path) throws JAXBException{
        List<Actividad1> videos = obtenerListaDeActividades1(path);
        int id = 0;
        if(videos != null){
            id = videos.get(videos.size()-1).getId();
        }
        id = id + 1;
        return id;
    }
    
    public static int ultimoIdActividades1(String path) throws JAXBException{
        List<Actividad1> actividades1= obtenerListaDeActividades1(path);
        int id = 0;
        if(actividades1 != null){
            id = actividades1.get(actividades1.size()-1).getId();
        }
        id = id + 1;
        return id;
    }
}

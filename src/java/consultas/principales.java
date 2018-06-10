/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consultas;

import beans.Actividad1;
import beans.Alumno;
import beans.Grupo;
import beans.Maestro;
import beans.Persona;
import consultas.obtenerXML;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;

public class principales {
    
    public static int getIdPersona(String path) throws JAXBException{
        List<Persona> personas = obtenerXML.obtenerListaUsuarios(path);
        int id = 0;
        if(personas != null){
            Persona ultimo = personas.get(personas.size()-1);
            id = ultimo.getId();
        }
        id += 1;
        return id;
    }
    
    public static int getIdGrupo(String path) throws JAXBException{
        List<Grupo> grupos = obtenerXML.obtenerListaGrupos(path);
        path = path + "/grupos.xml";
        int id = 0;
        if(grupos != null){
            Grupo ultimo = grupos.get(grupos.size()-1);
            id = ultimo.getId();
        }
        id += 1;
        return id;
    }
    
    public static String obtenerPersonaYAlumno(int id,String path) throws JAXBException{
        List<Persona> personas = obtenerXML.obtenerListaUsuarios(path);
        String persona = "";
        for(Persona p : personas){
            if(p.getId() == id){
                persona = p.toString();
                break;
            }
        }
        List<Alumno> alumnos = obtenerXML.obtenerListaAlumnos(path);
        String alumno = "";
        for(Alumno a : alumnos){
            if(a.getId() == id){
                alumno = a.toString();
                break;
            }
        }
        return persona+" "+alumno;
    }
    public static List<String> obtenerGrupo(List<Grupo> grupo){
        List<String> grupos = new ArrayList<String>();
        if(grupo != null){
            for(Grupo g : grupo){
                grupos.add(g.toString());
            }
        }else{
            grupos.add("No tienes grupos registrados");
        }
        return grupos;
    }
    
    
    public static Actividad1 obtenerActividad1(String path, int id) throws JAXBException{
        List<Actividad1> actividades = obtenerXML.obtenerListaDeActividades1(path);
        List<Actividad1> actividad = actividades.stream().filter(e->e.getId()==id).collect(Collectors.toList()); 
        return actividad.get(0);
    }
    
    public static boolean cambiarGrupoMaestro(List<Alumno> alumno){
        return true;
    }
    
}

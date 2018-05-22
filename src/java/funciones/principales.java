/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package funciones;

import beans.Alumno;
import beans.Maestro;
import beans.Persona;
import funciones.obtenerXML;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBException;

public class principales {
    
    public static int getIdPersona(String path) throws JAXBException{
        List<Persona> personas = obtenerXML.obtenerListaUsuarios(path);
        Persona ultimo = personas.get(personas.size()-1);
        int id = ultimo.getId();
        id += 1;
        return id;
    }
    
    public static List<String> personasMaestros(List<Persona> personas,List<Maestro> maestros){
        System.out.println("pprincipales: hola");
        List<Persona> salidaPersonas = personas.stream().filter(e -> e.getRol()==2).collect(Collectors.toList());
        List<String> salidaMaestros = new ArrayList<String>();
        for(int i = 0; i<salidaPersonas.size();i++){
            salidaMaestros.add(salidaPersonas.get(i).toString()+" "+maestros.get(i).toString());
        }
        return salidaMaestros;
    }
     
    public static List<String> personasAlumnos(List<Persona> personas, List<Alumno> alumnos){
        System.out.println("principales: alumnos");
        List<Persona> salidaPersonas = personas.stream().filter(e -> e.getRol()==3).collect(Collectors.toList());
        List<String> salidaAlumnos = new ArrayList<String>();
        System.out.println(alumnos);
        for(int i = 0; i<salidaPersonas.size();i++){
            salidaAlumnos.add(salidaPersonas.get(i).toString()+" "+alumnos.get(i).toString());
        }
        System.out.println(salidaAlumnos);
        return salidaAlumnos;
    }
}

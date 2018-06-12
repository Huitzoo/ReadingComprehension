/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruds;

import beans.Actividad1;
import beans.Actividad2;
import beans.Actividades1;
import beans.Actividades2;
import beans.Palabras;
import beans.Preguntas;
import consultas.obtenerXML;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class crudActividad2 {
    public static void crearActividad2(String path, List<String> palabras,String nombre,int id,int idMaestro) throws JAXBException{
        path = path+"/actividades2.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades2.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades2 actividades2 = (Actividades2) unmarshaller.unmarshal(configFile);
        List<Actividad2> actividades2List = actividades2.getActividades();
        if(actividades2List == null){
            actividades2List = new ArrayList<>();
        }
        Palabras palabrasList = new Palabras(palabras);
        Actividad2 actividad = new Actividad2(id,idMaestro,nombre,palabrasList);
        actividades2List.add(actividad);
        actividades2.setActividades(actividades2List);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades2, configFile);
    }
    public static void editarActividad2(String path, List<String> palabras, String nombre, int id) throws JAXBException{
        List<Actividad2> actividades = obtenerXML.obtenerListaDeActividades2(path);
        path = path+"/actividades2.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades2.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades2 actividades2 = (Actividades2) unmarshaller.unmarshal(configFile);
        List<Actividad2> actividades1List = actividades2.getActividades();
        Palabras palabrasList = new Palabras(palabras);
        for(Actividad2 a : actividades1List){
            if(a.getId() == id){
                a.setName(nombre);
                a.setPalabras(palabrasList);
            }
        }
        actividades2.setActividades(actividades1List);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades2, configFile);
    }
    public static void eliminarActividad2(String path,int id) throws JAXBException{
        List<Actividad2> actividades = obtenerXML.obtenerListaDeActividades2(path);
        path = path+"/actividades2.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades2.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades2 actividades2 = (Actividades2) unmarshaller.unmarshal(configFile);
        List<Actividad2> actividades1List = actividades2.getActividades();
        List<Actividad2> actividad = actividades.stream().filter(e -> e.getId() != id).collect(Collectors.toList());
        if(actividad == null){
            actividad = new ArrayList<>();
        }
        actividades2.setActividades(actividad);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades2, configFile);
    }
    
    public static void eliminarActividad2(String path,int id,int basura) throws JAXBException{
        List<Actividad2> actividades = obtenerXML.obtenerListaDeActividades2(path);
        path = path+"/actividades2.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades2.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades2 actividades2 = (Actividades2) unmarshaller.unmarshal(configFile);
        List<Actividad2> actividades1List = actividades2.getActividades();
        List<Actividad2> actividad = actividades1List.stream().filter(e -> e.getIdMaestro() != id).collect(Collectors.toList());
        if(actividad == null){
            actividad = new ArrayList<>();
        }
        actividades2.setActividades(actividad);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades2, configFile);
    }
    
}

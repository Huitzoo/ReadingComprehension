/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruds;

import beans.Actividad1;
import beans.Actividades1;
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

/**
 *
 * @author huitz
 */
public class crudActividad1 {
    public static void crearActividad1(String path, List<String> preguntas, List<String> datos, int id) throws JAXBException{
        path = path+"/actividades1.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades1.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades1 actividades1 = (Actividades1) unmarshaller.unmarshal(configFile);
        List<Actividad1> actividades1List = actividades1.getActividades();
        if(actividades1List == null){
            actividades1List = new ArrayList<>();
        }
        Preguntas preguntasList = new Preguntas(preguntas);
        Actividad1 actividad = new Actividad1(id,datos,preguntasList);
        actividades1List.add(actividad);
        actividades1.setActividades(actividades1List);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades1, configFile);
    }
    public static void editarActividad1(String path, int id,List<String> datos, List<String> preguntas) throws JAXBException{
        List<Actividad1> actividades = obtenerXML.obtenerListaDeActividades1(path);
        path = path+"/actividades1.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades1.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades1 actividades1 = (Actividades1) unmarshaller.unmarshal(configFile);
        List<Actividad1> actividades1List = actividades1.getActividades();
        for(Actividad1 a : actividades1List){
            if(a.getId() == id){
                Preguntas p = a.getPreguntas();
                p.setPreguntas(preguntas);
                a.setPreguntas(p);
                if(!"".equals(datos.get(1))){
                    a.setPathAudio(datos.get(1));
                }
                if(!"".equals(datos.get(0))){
                    a.setPathTexto(datos.get(0));
                }
                if(!"".equals(datos.get(3))){
                    a.setPathImagen(datos.get(3));
                }
                if(!"".equals(datos.get(4))){
                    a.setNombre(datos.get(4));
                }
                break;
            }
        }
        actividades1.setActividades(actividades1List);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades1, configFile);
    }
    public static void eliminarActividad1(String path,int id) throws JAXBException{
        List<Actividad1> actividades = obtenerXML.obtenerListaDeActividades1(path);
        path = path+"/actividades1.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades1.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades1 actividades1 = (Actividades1) unmarshaller.unmarshal(configFile);
        List<Actividad1> actividades1List = actividades1.getActividades();
        List<Actividad1> actividad = actividades.stream().filter(e -> e.getId() != id).collect(Collectors.toList());
        if(actividad == null){
            actividad = new ArrayList<>();
        }
        actividades1.setActividades(actividad);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades1, configFile);
    }
    
    public static void eliminarActividad1(String path,int id,int basura) throws JAXBException{
        List<Actividad1> actividades = obtenerXML.obtenerListaDeActividades1(path);
        path = path+"/actividades1.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Actividades1.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Actividades1 actividades1 = (Actividades1) unmarshaller.unmarshal(configFile);
        List<Actividad1> actividades1List = actividades1.getActividades();
        List<Actividad1> actividad = actividades.stream().filter(e -> e.getIdMaestro()!= id).collect(Collectors.toList());
        if(actividad == null){
            actividad = new ArrayList<>();
        }
        actividades1.setActividades(actividad);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(actividades1, configFile);
    }
}

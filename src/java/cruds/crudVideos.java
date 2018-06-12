/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cruds;

import beans.Actividad1;
import beans.Actividades1;
import beans.Preguntas;
import beans.Video;
import beans.Videos;
import consultas.obtenerXML;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class crudVideos {
    public static void crearVideo(String path, String location, int idM, int id, int actividad) throws JAXBException{
        //List<Video> videos = obtenerXML.obtenerListaVideos(path);
        path = path +"/videos.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Videos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Videos videos = (Videos) unmarshaller.unmarshal(configFile);
        List<Video> videosList = videos.getVideos();
        if(videosList == null){
            videosList = new ArrayList<>();
        }
        Video video = new Video(id,idM,location,actividad);
        videosList.add(video);
        videos.setVideos(videosList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(videos, configFile);
    }
    
    public static void editar(String path,int id, String location) throws JAXBException{
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Videos.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Videos videos = (Videos) unmarshaller.unmarshal(configFile);
        List<Video> videosList = videos.getVideos();
        if(videosList == null){
            videosList = new ArrayList<>();
        }
        for(Video v : videosList){
            if(v.getId()==1){
                v.setPathVideo(location);
            }
        }
        videos.setVideos(videosList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(videos, configFile);
    }
    
}

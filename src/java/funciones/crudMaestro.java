package funciones;

import beans.Maestro;
import beans.Maestros;
import java.util.List;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public class crudMaestro {
    
    public static boolean crearMaestro(String path,String[] datos,int id) throws JAXBException{
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Maestros.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Maestros maestros = (Maestros)unmarshaller.unmarshal(configFile);
        Maestro maestro = new Maestro(id,datos);
        List<Maestro> maestroList = maestros.getMaestros();
        maestroList.add(maestro);
        maestros.setMaestros(maestroList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(maestros, configFile);
        return true;
    }    
    public static boolean eliminarMaestro(String path,List<Maestro> maestroList) throws JAXBException{
        path = path + "/maestro.xml";
        File configFile = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Maestros.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Maestros maestros = (Maestros)unmarshaller.unmarshal(configFile);
        maestros.setMaestros(maestroList);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(maestros, configFile);
        return true;
    }
}
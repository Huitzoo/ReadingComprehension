package xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import beans.Usuario;
import beans.Persona;

public class archivo {
	public static void main(String[] args) {

	  Usuario usuario = new Usuario();
          Persona persona = new Persona();
          persona.setId(3);
          persona.setRol(2);
	  persona.setNombre("Santa");
	  persona.setApellidos("Barrera Ramirez");
	  persona.setContra("halo0316");
	  persona.setNombre_de_usuario("Santita");
          usuario.setPersona(persona);
	  try {
                
		File file = new File("C:\\Users\\huitz\\Documents\\Huitzoo\\ESCOM\\Web\\Proyecto\\ReadingComprehension\\web\\baseDeDatos\\xml\\personas.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(usuario, file);
		jaxbMarshaller.marshal(usuario, System.out);
	      } catch (JAXBException e) {
		e.printStackTrace();
	      }
        }
}
package xml;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import beans.Usuario;
        
public class archivo {
	public static void main(String[] args) {

	  Usuario customer = new Usuario();
	  customer.setId(100);
	  customer.setName("mkyong");
	  customer.setAge(29);

	  try {
                System.out.println();
		File file = new File("C:\\Users\\huitz\\Documents\\Huitzoo\\hola.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Usuario.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(customer, file);
		jaxbMarshaller.marshal(customer, System.out);

	      } catch (JAXBException e) {
		e.printStackTrace();
	      }

	}
}
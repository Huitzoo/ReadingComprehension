package controladores;

import beans.Alumno;
import beans.Grupo;
import beans.Maestro;
import beans.Persona;
import consultas.obtenerXML;
import cruds.crudMaestro;
import cruds.crudAlumno;
import cruds.crudPersonas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

/**
 *
 * @author huitz
 */
public class ajaxMaestros extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String id = request.getParameter("id");
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml"); 
        List<Persona> personas = null;
        List<Maestro> maestros = null;
        List<Alumno> alumnos = null;
        List<Grupo> grupos = null;
        List<Alumno> alumnosM = null;
        List<Grupo> gruposM = null;
        /*Obtengo todas las listas que necesito para hacer el delete de un profesor*/
        /*Cuando se borra un profesor, se coloca S/A tanto a grupos como a alumnos*/
        try{
            personas = obtenerXML.obtenerListaUsuarios(path);
            maestros = obtenerXML.obtenerListaMaestros(path);
            alumnos = obtenerXML.obtenerListaAlumnos(path);
            grupos = obtenerXML.obtenerListaGrupos(path);       
            
            if(alumnos != null){
                //Aqui obtengo los alumnos que les da clase ese maestro
                for(Alumno a : alumnos){
                    if(a.getNombreMaestro().split(" ")[0].equals(id)){
                        a.setNombreMaestro("Sin S/A");
                        System.out.println("**********holis");
                    }
                }
                cruds.crudAlumno.editarAlumno(path,alumnos);
                //Aqui obtenfo los grupos de los profes
            }if(grupos != null){
                for(Grupo g : grupos){
                    if(g.getMaestro().split(" ")[0].equals(id)){
                        g.setMaestro("Sin S/A");
                        System.out.println("**********holis grupos");
                    }
                }
                cruds.crudGrupo.editarGrupo(path,grupos);
            }
        }catch (JAXBException ex) {
               Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        int idM = Integer.parseInt(id);
        List<Maestro> maestrosS = new ArrayList<>();
        List<Persona> personasS = new ArrayList<>();
        for(Maestro m : maestros){
            if(m.getId() != idM){
                maestrosS.add(m);
            }
        }
        for(Persona p : personas){
            if(p.getId() != idM){
                personasS.add(p);
            }
        }
        try {
            cruds.crudMaestro.eliminarMaestro(path, maestrosS);
            cruds.crudPersonas.eliminarPersona(path, personasS);
        } catch (JAXBException ex) {
            Logger.getLogger(ajaxMaestros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Esta función es para editar*/
        
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        String nombre_usuario = request.getParameter("usuario");
        int edad = Integer.parseInt(request.getParameter("edad"));
        int id = Integer.parseInt(request.getParameter("id"));
        if(request.getParameter("correo")!= ""){ //Se va a editar un profesor
            String correo = request.getParameter("correo");
            String asignatura = request.getParameter("asignatura");
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("/baseDeDatos/xml"); 
            List<String> datos = Arrays.asList(nombre,apellidos,nombre_usuario,correo,asignatura);
            System.out.println(datos.toString());
            try {
                crudPersonas.editarPersona(path,datos, edad, id);
                crudMaestro.editarMaestro(path,datos,id);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxMaestros.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
        HttpSession session = request.getSession();
        session.setAttribute("bandera", 1);
        response.sendRedirect("adminDashboardServlet");
    }
    
}

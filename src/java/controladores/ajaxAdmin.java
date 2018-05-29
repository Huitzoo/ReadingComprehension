package controladores;

import beans.Alumno;
import beans.Maestro;
import beans.Persona;
import funciones.obtenerXML;
import funciones.crudMaestro;
import funciones.crudAlumno;
import funciones.crudPersonas;
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
public class ajaxAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("*********"+id);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml"); 
        List<Persona> personas = null;
        List<Maestro> maestros = null;
        List<Alumno> alumnos = null;
        try{
               personas = obtenerXML.obtenerListaUsuarios(path);
               maestros = obtenerXML.obtenerListaMaestros(path);
               alumnos = obtenerXML.obtenerListaAlumnos(path);
        }catch (JAXBException ex) {
               Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Persona> listaPersonas = personas.stream().filter(e -> e.getId()!=id ).collect(Collectors.toList());
        List<Maestro> salidaMaestros = maestros.stream().filter(e -> e.getId()!=id).collect(Collectors.toList());
        List<Alumno> salidaAlumnos = alumnos.stream().filter(e -> e.getIdMaestro() != id).collect(Collectors.toList());
                
        System.out.println("lista personas");
        System.out.println(listaPersonas);
        if(alumnos!= null){
                List<Alumno> quitarAlumnos = alumnos.stream().filter(e -> e.getIdMaestro() == id).collect(Collectors.toList());
                if(quitarAlumnos != null){
                    List<Persona> salidaPersonas = new ArrayList();
                    List<Integer> idPersonas = new ArrayList();
                    List<Integer> idAlumnos = new ArrayList();
                    for(Persona p : listaPersonas){
                        idPersonas.add(p.getId());
                    }
                    for(Alumno a : quitarAlumnos){
                        idAlumnos.add(a.getId());
                    }
                    idPersonas.removeAll(idAlumnos);
                    for(Integer i : idPersonas){
                        for(Persona p : listaPersonas){
                            if(p.getId() == i ){
                                salidaPersonas.add(p);
                                break;
                            }
                        }
                    }
                    System.out.println(salidaPersonas);
                    try{
                        crudAlumno.eliminarAlumno(path,salidaAlumnos);
                        crudPersonas.eliminarPersona(path,salidaPersonas);
                        crudMaestro.eliminarMaestro(path,salidaMaestros);

                    }catch (JAXBException ex) {
                        Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }else{ 
            try {
                crudPersonas.eliminarPersona(path,listaPersonas);
                crudMaestro.eliminarMaestro(path,salidaMaestros);

            }catch (JAXBException ex) {
                   Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Esta función es para editar*/
        
        //response.setContentType("text/html;charset=UTF-8");
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
                Logger.getLogger(ajaxAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }else{//alumno
            String grupo = request.getParameter("grupo");
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("bandera", 1);
        response.sendRedirect("adminDashboardServlet");
        }
    
}

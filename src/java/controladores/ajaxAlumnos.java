package controladores;

import beans.Persona;
import beans.Alumno;
import consultas.obtenerXML;
import consultas.principales;
import cruds.crudAlumno;
import cruds.crudPersonas;
import java.io.IOException;
import java.util.ArrayList;
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
public class ajaxAlumnos extends HttpServlet {

    /*Aqui voy a manejar edición*/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        session.setAttribute("usuario", usuario);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml");
        //sino, se esta agregando a un alumno
        String grupo = "";
        String ciclo = request.getParameter("ciclo");
        int id = Integer.parseInt(request.getParameter("id"));
        String[] datos = new String[]{};
        if(id == 0){
           grupo = "Sin S/A";
        }else{
            try {
                String alumno = principales.obtenerPersonaYAlumno(id, path);
                datos = alumno.split(" ");
                grupo = obtenerXML.obtenerGrupoYAgregar(path,Integer.parseInt(request.getParameter("grupo")),null,1);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(grupo);
        }
        List<String> datosP = new ArrayList<>();
        datosP.add(request.getParameter("nombre"));
        datosP.add(request.getParameter("apellidos").replace(" ","_"));
        datosP.add(request.getParameter("usuario"));
        String[] datosA = {ciclo,grupo,request.getParameter("idMaestroAlumnos"),request.getParameter("grupo")};
        try {
            crudPersonas.editarPersona(path,datosP,Integer.parseInt(request.getParameter("edad")),Integer.parseInt(datos[0]));
            crudAlumno.editarAlumno(path,datosA,Integer.parseInt(datos[0]));
        } catch (JAXBException ex) {
            Logger.getLogger(ajaxAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!datos[9].equals(datosA[3])){
            try {
                obtenerXML.obtenerGrupoYAgregar(path,Integer.parseInt(datosA[3]),datos[9],3);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxAlumnos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        session.setAttribute("bandera",1);
        response.sendRedirect("adminDashboardServlet");
    }    
   
    /*Aqui voy a manejar eliminacion*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        session.setAttribute("usuario", usuario);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml");
        String alumno = "";
        List<Alumno> alumnos = null;
        List<Persona> personas = null;
        try {
            alumno = principales.obtenerPersonaYAlumno(Integer.parseInt(request.getParameter("id")),path);
            personas = obtenerXML.obtenerListaUsuarios(path);
            alumnos = obtenerXML.obtenerListaAlumnos(path);
        } catch (JAXBException ex) {
            Logger.getLogger(ajaxAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] datos = alumno.split(" ");
        List<Alumno> alumnoList = alumnos.stream().filter(e -> e.getId()!= Integer.parseInt(datos[0])).collect(Collectors.toList());
        List<Persona> personaList = personas.stream().filter(e -> e.getId()!= Integer.parseInt(datos[0])).collect(Collectors.toList());
        try {
            crudAlumno.eliminarAlumno(path,alumnoList);
            crudPersonas.eliminarPersona(path, personaList);
            obtenerXML.obtenerGrupoYAgregar(path,0,datos[9],4);
        } catch (JAXBException ex) {
            Logger.getLogger(ajaxAlumnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Alumno;
import beans.Grupo;
import beans.Persona;
import consultas.obtenerXML;
import cruds.crudAlumno;
import cruds.crudGrupo;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author huitz
 */
public class ajaxGrupos extends HttpServlet {
    
    /*Get me regresa la lista de grupos de un profesor*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        session.setAttribute("usuario", usuario);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml");
        String salida = "";        
        System.out.println("***************BUSCO GRUPOS");
        String id = request.getParameter("id");
        List<Grupo> grupo = null;
        try {
            grupo = obtenerXML.obtenerListaGrupos(path, id);
        } catch (JAXBException ex) {
            Logger.getLogger(ajaxGrupos.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray obj = new JSONArray();
        if(grupo == null){
            salida = null;
            out.write(salida);
        }else{
            for(Grupo g : grupo){
                try {
                    JSONObject list1 = new JSONObject();
                    list1.put("nombre : ",g.getNombre());
                    list1.put("id : ",g.getId());
                    obj.put(list1);
                } catch (JSONException ex) {
                    Logger.getLogger(ajaxGrupos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println(obj);
            out.write(obj.toString());
        }
    }
    
    /*Aqui voy a manejar la edición y eliminación*/
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        session.setAttribute("usuario", usuario);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml");
        String banderaS = request.getParameter("bandera");
        int bandera = 0;
        if(banderaS == null){
            bandera = 2;
        }else{
            bandera = Integer.parseInt(banderaS);
        }
        if(bandera ==1){// se edita
            String nombre = request.getParameter("nombre");
            String maestro = request.getParameter("editarMaestroGrupo");
            int id = Integer.parseInt(request.getParameter("id")); //Este id es del grupo
            String asignatura = request.getParameter("asignatura");
            List<String> datos = new ArrayList<>();
            datos.add(nombre);
            datos.add(maestro);
            datos.add(asignatura);
            List<Alumno> alumnos = null;
            try {
                //cruds.crudGrupo.editarGrupo();
                alumnos = obtenerXML.obtenerListaAlumnos(path);
                crudGrupo.editarGrupo(path, id, datos);
                List<Alumno> alumnosList = alumnos.stream().filter(a -> a.getIdGrupo()== id).collect(Collectors.toList());
                System.out.println(alumnosList);
                crudAlumno.editarAlumno(path, datos, id, alumnosList,bandera);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            session.setAttribute("bandera",1);
            response.sendRedirect("adminDashboardServlet");
            
        }else{ //Aqui elimino al grupo
            int id = Integer.parseInt(request.getParameter("id"));//Este id es del grupo
            List<Grupo> grupo;
            List<Alumno> alumnos;

            try {
                grupo = obtenerXML.obtenerListaGrupos(path);
                List<Grupo> grupos = grupo.stream().filter(g -> g.getId() != id).collect(Collectors.toList());
                cruds.crudGrupo.eliminarGrupo(path,grupos);
                alumnos = obtenerXML.obtenerListaAlumnos(path);
                List<Alumno> alumnosList = alumnos.stream().filter(a -> a.getIdGrupo()== id).collect(Collectors.toList());
                List<String> datosA = new ArrayList<>();
                datosA.add("S/A");
                cruds.crudAlumno.editarAlumno(path, datosA , id, alumnos,bandera);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("bandera",2);
        }
    }
}

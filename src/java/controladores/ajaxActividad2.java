/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Actividad1;
import beans.Persona;
import consultas.obtenerXML;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ajaxActividad2 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            List<String> nombres = new ArrayList<String>(request.getParameterMap().keySet());            
            List<String> palabras = new ArrayList<>();
            String nombre = "";
            HttpSession session = request.getSession();
            Persona usuario = (Persona)session.getAttribute("usuario");
            session.setAttribute("usuario",usuario);
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDeDatos/xml");
            int id = Integer.parseInt(request.getParameter("id"));
            for(String n: nombres){
                if(!n.equals("nombre") && !n.equals("id") ){
                    palabras.add(request.getParameter(n));
                }else{
                    nombre = request.getParameter(n);
                }
            }
            try {
                //id = obtenerXML.ultimoIdActividades2(path);
                cruds.crudActividad2.editarActividad2(path, palabras, nombre, id);
            } catch (JAXBException ex) {
                Logger.getLogger(maestroActividades2Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.setAttribute("bandera", 1);
            response.sendRedirect("maestroActividades2Servlet");
        }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            Persona usuario = (Persona)session.getAttribute("usuario");
            session.setAttribute("usuario",usuario);
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDeDatos/xml");
            int id = Integer.parseInt(request.getParameter("id"));
             try {
                cruds.crudActividad2.eliminarActividad2(path, id);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxActividad1.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}

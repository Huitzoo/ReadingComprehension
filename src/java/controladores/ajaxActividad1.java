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
public class ajaxActividad1 extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            /*Con esta función se salva por ahora la actividad 1*/
            /*Se guarda el path del audio y del texto*/
            /*Se obtienen las preguntas de los input y se guardan*/
            /*Se hace una función como la de idUusario y id Grupo*/
            /*Se obitne id de la persona que inicio sesión*/
            List<String> nombres = new ArrayList<String>(request.getParameterMap().keySet());            
            HttpSession session = request.getSession();
            Persona usuario = (Persona)session.getAttribute("usuario");
            session.setAttribute("usuario",usuario);
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDeDatos/xml");
            String audio = "";
            String texto = "";
            String img = "";
            String nombre = "";
            if(session.getAttribute("path")!=null){
                texto = (String)session.getAttribute("path");
            }
            if(session.getAttribute("audio")!=null){
                audio = (String)session.getAttribute("audio");
            }
            if(session.getAttribute("img")!=null){
                img = (String)session.getAttribute("img");
            }
            if(session.getAttribute("nombre")!=null){
                nombre = (String)session.getAttribute("nombre");
            }
            List<String> preguntas = new ArrayList<>();
            for(String s : nombres){
                System.out.println(s);
                if(!s.equals("id")){
                    preguntas.add(request.getParameter(s));
                }
            }
            List<String> datos = new ArrayList<>();
            datos.add(texto);
            datos.add(audio);
            datos.add(Integer.toString(usuario.getId())); //id del profesor
            datos.add(img);
            datos.add(nombre);
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                if(id == 0){
                    id = obtenerXML.ultimoIdActividades1(path);
                    cruds.crudActividad1.crearActividad1(path,preguntas,datos,id);
                }else{
                    cruds.crudActividad1.editarActividad1(path, id, datos, preguntas);
                }
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxActividad1.class.getName()).log(Level.SEVERE, null, ex);
            }
            session.removeAttribute("nombre");
            session.removeAttribute("img");
            session.removeAttribute("audio");
            session.removeAttribute("path");
        }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            Persona usuario = (Persona)session.getAttribute("usuario");
            session.setAttribute("usuario",usuario);
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDeDatos/xml");
            int id = Integer.parseInt(request.getParameter("id"));
             try {
                cruds.crudActividad1.eliminarActividad1(path, id);
            } catch (JAXBException ex) {
                Logger.getLogger(ajaxActividad1.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}

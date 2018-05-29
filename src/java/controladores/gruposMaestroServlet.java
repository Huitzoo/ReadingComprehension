/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Grupo;
import beans.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import funciones.obtenerXML;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
/**
 *
 * @author huitz
 */
public class gruposMaestroServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        session.setAttribute("usuario", usuario);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml"); 
        List<Grupo> grupos = new ArrayList<>();
        try {
            grupos = obtenerXML.obtenerListaGrupos(path, usuario.getId());
        } catch (JAXBException ex) {
            Logger.getLogger(gruposMaestroServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String salida = "";
        if(grupos == null){
            salida = "No has registrado ningun grupo";
        }else{
            salida = grupos.toString();
        }
    }
}

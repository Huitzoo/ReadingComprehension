/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Actividad1;
import beans.Persona;
import consultas.principales;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author huitz
 */
public class visualizarActividad1Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
           HttpSession session = request.getSession();
        if(session.getAttribute("usuario") != null){
            Persona usuario = (Persona)session.getAttribute("usuario");
            session.setAttribute("usuario",usuario);
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDedatos/xml");
            int id = Integer.parseInt(request.getParameter("id"));
            Actividad1 actividad = null;
            try {
                actividad = principales.obtenerActividad1(path, id);
            } catch (JAXBException ex) {
                Logger.getLogger(visualizarActividad1Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            pintar(out,actividad);
        }else{
            response.sendRedirect("loginServlet");
        }
            
    }
    public static void pintar(PrintWriter out, Actividad1 actividad) throws FileNotFoundException, IOException{
        InputStream is = new FileInputStream(new File(actividad.getPathTexto()));
        System.out.println(is);
        String contents = "";
        try {
          contents = IOUtils.toString(is, "UTF-8");
          System.out.println(contents);
        } finally {
          IOUtils.closeQuietly(is);
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel='stylesheet' type='text/css' href='css/jquery.booklet.latest.css'>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("<title>Books</title>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<nav class='navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow'>");
        out.println("<a class='navbar-brand col-sm-3 col-md-2 mr-0' href='#'>El ABC de TOMMY</a>");
        out.println("<ul class='navbar-nav px-3'>");
        out.println("<li class='nav-item text-nowrap'>");
        out.println("<a class='nav-link' href='loginServlet'>Salir</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("<br>");
        
        out.println("<br>");
        out.println("<br>");
        out.println("<main class='bd-masthead' id='content' role='main'>");
        out.println("<div class='booklet' id='mybook'>");
        out.println("");
        out.println("<div class='b-page b-page-1 b-p2'>");
        out.println("<div class='b-wrap b-wrap-right'>");
        out.println("<div>");
        out.println("<h2>"+actividad.getNombre()+"</h2>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='b-page b-page-2 b-p3'>");
        out.println("<div class='b-wrap b-wrap-left'>");
        out.println("<p>"+contents+"</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='b-page b-page-3 b-p4'>");
        out.println("<div class='b-wrap b-wrap-right'>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='b-page b-page-4 b-p5'>");
        out.println("<div class='b-wrap b-wrap-left'>");
      
        out.println("</div>");
        out.println("</div>");
        out.println("");
        out.println("<div class='b-controls'>");
        out.println("...");
        out.println("</div>");
        out.println("</div>");
        out.println("</main>");
        out.println("<script type='text/javascript' src='js/jquery-2.1.0.min.js'></script>");
        out.println("<script type='text/javascript' src='js/jquery-ui-1.10.4.min.js'></script>");
        out.println("<script type='text/javascript' src='js/jquery.easing.1.3.js'></script>");
        out.println("<script type='text/javascript' src='js/jquery.booklet.latest.js'></script>");
        out.println("<script type='text/javascript'>");
        out.println("$(function() {");
        out.println("$('#mybook').booklet({");
        out.println("closed: true,        ");
        out.println("autoCenter: true,");
        out.println("width:  600,");
        out.println("height: 400");
        out.println("});");
        out.println("});");
        out.println("</script>");

        out.println("</body>");
        out.println("</html>");


        
    }

}

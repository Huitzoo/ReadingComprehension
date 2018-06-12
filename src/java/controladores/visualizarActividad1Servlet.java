/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Actividad1;
import beans.Persona;
import beans.Video;
import consultas.obtenerXML;
import consultas.principales;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
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
        if (session.getAttribute("usuario") != null) {
            Persona usuario = (Persona) session.getAttribute("usuario");
            session.setAttribute("usuario", usuario);
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDedatos/xml");
            int id = Integer.parseInt(request.getParameter("id"));
            Actividad1 actividad = null;
            List<Video> videos = null;
            try {
                actividad = principales.obtenerActividad1(path, id);
                videos = obtenerXML.obtenerListaVideos(path,1);
            } catch (JAXBException ex) {
                Logger.getLogger(visualizarActividad1Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            Video video = null;
            if(videos != null){
                video = videos.get(Math.abs(new Random().nextInt(videos.size())));
            }
            pintar(out, actividad,video);
        } else {
            response.sendRedirect("loginServlet");
        }

    }

    public static void pintar(PrintWriter out, Actividad1 actividad,Video video) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(new File(actividad.getPathTexto()));
        System.out.println(is);
        String contents = "";
        try {
            contents = IOUtils.toString(is, "UTF-8");
            System.out.println(contents);
        } finally {
            IOUtils.closeQuietly(is);
        }
        List<String> s = actividad.getPreguntas().getPreguntas();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en' class='no-js'>");
        out.println("<head>");
        //out.println("<meta charset='UTF-8' />");
        out.println("<meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'> ");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'> ");
        out.println("<title>Actividad 1</title>");
        out.println("<link rel='stylesheet' type='text/css' href='css/jquery.jscrollpane.custom.css' />");
        out.println("<link rel='stylesheet' type='text/css' href='css/bookblock.css' />");
        out.println("<link rel='stylesheet' type='text/css' href='css/custom.css' />");
        out.println("<link rel='stylesheet' type='text/css' href='css/input.css' />");
        out.println("<script src='js/modernizr.custom.79639.js'></script>");
        //out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("<script src='https://code.createjs.com/createjs-2015.11.26.min.js'></script>");
        out.println("<script>");
        out.println("var soundID = 'Thunder';");
        out.println("");
        out.println("function loadSound () {");
        out.println("createjs.Sound.registerSound('" + actividad.getPathAudio() + "', soundID);");
        out.println("}");
        out.println("");
        out.println("function playSound () {");
        out.println("createjs.Sound.play(soundID);");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body onload='loadSound();'>");

        out.println("<div id='container' class='container'>");
        out.println("<div class='menu-panel'>");
        out.println("<h3>Contenidos</h3>");
        out.println("<ul id='menu-toc' class='menu-toc'>");
        out.println("<li class='menu-toc-current'><a href='#item1'>Actividad 1</a></li>");
        out.println("<li><a href='#item2'>Lectura</a></li>");
        out.println("<li><a href='#item3'>Escucha el audio</a></li>");
        int contador = 4;
        for (String s1 : s) {
            if (contador % 4 == 0) {
                out.println("<li><a href='#item" + contador + "'>Preguntas</a></li>");
            }
            contador++;
        }
        out.println("</ul>");
        out.println("</div>");
        out.println("");
        out.println("<div class='bb-custom-wrapper'>");
        out.println("<div id='bb-bookblock' class='bb-bookblock'>");

        out.println("<div class='bb-item' id='item1'>");
        out.println("<div class='content'>");
        out.println("<div class='scroller'>");
        if(video != null){
            out.println("<video width='320' height='240' controls>");
            out.println("<source src='"+video.getPathVideo()+"' type='video/mp4'>");
            out.println("</video>");
        }
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<div class='bb-item' id='item2'>");
        out.println("<div class='content'>");
        out.println("<div class='scroller'>");

        out.println("<h2>" + actividad.getNombre() + "</h2>");
        out.println("<p>" + contents + "</p>");

        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<div class='bb-item' id='item3'>");
        out.println("<div class='content'>");
        out.println("<div class='scroller'>");
        out.println("<h2>Escucha el audio</h2>");

        out.println("<img onclick='playSound();' class='playSound' title='Da click para escuchar' src='img/habla.gif'/></a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        contador = 4;
        int index = 0;

        for (int i = 0; i < s.size(); i = i + 4) {
            out.println("<div class='bb-item' id='item" + contador + "'>");
            contador++;
            out.println("<div class='content'>");
            out.println("<div class='scroller'>");
            out.println("<h2>Preguntas</h2>");
            for (index = i; index < i + 4; index++) {
                if (index < s.size()) {
                    if (!"".equals(s.get(index))) {
                        out.println("<p>" + s.get(index) + "</p>");
                        out.println("<input type='text' class=\"palabra\" name='r" + index + "'/>");
                    }
                }
            }
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");

        }

        out.println("<nav>");
        out.println("<span id='bb-nav-prev'>&larr;</span>");
        out.println("<span id='bb-nav-next'>&rarr;</span>");
        out.println("</nav>");
        out.println("");
        out.println("<span id='tblcontents' class='menu-button'>Table of Contents</span>");
        out.println("");
        out.println("</div>");
        out.println("");
        out.println("</div><!-- /container1 -->");
        out.println("<script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js'></script>");
        out.println("<script src='js/jquery.mousewheel.js'></script>");
        out.println("<script src='js/jquery.jscrollpane.min.js'></script>");
        out.println("<script src='js/jquerypp.custom.js'></script>");
        out.println("<script src='js/jquery.bookblock.js'></script>");
        out.println("<script src='js/page.js'></script>");

        out.println("<script>");
        out.println("$(function() {");
        out.println("");
        out.println("Page.init();");
        out.println("");
        out.println("});");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Actividad2;
import beans.Persona;
import consultas.principales;
import java.io.FileNotFoundException;
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

public class visualizarActividad2Servlet extends HttpServlet {

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
            Actividad2 actividad = null;
            try {
                actividad = principales.obtenerActividad2(path, id);
            } catch (JAXBException ex) {
                Logger.getLogger(visualizarActividad1Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            pintar(out,actividad);
        }else{
            response.sendRedirect("loginServlet");
        }
        
    }
    public static void pintar(PrintWriter out, Actividad2 actividad) throws FileNotFoundException, IOException{
        List<String> salida = new ArrayList<>();
        for(String s : actividad.getPalabras().getPalabras()){
            salida.add("\""+s+"\"");
        }
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hangman</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB' crossorigin='anonymous'>");
        out.println("<link rel='stylesheet' href='css/hangman.css'>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<nav class=\"navbar navbar-dark bg-dark static-top\" shadow'>");
        out.println("<a class='navbar-brand col-sm-3 col-md-2 mr-0' href='#'>El ABC de TOMMY</a>");
        out.println("<ul class='navbar-nav px-3'>");
        out.println("<li class='nav-item text-nowrap'>");
        out.println("<a class='nav-link' href='loginServlet'>Salir</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</nav>");
        
        out.println("<h1 id='titulo'>Actividad2</h1>");
        out.println("<P id='instrucciones'>");
        out.println("<a type='button' class='btn btn-primary' href=''></a>");
        out.println("</P>");
        out.println("");
        out.println("<div class='container'>");
        out.println("<div class='row'>");
        out.println("<div id='espacios'>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='row'>");
        out.println("<div class='col'>");
        out.println("<div id='muneco'>");
        out.println("<canvas id='cabeza'></canvas>");
        out.println("<canvas id='bizquierdo'></canvas>");
        out.println("<canvas id='torso'></canvas>");
        out.println("<canvas id='cadera'></canvas>");
        out.println("<canvas id='bderecho'></canvas>");
        out.println("<canvas id='pderecho'></canvas>");
        out.println("<canvas id='pizquierdo'></canvas>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col'>");
        out.println("<div id='letras'>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js'></script>");
        out.println("<script src='http://code.jquery.com/jquery-latest.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js'></script>");
        out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");
        out.println("<script type='text/javascript' src='js/fabric.js'></script>");
        out.println("<script type='text/javascript' src='js/hangman.js'></script>");
        out.println("<script type='text/javascript'>");
        out.println("init("+salida+");");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");

    }
}

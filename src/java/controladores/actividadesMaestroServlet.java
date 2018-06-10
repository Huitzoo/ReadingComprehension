/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author huitz
 */
public class actividadesMaestroServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession session = request.getSession();
            if(session.getAttribute("usuario") != null){
                PrintWriter out = response.getWriter();
                Persona usuario = (Persona)session.getAttribute("usuario");
                session.setAttribute("usuario", usuario);
                pintar(out,usuario.getNombre());
            }else{
                response.sendRedirect("loginServlet");
            }
    }
    public void pintar(PrintWriter out, String nombre){
        out.println("<header style='margin-bottom: 80px;' class='navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar'>");
        out.println("<nav class='navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow'>");
        out.println("<a class='navbar-brand col-sm-3 col-md-2 mr-0' href='#'>El ABC de TOMMY: "+nombre+"</a>");
        out.println("<ul class='navbar-nav px-3'>");
        out.println("<li class='nav-item text-nowrap'>");
        out.println("<a class='nav-link' href='loginServlet'>Salir</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("</header>");
        out.println("<!DOCTYPE html>");
        out.println("<html lang='es' dir='ltr'>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Maestro</title>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='row'>");
        out.println("<div class='col-sm-1'></div>");
        out.println("<div class='col-sm-10'>");
        out.println("<div id='carouselExampleControls' class='carousel slide' data-ride='carousel'>");
        out.println("<div class='carousel-inner'>");
        out.println("<div class='carousel-item active'>");
        out.println("<img class='d-block w-100' src='img/libros.png' alt=' slide'>");
        out.println("<div style='background: rgba(0, 0, 0, .5);' class='carousel-caption d-none d-md-block'>");
        out.println("<h5>Actividad 1</h5>");
        out.println("<p>Crea actividad de comprensión lectora y pon a prueba a tus alumnos con preguntas y lecturas</p>  ");
        out.println("<a href='maestroActividades1Servlet' class='btn btn-info' id='irAGestionarA1'>Entrar</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='carousel-item'>");
        out.println("<img class='d-block w-100' src='img/estamos-trabajando.jpg' alt='Third slide'>");
        out.println("<div style='background: rgba(0, 0, 0, .5);' class='carousel-caption d-none d-md-block'>");
        out.println("<h5>Actividad 2</h5>");
        out.println("<p>Crea tableros de relación de imagenes y nombres y pon a prueba a tus alumnos (proximamente)</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<a class='carousel-control-prev' href='#carouselExampleControls' role='button' data-slide='prev'>");
        out.println("<span class='carousel-control-prev-icon' aria-hidden='true'></span>");
        out.println("<span class='sr-only'>Previous</span>");
        out.println("</a>");
        out.println("<a class='carousel-control-next' href='#carouselExampleControls' role='button' data-slide='next'>");
        out.println("<span class='carousel-control-next-icon' aria-hidden='true'></span>");
        out.println("<span class='sr-only'>Next</span>");
        out.println("</a>");
        out.println("</div> ");
        out.println("</div>");
        out.println("<div class='col-sm-1'></div>");
        out.println("</div>");
        out.println("</div>");
        out.println("");
        out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>");
        out.println("<script src='http://code.jquery.com/jquery-latest.min.js'></script>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js'></script>");
        out.println("<script src='https://unpkg.com/feather-icons/dist/feather.min.js'></script>");
        out.println("<script>");
        out.println("feather.replace()");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");

    }
}

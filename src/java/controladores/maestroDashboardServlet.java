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
public class maestroDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    
    public void pintar(PrintWriter out,String nombre){
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("");
        out.println("<head>");
        out.println("");
        out.println("<meta charset='utf-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no'>");
        out.println("<meta name='description' content=''>");
        out.println("<meta name='author' content=''>");
        out.println("");
        out.println("<title>Maestro</title>");
        out.println("");
        out.println("<!-- Bootstrap core CSS -->");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("");
        out.println("<!-- Custom fonts for this template -->");
        out.println("<link href='css/font-awesome.min.css' rel='stylesheet' type='text/css'>");
        out.println("<link href='css/simple-line-icons.css' rel='stylesheet' type='text/css'>");
        out.println("<link href='https://fonts.googleapis.com/css?family=Lato:300,400,700,300italic,400italic,700italic' rel='stylesheet' type='text/css'>");
        out.println("");
        out.println("<!-- Custom styles for this template -->");
        out.println("<link href='css/landing-page.min.css' rel='stylesheet'>");
        out.println("<link rel='stylesheet' type='text/css' href='css/fondo.css'>");
        out.println("</head>");
        out.println("");
        out.println("<body>");
        out.println("");
        out.println("<!-- Navigation -->");
        out.println("<nav class='navbar navbar-dark bg-dark static-top'>");
        out.println("<div class='container'>");
        out.println("<a class='navbar-brand' href='#'>El abc de Tommy</a>");
        out.println("<a class='nav-link text-white' href='loginServlet'>Salir</a>");
        out.println("</div>");
        out.println("</nav>");
        out.println("");
        out.println("<!-- Masthead -->");
        out.println("<header class='jumbotron subhead text-white' id='headerFondo'>");
        out.println("<div class=''></div>");
        out.println("<div class='container'>");
        out.println("<div class='row'>");
        out.println("<div id='fondoLetras' class='col-xl-9 mx-auto'>");
        out.println("<h1 class='mb-5'>Bienvenido : </h1>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</header>");
        out.println("");
        out.println("<!-- Icons Grid -->");
        out.println("<section class='features-icons bg-light text-center'>");
        out.println("<div class='container'>");
        out.println("<div class='row'>");
        out.println("<div class='col-lg-4'>");
        out.println("<div class='features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3'>");
        out.println("<div class='features-icons-icon d-flex'>");
        out.println("<i class='icon-notebook m-auto text-primary'></i>");
        out.println("</div>");
        out.println("<h3><a class='text-dark' href='maestroActividades1Servlet'>Actividad1</a></h3>");
        out.println("<p class='lead mb-0'>Crea actividades tipo 1, donde tendras que subir un video explicando</p>");
        out.println("<p class='lead mb-0'>la actividad. Seguido, tendras que subir un texto que podrás convertir</p>");
        out.println("<p class='lead mb-0'>en audio y finalmente subir preguntas de esa lectura</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col-lg-4'>");
        out.println("<div class='features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3'>");
        out.println("<div class='features-icons-icon d-flex'>");
        out.println("<i class='icon-book-open m-auto text-primary'></i>");
        out.println("</div>");
        out.println("<h3><a class='text-dark' href='maestroActividades2Servlet'>Actividad2</a></h3>");
        out.println("<p class='lead mb-0'>Crea actividades tipo 2, donde tendras que subir temas</p>");
        out.println("<p class='lead mb-0'>cada tema tendra 5 palabras y después se generará un juego de</p>");
        out.println("<p class='lead mb-0'>ahorcado, cada vez que hagan una palabra se mostrará su significado.</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col-lg-4'>");
        out.println("<div class='features-icons-item mx-auto mb-0 mb-lg-3'>");
        out.println("<div class='features-icons-icon d-flex'>");
        out.println("<i class='icon-clock m-auto text-primary'></i>");
        out.println("</div>");
        out.println("<h3>Proximamente</h3>");
        out.println("<p class='lead mb-0'>Estamos trabajando en algo diferente</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</section>");
        out.println("<!-- Bootstrap core JavaScript -->");
        out.println("<script src='js/jquery.min.js'></script>");
        out.println("<script src='js/bootstrap.bundle.min.js'></script>");
        out.println("");
        out.println("</body>");
        out.println("");
        out.println("</html>");

    }
}

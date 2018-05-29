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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            Persona usuario = (Persona)session.getAttribute("usuario");
            pintar(out,usuario.getNombre());
        }
    }
    
    public void pintar(PrintWriter out,String nombre){
        out.println("<!DOCTYPE html>");
        out.println("<html lang='es' dir='ltr'>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<title>Maestro</title>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' href='css/cards.css' />");
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
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<h2 style='margin-left : 70px;'>Bienvenido: "+nombre+"</h2>");
        out.println("<br />");
        out.println("<div class='container'>");
        out.println("<div class='row'>");
        out.println("<div class='col'>");
        out.println("<div class='card text-white bg-success mb-3' style='width: 18rem;' >");
        out.println("<div class='card-img-wrap'>");
        out.println("<img class='card-img-top' src='img/student.jpg' alt='Card image cap'>");
        out.println("</div>");
        out.println("<div class='card-block'>");
        out.println("<div class='mx-auto' style='width: 200px;'>");
        out.println("<h4 class='card-title'>Estudiantes</h4>");
        out.println("</div>");
        out.println("<div class='card-body'>");
        out.println("<p class='card-text'>Aquí puedes eliminar, editar y agregar a tus alumnos, así como de ver sus actividades.</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='card-footer bg-success '>");
        out.println("<a href=''  href='#' class='btn btn-info' role='button'>Ver mas</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col'>");
        out.println("<div class='card text-white bg-danger mb-3' style='width: 18rem;' >");
        out.println("<div class='card-img-wrap'>");
        out.println("<img class='card-img-top' src='img/actividades.jpg' alt='Card image cap'>");
        out.println("</div>");
        out.println("<div class='card-block'>");
        out.println("<div class='mx-auto' style='width: 200px;'>");
        out.println("<h4 class='card-title'>Actividades</h4>");
        out.println("</div>");
        out.println("<div class='card-body'>");
        out.println("<p class='card-text'>Aquí puedes eliminar, editar y agregar tus activides.</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='card-footer bg-danger '>");
        out.println("<a href=''  href='' class='btn btn-info' role='button'>Ver mas</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col'>");
        out.println("<div class='card text-white bg-dark mb-3' style='width: 18rem;' >");
        out.println("<div class='card-img-wrap'>");
        out.println("<img class='card-img-top' src='img/Clase.jpg' alt='Card image cap'>");
        out.println("</div>");
        out.println("<div class='card-block'>");
        out.println("<div class='mx-auto' style='width: 200px;'>");
        out.println("<h4 class='card-title'>Grupos</h4>");
        out.println("</div>");
        out.println("<div class='card-body'>");
        out.println("<p class='card-text'>Crea, elimina, edita grupos y ve cuantos alumnos hay en cada uno.</p>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='card-footer bg-dark '>");
        out.println("<a href=''  href='#' class='btn btn-info' role='button'>Ver mas</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
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

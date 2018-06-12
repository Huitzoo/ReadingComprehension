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
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
public class maestroActividades1Servlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        if(session.getAttribute("usuario") != null){
            Persona usuario = (Persona)session.getAttribute("usuario");
            session.setAttribute("usuario",usuario);
            PrintWriter out = response.getWriter();
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("baseDedatos/xml");
            List<Actividad1> actividades = null;
            try {
                actividades = obtenerXML.obtenerListaDeActividades1(path);

            } catch (JAXBException ex) {
                Logger.getLogger(maestroActividades1Servlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<Actividad1> actividad1 = new ArrayList<>();
            if(actividades != null){
                actividad1 = actividades.stream().filter(e -> e.getIdMaestro() == usuario.getId()).collect(Collectors.toList());
            }
            pintar(out,actividad1);
        }else{
            response.sendRedirect("loginServlet");
        }
    }
    
    public static void pintar(PrintWriter out,List<Actividad1> actividades){
      
        out.println("<!DOCTYPE html>");
        out.println("<html lang='es' dir='ltr'>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' href='css/dashboard.css' />");
        out.println("<title>Gestionar Act1</title>");
        out.println("<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>");
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
        
        out.println("<main class='bd-masthead' id='content' role='main'>");
        out.println("<center>");
        out.println("<h2>Actividad 1</h2>");
        out.println("<h5>Estas actividades se tratan de leer una lectura o escucharla y responder las preguntas.</h5>");
        out.println("</center>");
        out.println("<br>");
        out.println("<center>");
        out.println("<a style='margin-rigth: 50px;' href='crearEditarActividad1Servlet' type='button' class='btn btn-light'>");
        out.println("<img src='package/build/svg/plus.svg'/>Agregar");
        out.println("</a>");
        
        out.println("<button style='margin-rigth: 50px;' type='button' class='btn btn-primary' data-toggle='modal' data-target='#addVideo'>");
        out.println("<img src='package/build/svg/plus.svg'/>Agregar video");
        out.println("</buton>"); 
        out.println("</center>");
 

        
        out.println("<div class='modal fade' id='addVideo' tabindex='-1' role='dialog' aria-labelledby='addVideo' aria-hidden='true'>");
        out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Agregar</h5>");
        out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
        out.println("<span aria-hidden='true'>&times;</span>");
        out.println("</button>");
        out.println("</div>");
        
        out.println("<form action='ajaxVideo1' method='post' enctype='multipart/form-data'>");
        out.println("<div class='modal-body'>");
        out.println("<div class='form-group'>");
        out.println("<label for='nombre'>Nombre</label>");
        out.println("<input required name='video' type='file' class='form-control' id='video'>");
        out.println("</div>");
       
        out.println("<div class='modal-footer'>");
        out.println("<button id='modalAgregar' type='submit' class='btn btn-success'>Agregar</button>");
        out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
        out.println("</div>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>"); 
        
        out.println("</center>");
        out.println("<br>");
        
        
        
        
        out.println("<br>");
        out.println("<div class='container'>");
        int i = 0;
        if(0 == actividades.size()){
           out.println("<p>No has creado actividades</p>");
        }else{
            for(i = 0; i< actividades.size() ; i++){
                    if(i%3==0){
                        out.println("<div class='row'>"); 
                    }
                    out.println("<div class='col sm-4'>");
                    out.println("<div class='card' style='width: 14rem;'>");
                    out.println("<img class='card-img-top img-thumbnail' src='"+actividades.get(i).getPathImagen()+"' alt='Card image cap'>");
                    out.println("<div class='card-body'>");
                    out.println("<h5 class='card-title'>"+actividades.get(i).getNombre()+"</h5>");
                    out.println("<p class='card-text'>Numero de preguntas: </p>");
                    out.println("<p class='card-text'>"+actividades.get(i).getPreguntas().getPreguntas().size()+"</p>");
                    out.println("</div>");
                    out.println("<ul class='list-group list-group-flush'>");
                    out.println("<li class='list-group-item'>");
                    out.println("<a type='button' class='btn btn-success' href='crearEditarActividad1Servlet?id="+actividades.get(i).getId()+"'>");
                    out.println("<img src='package/build/svg/pencil.svg'/>");
                    out.println("</a>");
                    out.println("<a type='button' class='btn btn-ligth' data-toggle='modal' data-target='#deleteM"+actividades.get(i).getId()+"'>");
                    out.println("<img src='package/build/svg/x.svg'/>");
                    out.println("</a>");
                    out.println("<a href='visualizarActividad1Servlet?id="+actividades.get(i).getId()+"' type='button' class='btn btn-danger' title='Visualizar'>");
                    out.println("<img src='package/build/svg/tasklist.svg'/>");
                    out.println("</a>");
                    out.println("</li>");
                    out.println("</ul>");
                    out.println("</div>");
                    out.println("<br>");
                    out.println("</div>");
                    out.println("<div class='modal fade' id='deleteM"+actividades.get(i).getId()+"' tabindex='-1' role='dialog' aria-labelledby='delete' aria-hidden='true'>");
                    out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                    out.println("<div class='modal-content'>");
                    out.println("<div class='modal-header'>");
                    out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Eliminar</h5>");
                    out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                    out.println("<span aria-hidden='true'>&times;</span>");
                    out.println("</button>");
                    out.println("</div>");
                    out.println("<div class='modal-body'>");
                    out.println("¿Esta seguro de eliminar la actividad "+actividades.get(i).getNombre()+"?");
                    out.println("</div>");
                    out.println("<div class='modal-footer'>");
                    out.println("<button id='eliminar"+actividades.get(i).getId()+"' type='submit' class='btn btn-danger'>Eliminar</button>");
                    out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<script>");
                    out.println("$('#eliminar"+actividades.get(i).getId()+"').click(function(){");
                    out.println("$.ajax({");
                    out.println("url:'ajaxActividad1',");
                    out.println("data:{id:'"+actividades.get(i).getId()+"'},");
                    out.println("type:'get',");
                    out.println("cache:false,");
                    out.println("success:(function(data){");
                    out.println("swal('Eliminado correctamente','Da click en el boton','success').then((value) => { location.reload(); });");
                    out.println("})");
                    out.println("})");
                    out.println("})");
                    out.println(" </script>");
                    
                    if((i+1)%3==0){
                        out.println("</div>"); 
                    }
            }
        }
        if((i)%3!=0){
            out.println("</div>"); 
        }
        out.println("</div>");
        out.println("</main>");
        out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>");
        out.println("<script src='http://code.jquery.com/jquery-latest.min.js'></script>");
        out.println("<script src='https://code.jquery.com/ui/1.12.1/jquery-ui.js'></script>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js'></script>");
        out.println("<script src='https://unpkg.com/feather-icons/dist/feather.min.js'></script>");
        out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");

        out.println("<script>");
        out.println("feather.replace()");
        out.println("</script>");
        
        out.println("<script>");
        out.println("$(document).on('click', '#delete', function() { // When HTML DOM 'click' event is invoked on element with ID 'somebutton', execute the following function...");
        out.println("$.get('someservlet', function(responseText) {   // Execute Ajax GET request on URL of 'someservlet' and execute the following function with Ajax response text...");
        out.println("$('#delete').text(responseText);           // Locate HTML DOM element with ID 'somediv' and set its text content with the response text.");
        out.println("});");
        out.println("});");
        out.println("</script>");
        out.println("</body>");
        out.println("");
        out.println("</html>");
    }
}

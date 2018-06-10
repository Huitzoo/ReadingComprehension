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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class crearEditarActividad1Servlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        if(session.getAttribute("usuario") != null){
                Persona usuario = (Persona)session.getAttribute("usuario");
                session.setAttribute("usuario", usuario);
                if(request.getParameter("id") == null){
                    pintar(out,null,0);
                }else{
                    int id = Integer.parseInt(request.getParameter("id"));
                    ServletContext context = request.getServletContext();
                    String path = context.getRealPath("/baseDeDatos/xml"); 
                    List<Actividad1> actividad = null;
                    List<Actividad1> s = null;
                    try {
                        actividad = obtenerXML.obtenerListaDeActividades1(path);
                        s = actividad.stream().filter(e -> e.getId() == id).collect(Collectors.toList());
                    } catch (JAXBException ex) {
                        Logger.getLogger(crearEditarActividad1Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pintar(out,s.get(0),id);
                }
        }else{
            response.sendRedirect("loginServlet");
        }
    }
    
    public static void pintar(PrintWriter out,Actividad1 actividad,int id){

        out.println("<!DOCTYPE html>");
        out.println("<!--");
        out.println("To change this license header, choose License Headers in Project Properties.");
        out.println("To change this template file, choose Tools | Templates");
        out.println("and open the template in the editor.");
        out.println("-->");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>ElABCdeTommy</title>");
        out.println("<meta charset='UTF-8'>");
        out.println("<link rel='stylesheet' href='css/dropzone.css'>");
        out.println("<link rel='stylesheet' href='css/drop.css'>");
        out.println("<link rel='stylesheet' href='css/color.css'>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js'></script>");
        out.println("<script type='text/javascript' src='js/dropzone.js'></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<header style='margin-bottom: 80px;' class='navbar navbar-expand navbar-dark flex-column flex-md-row bd-navbar'>");
        out.println("<nav class='navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow'>");
        out.println("<a class='navbar-brand col-sm-3 col-md-2 mr-0' href='#'>El ABC de TOMMY</a>");
        out.println("<ul class='navbar-nav px-3'>");
        out.println("<li class='nav-item text-nowrap'>");
        out.println("<a class='nav-link' href='loginServlet'>Salir</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("</header>");
        out.println("<main class='bd-masthead' id='content' role='main'>");
        out.println("<div class='container-fluid'>");
        out.println("<div class='row'>");
        out.println("<div class='col-sm-2'>");
        out.println("</div>");
        out.println("<div class='col-sm-8'>   ");
        out.println("<ul class='nav nav-pills mb-3' id='pills-tab' role='tablist'>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link btn btn-dark active' id='pills-home-tab' data-toggle='pill' href='#pills-home' role='tab' aria-controls='pills-home' aria-selected='true'>Lectura</a>");
        out.println("</li>");
        out.println("<li class='nav-item '>");
        out.println("<a class='nav-link btn btn-dark' id='pills-profile-tab' data-toggle='pill' href='#pills-profile' role='tab' aria-controls='pills-profile' aria-selected='false'>Audio</a>");
        out.println("</li>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link btn btn-dark' id='pills-contact-tab' data-toggle='pill' href='#pills-contact' role='tab' aria-controls='pills-contact' aria-selected='false'>Preguntas</a>");
        out.println("</li>");
        out.println("<li class='nav-item px-3'>");
        out.println("<a class='nav-link btn btn-info' id='salvar' data-toggle='pill' href='#' role='tab' aria-controls='pills-contact' aria-selected='false'>Salvar</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("<div class='tab-content jumbotron' style='background:linear-gradient(30deg, crimson,sienna, royalblue, indianred, purple);' id='pills-tabContent'>");
        out.println("<div class='tab-pane fade show active' id='pills-home' role='tabpanel' aria-labelledby='pills-home-tab'>");
        out.println("<h1 class='display-5' style='color: white'>Subir lectura.</h1>");
        out.println("<h4 style='color: white'>Dropea una lectura y una imagen representativa.</h4>");
        out.println("<hr class='my-4'>");
        out.println("<form id='myDropzone' method='post' action='files' class='dropzone'>");
        out.println("<div class='fallback'>");
        out.println("<input name='file' type='file' />");
        out.println("</div>");
        out.println("</form>");
        out.println("<hr class='my-4'>");
        out.println("</div>  ");
        out.println("<div class='tab-pane fade' id='pills-profile' role='tabpanel' aria-labelledby='pills-profile-tab'>");
        out.println("<div class='container'>");
        out.println("<h1 class='display-5' style='color: white'>Convierte tu lectura en audio.</h1>");
        out.println("<h4 style='color: white'>Da click en convertir si quieres convertir tu texto en audio.</h4><br>");
        out.println("<div class='row'>");
        out.println("<div class='col-sm-6'>");
        out.println("<button class='btn btn-info' id='convertir'>Convertir</button>");
        out.println("</div>");
        out.println("<div class='col-sm-6'>");
        out.println("<audio id ='audioS' controls>");
        out.println("</audio>");
        out.println("<script>");
        out.println("$('#convertir').click(function(){");
        out.println("alert('g');");
        out.println("$.ajax({");
        out.println("url:'ajaxVoice',");
        out.println("type:'post',");
        out.println("cache:false,");
        out.println("success:(function(data){");
        out.println("setTimeout(5000);");
        out.println("swal('Creado correctamente','Da click en el boton','success').then((value) => {");
        out.println("$('#audioS').empty();");
        out.println("$('#audioS').append('<source src=\"'+data+'\" type=\"audio/wav\">');});");
        out.println("})");
        out.println("})");
        out.println("});");
        out.println("</script>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='tab-pane fade' id='pills-contact' role='tabpanel' aria-labelledby='pills-contact-tab'>");
        out.println("<h1 class='display-5' style='color: white'>Agrega tus preguntas</h1>");
        out.println("<h4 style='color: white'>Agrega preguntas relacionadas a tu lectura.</h4><br>");
        out.println("<br><br>");
        out.println("<form id='actividad'>");
        out.println("<div class='container'>");
        out.println("<div class='row'>");
        out.println("<div class='col-sm-2'>");
        out.println("<button type='button' id='agregarP' class='btn btn-success'>+Agregar</button>");
        out.println("</div>");
        out.println("<div class='col-sm-8' id='preguntas'>");
        out.println("<input name='pregunta_0' class='form-control' type='text' placeholder='¿Que es un bit?'/>");
        int cont = 0;
        if(actividad != null){
            List<String> p = actividad.getPreguntas().getPreguntas();
            for(String s : p){
                cont = cont + 1;
                out.println("<input name='r_"+cont+"' value='"+s+"'class='form-control' type='text' placeholder='¿Que es un bit?'/>");
            }
        }
        out.println("<br>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col-sm-2'>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div> ");
        out.println("</main>");
        out.println("<script src='http://code.jquery.com/jquery-latest.min.js'></script>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js'></script>");
        out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");
        out.println("<script>");
        out.println("Dropzone.options.myDropzone = {");
        out.println("maxFiles: 2,");
        out.println("addRemoveLinks: true,");
        out.println("acceptedFiles: '.txt,.png,.jpg',");
        out.println("dictDefaultMessage: 'Dropea tu lectura .txt en este drop',");
        out.println("init: function() {");
        out.println("var self = this;");
        out.println("self.options.addRemoveLinks = true;");
        out.println("self.options.dictRemoveFile = 'Delete';");
        out.println("this.on('complete', function (file) {");
        out.println("setTimeout(3000);");
        out.println("swal('Añadido correctamente','Da click en el boton','success').then((value) => {                                    ");
        out.println("setTimeout(1000);");
        out.println("this.removeFile(file); ");
        out.println("});");
        out.println("});");
        out.println("}    ");
        out.println("};");
        out.println("</script>");
        out.println("<script>");
        out.println("var cont = 0;");
        out.println("$('#agregarP').click(function(){");
        out.println("cont = cont + 1;");
        out.println("console.log(cont);");
        out.println("$('#preguntas').append('<input class=\"form-control\" type=\"text\" name=\"pregunta_'+cont+'\" placeholder=\"¿Que es un bit?\" /><br>');");
        out.println("});");
        out.println("</script>");
        out.println("<script>");
        out.println("$('#salvar').click(function(){");
        out.println("var preguntas = $('#actividad').serializeArray();");
        out.println("var values = {};");
        out.println("console.log(preguntas);");
        out.println("$.each(preguntas,function(i,field){");
        out.println("values[field.name] = field.value;     ");
        out.println("});");
        out.println("console.log(values);");
        out.println("$.ajax({");
        out.println("url:'ajaxActividad1',");
        out.println("data:{values,id:'"+id+"'},");
        out.println("type:'post',");
        out.println("cache:false,");
        out.println("success:(function(data){");
        out.println("setTimeout(2000);");
        out.println("swal('Creado correctamente','Da click en el boton','success').then((value) => {                                    ");
        out.println("document.location.href='maestroActividades1Servlet';");
        out.println("});");
        out.println("})");
        out.println("});");
        out.println("});");
        out.println("</script>");
        out.println("</body>");
        out.println("</html>");
        out.println("");
    }
}

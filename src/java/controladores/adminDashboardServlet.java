/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Alumno;
import beans.Grupo;
import beans.Maestro;
import beans.Persona;
import cruds.crudAlumno;
import cruds.crudGrupo;
import cruds.crudMaestro;
import consultas.obtenerXML;
import consultas.principales;
import cruds.crudPersonas;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;
/**
 *
 * @author huitz
 */
public class adminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        if(session.getAttribute("usuario") != null){
                Persona usuario = (Persona)session.getAttribute("usuario");
                session.setAttribute("usuario", usuario);
                int bandera = 0;
                if(session.getAttribute("bandera")!=null){
                    bandera = (int)session.getAttribute("bandera");
                    session.removeAttribute("bandera");
                }
                ServletContext context = request.getServletContext();
                String path = context.getRealPath("/baseDeDatos/xml"); 
                /*Obtener a mis usuarios*/
                List<Persona> personas;
                List<List<String>> alumnos_maestros = null;
                List<Grupo> grupos = null;
                try {
                    personas = obtenerXML.obtenerListaUsuarios(path);
                    alumnos_maestros = obtenerXML.obtenerMaestrosYAlumnos(path,personas);
                    grupos = obtenerXML.obtenerListaGrupos(path);
                } catch (JAXBException ex) {
                    Logger.getLogger(adminDashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                List<String> salidaGrupos = principales.obtenerGrupo(grupos);
                pintar(out,usuario,alumnos_maestros.get(1),alumnos_maestros.get(0),salidaGrupos,bandera);
        }else{
            response.sendRedirect("loginServlet");
        }
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException {
        /*Agregar maestros y alumnos*/
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        session.setAttribute("usuario",usuario);
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml");
        int id = 0;   
        String maestrosSelect = request.getParameter("idMaestro");
        try {
             if(maestrosSelect != null ){ //Se agregando a un grupo
                String nombre = request.getParameter("nombre");
                String asignatura = request.getParameter("asignatura");
                id = principales.getIdGrupo(path);
                crudGrupo.crearGrupo(path, id, maestrosSelect,nombre,asignatura);
            }else{
                String[] datos = {request.getParameter("nombre"),request.getParameter("apellidos").replace(" ","_"),request.getParameter("contra"),request.getParameter("usuario"),request.getParameter("sexo"),request.getParameter("cumple")};
                int edad = Integer.parseInt(request.getParameter("edad"));
                String ciclo = request.getParameter("ciclo");
                if(ciclo == null){ //Si es null se esta agregando a un profesor
                    String[] datosM = {request.getParameter("correo"),request.getParameter("especialidad"),request.getParameter("escuela")};
                    id = crudPersonas.crearPersona(path,datos,edad,2);
                    crudMaestro.crearMaestro(path,datosM, id);
                }else{//sino, se esta agregando a un alumno
                    String grupo = "";
                    if("0".equals(request.getParameter("grupo"))){
                       grupo = "Sin Asignar";
                    }else{
                       grupo = obtenerXML.obtenerGrupoYAgregar(path,Integer.parseInt(request.getParameter("grupo")),null,2);
                       System.out.println(grupo);
                    }
                    id = crudPersonas.crearPersona(path,datos,edad,3);
                    String[] datosM = {ciclo,grupo,request.getParameter("idMaestroAlumnos"),request.getParameter("grupo")};
                    crudAlumno.crearAlumno(path,datosM,id);
                }
            }
            session.setAttribute("bandera", 3);
            response.sendRedirect("adminDashboardServlet");
        }catch (JAXBException ex) {
            Logger.getLogger(adminDashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
    } 
    public void pintar(PrintWriter out,Persona usuario,List<String> maestros,List<String> alumnos, List<String> grupos,int bandera){
        out.println("<!DOCTYPE html>");
        out.println("<html lang='es' dir='ltr'>");
        out.println("<head>");
        out.println("<meta charset='utf-8'>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
        out.println("<link rel='stylesheet' href='css/dashboard.css' />");
        out.println("<link rel='stylesheet' href='https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css'>");
        out.println("<title>Administracion</title>");
        out.println("<script src='https://code.jquery.com/jquery-3.1.0.min.js'></script>");
        out.println("</head>");
        out.println("<body>");
        out.println("<nav class='navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow'>");
        out.println("<a class='navbar-brand col-sm-3 col-md-2 mr-0' href='#'>El ABC de TOMMY</a>");
        
        if(bandera==1){
            out.println("<div id='alerta' class='alert alert-success' role='alert'>");
            out.println("Se ha editado con exito");
            out.println("</div>");
        }else if(bandera == 3){
            out.println("<div id='alerta' class='alert alert-success' role='alert'>");
            out.println("Se ha agregado correctamente");
            out.println("</div>");
        }
        out.println("<script>");
        out.println("$('#alerta').show().delay(3000).fadeOut();");
        out.println("</script>");
        
        out.println("<ul class='navbar-nav px-3'>");
        out.println("<li class='nav-item text-nowrap'>");
        out.println("<a class='nav-link' href='loginServlet'>Salir</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</nav>");
        out.println("<div class='container-fluid'>");
        out.println("<div class='row'>");
        out.println("<nav class='col-md-2 d-none d-md-block bg-light sidebar'>");
        out.println("<div class='sidebar-sticky'>");
        out.println("<ul class='nav nav-tabs flex-column' id='myTab' role='tablist'>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link active' id='principal-tab' data-toggle='tab' href='#principal' role='tab' aria-controls='principal' aria-selected='true'>");
        out.println("<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-home'><path d='M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z'></path><polyline points='9 22 9 12 15 12 15 22'></polyline></svg>");
        out.println("Principal <span class='sr-only'>(current)</span>");
        out.println("</a>");
        out.println("</li>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link' id='maestros-tab' data-toggle='tab' href='#maestros' role='tab' aria-controls='maestros' aria-selected='false'>");
        out.println("<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-users'><path d='M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2'></path><circle cx='9' cy='7' r='4'></circle><path d='M23 21v-2a4 4 0 0 0-3-3.87'></path><path d='M16 3.13a4 4 0 0 1 0 7.75'></path></svg>");
        out.println("Maestros");
        out.println("</a>");
        out.println("</li>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link' id='alumnos-tab' data-toggle='tab' href='#alumnos' role='tab' aria-controls='alumnos' aria-selected='false'>");
        out.println("<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-users'><path d='M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2'></path><circle cx='9' cy='7' r='4'></circle><path d='M23 21v-2a4 4 0 0 0-3-3.87'></path><path d='M16 3.13a4 4 0 0 1 0 7.75'></path></svg>");
        out.println("Alumnos");
        out.println("</a>");
        out.println("</li>");
        out.println("<li class='nav-item'>");
        out.println("<a class='nav-link' id='grupos-tab' data-toggle='tab' href='#grupos' role='tab' aria-controls='grupos' aria-selected='false'>");
        out.println("<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round' class='feather feather-users'><path d='M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2'></path><circle cx='9' cy='7' r='4'></circle><path d='M23 21v-2a4 4 0 0 0-3-3.87'></path><path d='M16 3.13a4 4 0 0 1 0 7.75'></path></svg>");
        out.println("Grupos");
        out.println("</a>");
        out.println("</li>");
        out.println("</ul>");
        out.println("</div>");
        out.println("</nav>");
        
        out.println("<main role='main' class='col-md-9 ml-sm-auto col-lg-10 px-4'>");
        
        out.println("<div class='tab-content'>");
        out.println("<div class='tab-pane active' id='principal' role='tabpanel' aria-labelledby='principal-tab'>");
        out.println("<div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom'>");
        out.println("<h1 class='h2'>Administración</h1>");
        out.println("</div>");
        out.println("<p>");
        out.println("Sistema para administrar a tus profesores y alumnos.");
        out.println("</p>");
        out.println("<p>");
        if("No tienes maestros registrados".equals(maestros.get(0))){
            out.println("<center>Numero de Maestros actuales: 0 </center>");
        }else{
            out.println("<center>Numero de Maestros actuales: "+maestros.size()+"</center>");
        }
        out.println("</p>");
        out.println("<p>");
   
        if("No tienes alumnos registrados".equals(alumnos.get(0))){
            out.println("<center>Numero de Alumnos actuales: 0 </center>");
        }else{
            out.println("<center>Numero de Alumnos actuales: "+alumnos.size()+"</center>");
        }
        out.println("</p>");
        out.println("<p>");
        
        if("No tienes grupos registrados".equals(grupos.get(0))){
            out.println("<center>Numero de Grupos actuales: 0 </center>");
        }else{
            out.println("<center>Numero de Grupos actuales: "+grupos.size()+"</center>");
        }
        out.println("</p>");
        out.println("</div>");
        
        /*Esta es la parte de los maestros*/
        out.println("<div class='tab-pane' id='maestros' role='tabpanel' aria-labelledby='maestros-tab'>");
        out.println("<div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom'>");
        out.println("<h1 class='h2'>Administración</h1>");
        out.println("</div>");
        out.println("<h2>Maestros</h2>");
        out.println("<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#agregarMaestro'>");
        out.println("<img src='package/build/svg/plus.svg'/>Agregar");
        out.println("</button>");
        out.println("<br /><br />");
        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-striped table-sm'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>id</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Apellidos</th>");
        out.println("<th>Usuario</th>");
        out.println("<th>Fecha de nacimiento</th>");
        out.println("<th>Edad</th>");
        out.println("<th>Correo</th>");
        out.println("<th>Escuela</th>");
        out.println("<th>Opciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        if("No tienes maestros registrados".equals(maestros.get(0))){
             out.println(maestros.get(0));
        }else{
            for(String maestro : maestros){
                String s[] = maestro.split(" ");
                out.println("<tr>");
                out.println("<td>"+s[0]+"</td>");
                out.println("<td>"+s[1].replace("_"," ")+"</td>");
                out.println("<td>"+s[2].replace("_"," ")+"</td>");
                out.println("<td>"+s[3]+"</td>");
                out.println("<td>"+s[4]+"</td>");
                out.println("<td>"+s[5]+"</td>");
                out.println("<td>"+s[6]+"</td>");
                out.println("<td>"+s[7]+"</td>");
                out.println("<td>");
                out.println("<button type='button' class='btn btn-info' data-toggle='modal' data-target='#editarM"+s[0]+"'>");
                out.println("<img src='package/build/svg/pencil.svg'/>");
                out.println("</button>");
                out.println("<button type='button' class='btn btn-danger' data-toggle='modal' data-target='#deleteM"+s[0]+"'>");
                out.println("<img src='package/build/svg/x.svg'/>");
                out.println("</button>");
                out.println("</td>");
                out.println("</tr>");

                /*Modal de los maestros para editar*/
                out.println("<div class='modal fade' id='editarM"+s[0]+"' tabindex='-1' role='dialog' aria-labelledby='editar"+s[0]+"' aria-hidden='true'>");
                out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                out.println("<div class='modal-content'>");
                out.println("<div class='modal-header'>");
                out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Editar</h5>");
                out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                out.println("<span aria-hidden='true'>&times;</span>");
                out.println("</button>");
                out.println("</div>");
                out.println("<div class='modal-body'>");
                out.println("<div class='form-group'>");
                out.println("<form action='ajaxMaestros' method='post'>");
                out.println("<label for='nombre"+s[0]+"'>Nombre</label>");
                out.println("<input value='"+s[1]+"' name='nombre' type='text' class='form-control' id='nombre"+s[0]+"' placeholder='Ingresa tu nombre(s)'>");
                out.println("</div>");
                out.println("<div class='form-group'>");
                out.println("<label for='apellidos"+s[0]+"'>Apellidos</label>");
                out.println("<input value='"+s[2]+"' name='apellidos' type='text' class='form-control' id='apellidos"+s[0]+"' placeholder='Ingresa tus apellidos'>");
                out.println("</div>");
                out.println("<div class='form-group ui-widget'>");
                out.println("<label for='usuario"+s[0]+"'>Usuario</label>");
                out.println("<input value='"+s[3]+"' name='usuario' type='text' class='form-control' id='usuario"+s[0]+"' placeholder='¿Cómo quieres que te llame?'>");
                out.println("</div>");
                out.println("<div class='form-group'>");
                out.println("<label for='edad"+s[0]+"'>Edad</label>");
                out.println("<input value='"+s[5]+"' name='edad' min='1' max='130' type='number' class='form-control' id='edad"+s[0]+"' placeholder='Ingresa tu edad'>");
                out.println("</div>");
                out.println("<div class='form-group'>");
                out.println("<label for='correo"+s[0]+"'>Correo</label>");
                out.println("<input value='"+s[6]+"' name='correo' type='email' class='form-control' id='correo"+s[0]+"' aria-describedby='emailHelp' placeholder='Ingresa tu email'>");
                out.println("</div>");
                out.println("<div class='form-group'>");
                out.println("<label for='asignatura"+s[0]+"'>Asignatura</label>");
                out.println("<input value='"+s[7]+"' name='asignatura' type='text' class='form-control' id='asignatura"+s[0]+"' aria-describedby='emailHelp' placeholder='Ingresa la asignatura'>");
                out.println("<input name='id' value='"+s[0]+"' type='hidden'>");
                out.println("<input name='rol' value='2' type='hidden'>");
                out.println("</div>");
                out.println("</div>");
                out.println("<div class='modal-footer'>");
                out.println("<button id='modalEditar"+s[0]+"' type='submit' class='btn btn-warning'>editar</button>");
                out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                
                /*Modal de los maestros para elimiar*/
                out.println("<div class='modal fade' id='deleteM"+s[0]+"' tabindex='-1' role='dialog' aria-labelledby='delete' aria-hidden='true'>");
                out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                out.println("<div class='modal-content'>");
                out.println("<div class='modal-header'>");
                out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Eliminar</h5>");
                out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                out.println("<span aria-hidden='true'>&times;</span>");
                out.println("</button>");
                out.println("</div>");
                out.println("<div class='modal-body'>");
                out.println("¿Esta seguro de eliminar a "+ s[1]+"?");
                out.println("</div>");
                out.println("<div class='modal-footer'>");
                out.println("<button id='eliminar"+s[0]+"' type='submit' class='btn btn-danger'>Eliminar</button>");
                out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<script>");
                out.println("$('#eliminar"+s[0]+"').click(function(){");
                out.println("$.ajax({");
                out.println("url:'ajaxMaestros',");
                out.println("data:{id:'"+s[0]+"'},");
                out.println("type:'get',");
                out.println("cache:false,");
                out.println("success:(function(data){");
                out.println("swal('Eliminado correctamente','Da click en el boton','success').then((value) => {document.location.href ='http://localhost:8080/ReadingComprehension/adminDashboardServlet'});");
                out.println("})");
                out.println("})");
                out.println("})");
                out.println(" </script>");
            }
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</div>");
        
        /*--------------- SECCION DE ALUMNOS*/
        out.println("<div class='tab-pane' id='alumnos' role='tabpanel' aria-labelledby='alumnos-tab'>");
        out.println("<div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom'>");
        out.println("<h1 class='h2'>Administración</h1>");
        out.println("</div>");
        out.println("<h2>Alumnos</h2>");
        out.println("<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#agregarAlumno'>");
        out.println("<img src='package/build/svg/plus.svg'/>Agregar");
        out.println("</button>");
        out.println("<br /><br />");
        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-striped table-sm'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>id</th>");
        out.println("<th>Nombre</th>");
        out.println("<th>Apellidos</th>");
        out.println("<th>Usuario</th>");
        out.println("<th>Fecha de nacimiento</th>");
        out.println("<th>Edad</th>");
        out.println("<th>Maestro</th>");
        out.println("<th>Grupo</th>");
        out.println("<th>Opciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        
        if("No tienes alumnos registrados".equals(alumnos.get(0))){
             out.println(alumnos.get(0));
        }else{
            for(String alumno : alumnos){
                String[] s = alumno.split(" ");
                out.println("<tr>");
                out.println("<td>"+s[0]+"</td>");
                out.println("<td>"+s[1]+"</td>");
                out.println("<td>"+s[2]+"</td>");
                out.println("<td>"+s[3]+"</td>");
                out.println("<td>"+s[4]+"</td>");
                out.println("<td>"+s[5]+"</td>");
                out.println("<td>"+s[7].replace("_"," ")+"</td>");
                out.println("<td>"+s[8]+"</td>");
                
                out.println("<td>");
                out.println("<button type='button' class='btn btn-info' data-toggle='modal' data-target='#editarA"+s[0]+"'>");
                out.println("<img src='package/build/svg/pencil.svg'/>");
                out.println("</button>");
                out.println("<button type='button' class='btn btn-danger' data-toggle='modal' data-target='#deleteA"+s[0]+"'>");
                out.println("<img src='package/build/svg/x.svg'/>");
                out.println("</button>");
                out.println("</td>");
                out.println("</tr>");

                /*Modal de los alumnos para editar*/
                out.println("<div class='modal fade' id='editarA"+s[0]+"' tabindex='-1' role='dialog' aria-labelledby='editar"+s[0]+"' aria-hidden='true'>");
                out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                out.println("<div class='modal-content'>");
                out.println("<div class='modal-header'>");
                out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Editar</h5>");
                out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                out.println("<span aria-hidden='true'>&times;</span>");
                out.println("</button>");
                out.println("</div>");
                out.println("<div class='modal-body'>");
                out.println("<div class='form-group'>");
                out.println("<form action='ajaxAlumnos' method='post'>");
                out.println("<label for='nombre"+s[0]+"'>Nombre</label>");
                out.println("<input value='"+s[1]+"' name='nombre' type='text' class='form-control' id='nombre"+s[0]+"' placeholder='Ingresa tu nombre(s)'>");
                out.println("</div>");
                out.println("<div class='form-group'>");
                out.println("<label for='apellidos"+s[0]+"'>Apellidos</label>");
                out.println("<input value='"+s[2].replace("_"," ")+"' name='apellidos' type='text' class='form-control' id='apellidos"+s[0]+"' placeholder='Ingresa tus apellidos'>");
                out.println("</div>");
                out.println("<div class='form-group ui-widget'>");
                out.println("<label for='usuario"+s[0]+"'>Usuario</label>");
                out.println("<input value='"+s[3]+"' name='usuario' type='text' class='form-control' id='usuario"+s[0]+"' placeholder='¿Cómo quieres que te llame?'>");
                out.println("</div>");
                out.println("<div class='form-group'>");
                out.println("<label for='edad"+s[0]+"'>Edad</label>");
                out.println("<input value='"+s[5]+"' name='edad' min='1' max='130' type='number' class='form-control' id='edad"+s[0]+"' placeholder='Ingresa tu edad'>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='ciclo"+s[0]+"'>Ciclo</label>");
                out.println("<input value='"+s[10]+"' name='ciclo' min= '1' max = '6' class='form-control' id='ciclo"+s[0]+"' placeholder='Ingresa tu ciclo escolar'>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='maestro'>Maestro</label>");
                out.println("<select name='idMaestroAlumnos' class='form-control' id='editarMaestroAlumno'>");
                out.println("<option value=0>------</option>");
                for(String maestro : maestros){
                    String m[] = maestro.split(" ");
                    out.println("<option value='"+m[0]+" "+m[1]+"_"+m[2]+"'>"+m[1]+" "+m[2].replace("_"," ")+"</option>");
                }
                out.println("</select>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='nombre'>Grupo</label>");
                out.println("<select class='form-control' id='alumnoEditarGrupo' name='grupo'>"); /*Mandar los grupos del profesor que se elija*/
                out.println("<option value=0>------</option>");
                out.println("</select>");
                out.println("</div>");
                out.println("</div>");
                out.println("<input name='id' value='"+s[0]+"' type='hidden'>");
                out.println("<div class='modal-footer'>");
                out.println("<button id='modalEditarA' type='submit' class='btn btn-warning'>editar</button>");
                out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");


                /*Modal de los alumnos para elimiar*/
                out.println("<div class='modal fade' id='deleteA"+s[0]+"' tabindex='-1' role='dialog' aria-labelledby='delete' aria-hidden='true'>");
                out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                out.println("<div class='modal-content'>");
                out.println("<div class='modal-header'>");
                out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Eliminar</h5>");
                out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                out.println("<span aria-hidden='true'>&times;</span>");
                out.println("</button>");
                out.println("</div>");
                out.println("<div class='modal-body'>");
                out.println("¿Esta seguro de eliminar a "+ s[1]+"?");
                out.println("</div>");
                out.println("<div class='modal-footer'>");
                out.println("<button id='eliminarA"+s[0]+"' type='submit' class='btn btn-danger'>Eliminar</button>");
                out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<script>");
                out.println("$('#eliminarA"+s[0]+"').click(function(){");
                out.println("$.ajax({");
                out.println("url:'ajaxAlumnos',");
                out.println("data:{id:'"+s[0]+"'},");
                out.println("type:'get',");
                out.println("cache:false,");
                out.println("success:(function(data){");
                out.println("swal('Eliminado correctamente','Da click en el boton','success').then((value) => {document.location.href ='http://localhost:8080/ReadingComprehension/adminDashboardServlet'});");
                out.println("})");
                out.println("})");
                out.println("})");
                out.println(" </script>");
            }
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</div>"); 

        
        /*------------ SECCION DE LOS GRUPOS*/
        out.println("<div class='tab-pane' id='grupos' role='tabpanel' aria-labelledby='grupos-tab'>");
        out.println("<div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom'>");
        out.println("<h1 class='h2'>Administración</h1>");
        out.println("</div>");
        out.println("<h2>Grupos</h2>");
        out.println("<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#agregarGrupo'>");
        out.println("<img src='package/build/svg/plus.svg'/>Agregar");
        out.println("</button>");
        out.println("<br /><br />");
        out.println("<div class='table-responsive'>");
        out.println("<table class='table table-striped table-sm'>");
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Nombre</th>");
        out.println("<th>Maestro</th>");
        out.println("<th>Alumnos</th>");
        out.println("<th>Opciones</th>");
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
         if("No tienes grupos registrados".equals(grupos.get(0))){
             out.println(grupos.get(0));
        }else{
            for(String g : grupos){
                String[] s = g.split(" ");
                out.println("<tr>");
                out.println("<td>"+s[3]+"</td>");
                out.println("<td>"+s[2]+"</td>");
                out.println("<td>"+s[4]+"</td>");
                out.println("<td>");
                out.println("<button type='button' class='btn btn-info' data-toggle='modal' data-target='#editarG"+s[0]+"'>");
                out.println("<img src='package/build/svg/pencil.svg'/>");
                out.println("</button>");
                out.println("<button type='button' class='btn btn-danger' data-toggle='modal' data-target='#deleteG"+s[0]+"'>");
                out.println("<img src='package/build/svg/x.svg'/>");
                out.println("</button>");
                out.println("</td>");
                out.println("</tr>");
                
                /*Modal de los grupos para editar*/
       
                out.println("<div class='modal fade' id='editarG"+s[0]+"' tabindex='-1' role='dialog' aria-labelledby='editar"+s[0]+"' aria-hidden='true'>");
                out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                out.println("<div class='modal-content'>");
                out.println("<div class='modal-header'>");
                out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Editar</h5>");
                out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                out.println("<span aria-hidden='true'>&times;</span>");
                out.println("</button>");
                out.println("</div>");
                out.println("<div class='modal-body'>");
                out.println("<div class='form-group'>");
                out.println("<form name='formEditar"+s[0]+"' action='ajaxGrupos' method='post'>");
                out.println("<label for='nombre"+s[0]+"'>Nombre</label>");
                out.println("<input value='"+s[3]+"' name='nombre' type='text' class='form-control' id='nombre"+s[0]+"' placeholder='Ingresa el nombre del grupo'>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='asignatura"+s[0]+"'>Asginatura</label>");
                out.println("<input value='"+s[5]+"' name='asignatura' type='text' class='form-control' id='asignatura"+s[5]+"' placeholder='Ingresa la asignatura'>");
                out.println("</div>");
                
                out.println("<div class='form-group'>");
                out.println("<label for='maestro'>Maestro</label>");
                out.println("<select name='editarMaestroGrupo' class='form-control'>");
                out.println("<option value=0>------</option>");
                for(String maestro : maestros){
                String m[] = maestro.split(" ");
                    out.println("<option value='"+m[0]+" "+m[1]+"_"+m[2]+"'>"+m[1]+" "+m[2].replace("_"," ")+"</option>");
                }
                out.println("</select>");
                out.println("</div>");
                out.println("<input name='id' value='"+s[0]+"' type='hidden'>");
                out.println("<input name='numero' value='"+s[4]+"' type='hidden'>");
                out.println("<input name='bandera' value='1' type='hidden'>");
                out.println("</div>");
                out.println("<div class='modal-footer'>");
                out.println("<button id='modalEditarG"+s[0]+"' type='submit' class='btn btn-warning'>editar</button>");
                out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                out.println("</div>");
                out.println("</form>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
       
                /*Modal de los grupos para elimiar*/
                out.println("<div class='modal fade' id='deleteG"+s[0]+"' tabindex='-1' role='dialog' aria-labelledby='delete' aria-hidden='true'>");
                out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
                out.println("<div class='modal-content'>");
                out.println("<div class='modal-header'>");
                out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Eliminar</h5>");
                out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
                out.println("<span aria-hidden='true'>&times;</span>");
                out.println("</button>");
                out.println("</div>");
                out.println("<div class='modal-body'>");
                out.println("<input name='bandera' value='2' type='hidden'>");
                out.println("¿Esta seguro de eliminar a "+s[3]+"?");
                out.println("</div>");
                out.println("<div class='modal-footer'>");
                out.println("<button id='eliminarG"+s[0]+"' type='submit' class='btn btn-danger'>Eliminar</button>");
                out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("</div>");
                out.println("<script>");
                out.println("$('#eliminarG"+s[0]+"').click(function(){");
                out.println("$.ajax({");
                out.println("url:'ajaxGrupos',");
                out.println("data:{id:'"+s[0]+"'},");
                out.println("type:'post',");
                out.println("cache:false,");
                out.println("success:(function(data){");
                out.println("swal('Eliminado correctamente','Da click en el boton','success').then((value) => {document.location.href ='http://localhost:8080/ReadingComprehension/adminDashboardServlet'});");
                out.println("})");
                out.println("})");
                out.println("})");
                out.println(" </script>");
                out.println("</tr>");
            }
                       
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</main>");
        out.println("</div>");
        out.println("</div>");
         
        /*seccion de modals para agregar maestros, alumnos y grupos*/
        out.println("<!--Seccion de modals -->");
        out.println("<!--Esto es para crear maestros-->");
        out.println("<div class='modal fade' id='agregarMaestro' tabindex='-1' role='dialog' aria-labelledby='agregarMaestro' aria-hidden='true'>");
        out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Agregar</h5>");
        out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
        out.println("<span aria-hidden='true'>&times;</span>");
        out.println("</button>");
        out.println("</div>");
        out.println("<form action='adminDashboardServlet' method='POST'>");
        out.println("<div class='modal-body'>");
        out.println("<div class='form-group'>");
        out.println("<label for='nombre'>Nombre</label>");
        out.println("<input required name='nombre' type='text' class='form-control' id='nombre' placeholder='Ingresa tu nombre(s)'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='apellidos'>Apellidos</label>");
        out.println("<input required name='apellidos' type='text' class='form-control' id='apellidos' placeholder='Ingresa tus apellidos'>");
        out.println("</div>");
        out.println("<div class='form-group ui-widget'>");
        out.println("<label for='usuario'>Usuario</label>");
        out.println("<input required name='usuario' type='text' class='form-control' id='usuario' placeholder='¿Cómo quieres que te llame?'>");
        out.println("</div>");
        out.println("<label for='edad'>Edad</label>");
        out.println("<div class='input-group mb-3'>");
        out.println("<div class='input-group-append'>");
        out.println("<span  for='edad' class='input-group-text '><img src='package/build/svg/pencil.svg'/></span>");
        out.println("</div>");
        out.println("<input required name='edad' min='1' max='130' type='number' class='form-control' id='edad' placeholder='Ingresa tu edad'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='especialidad'>Especialidad</label>");
        out.println("<input required name='especialidad' type='text' class='form-control' id='especialidad' placeholder='¿Cual es tu especialidad?'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='escuela'>Escuela</label>");
        out.println("<input required name='escuela' type='text' class='form-control' id='escuela' placeholder='Escuela donde trabajas'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label class='mr-sm-2' for='sexo'>Género</label>");
        out.println("<select name ='sexo' class='custom-select mb-2 mr-sm-2 mb-sm-0' id='sexo'>");
        out.println("<option selected>Elige uno</option>");
        out.println("<option value='1'>Masculino</option>");
        out.println("<option value='2'>Femenino</option>");
        out.println("</select>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='correo'>Correo</label>");
        out.println("<input name='correo' required  type='email' class='form-control' id='correo' aria-describedby='emailHelp' placeholder='Ingresa tu email'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='contra'>Contraseña</label>");
        out.println("<input name='contra' required  type='password' class='form-control' id='contra' placeholder='Contraseña'>");
        out.println("</div>");
        out.println("<div class='form-group row'>");
        out.println("<label for='cumple' class='col-3 col-form-label'>Cumpleaños</label>");
        out.println("<div class='col-9'>");
        out.println("<input name='cumple' required  class='form-control' type='date' id='cumple'>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='modal-footer'>");
        out.println("<button id='modalAgregar' type='submit' class='btn btn-success'>Agregar</button>");
        out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        
        out.println("<!--Esto es para crear alumnos-->");
        
        out.println("<div class='modal fade' id='agregarAlumno' tabindex='-1' role='dialog' aria-labelledby='agregarAlumno' aria-hidden='true'>");
        out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Agregar</h5>");
        out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
        out.println("<span aria-hidden='true'>&times;</span>");
        out.println("</button>");
        out.println("</div>");
        out.println("<form action='adminDashboardServlet' method='POST'>");
        out.println("<div class='modal-body'>");
        out.println("<div class='form-group'>");
        out.println("<label for='nombre'>Nombre</label>");
        out.println("<input required name='nombre' type='text' class='form-control' id='nombre' placeholder='Ingresa tu nombre(s)'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='apellidos'>Apellidos</label>");
        out.println("<input required name='apellidos' type='text' class='form-control' id='apellidos' placeholder='Ingresa tus apellidos'>");
        out.println("</div>");
        out.println("<div class='form-group ui-widget'>");
        out.println("<label for='usuario'>Usuario</label>");
        out.println("<input required name='usuario' type='text' class='form-control' id='usuario' placeholder='¿Cómo quieres que te llame?'>");
        out.println("</div>");
        out.println("<label for='edad'>Edad</label>");
        out.println("<div class='input-group mb-3'>");
        out.println("<div class='input-group-append'>");
        out.println("<span  for='edad' class='input-group-text '><img src='package/build/svg/pencil.svg'/></span>");
        out.println("</div>");
        out.println("<input required name='edad' min='1' max='130' type='number' class='form-control' id='edad' placeholder='Ingresa tu edad'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='maestro'>Maestro</label>");
        out.println("<select name='idMaestroAlumnos' class='form-control' id='maestroSelectAlumnos'>");
        out.println("<option value=0>---</option>");
        for(String maestro : maestros){
            String s[] = maestro.split(" ");
            out.println("<option value='"+s[0]+" "+s[1]+"_"+s[2]+"'>"+s[1]+" "+s[2].replace("_"," ")+"</option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='nombre'>Grupo</label>");
        out.println("<select class='form-control' id='alumnoSelectGrupos' name='grupo'>"); /*Mandar los grupos del profesor que se elija*/
        out.println("<option value=0>------</option>");
        
        out.println("</select>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label class='mr-sm-2' for='sexo'>Género</label>");
        out.println("<select name ='sexo' class='custom-select mb-2 mr-sm-2 mb-sm-0' id='sexo'>");
        out.println("<option selected>Elige uno</option>");
        out.println("<option value='1'>Masculino</option>");
        out.println("<option value='2'>Femenino</option>");
        out.println("</select>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='contra'>Contraseña</label>");
        out.println("<input name='contra' required  type='password' class='form-control' id='contra' placeholder='Contraseña'>");
        out.println("</div>");
        out.println("<div class='form-group row'>");
        out.println("<label for='cumple' class='col-3 col-form-label'>Cumpleaños</label>");
        out.println("<div class='col-9'>");
        out.println("<input name='cumple' required  class='form-control' type='date' id='cumple'>");
        out.println("</div>");
        out.println("</div>");
        out.println("<label for='edad'>Año escolar</label>");
        out.println("<div class='input-group mb-3'>");
        out.println("<div class='input-group-append'>");
        out.println("<span  for='ciclo' class='input-group-text '><img src='package/build/svg/pencil.svg'/></span>");
        out.println("</div>");
        out.println("<input required name='ciclo' min='1' max='6' type='number' class='form-control' id='ciclo' placeholder='Ingresa tu año escolar'>");
        out.println("</div>");  
        out.println("</div>");
        out.println("<div class='modal-footer'>");
        out.println("<button id='modalAgregar' type='submit' class='btn btn-success'>Agregar</button>");
        out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
       
        out.println("<!--Esto es para crear grupos-->");
        
        out.println("<div class='modal fade' id='agregarGrupo' tabindex='-1' role='dialog' aria-labelledby='agregarMaestro' aria-hidden='true'>");
        out.println("<div class='modal-dialog modal-dialog-centered' role='document'>");
        out.println("<div class='modal-content'>");
        out.println("<div class='modal-header'>");
        out.println("<h5 class='modal-title' id='exampleModalLongTitle'>Agregar</h5>");
        out.println("<button type='button' class='close' data-dismiss='modal' aria-label='Close'>");
        out.println("<span aria-hidden='true'>&times;</span>");
        out.println("</button>");
        out.println("</div>");
        out.println("<form action='adminDashboardServlet' method='POST'>");
        out.println("<div class='modal-body'>");
        out.println("<div class='form-group'>");
        out.println("<label for='nombre'>Nombre</label>");
        out.println("<input required name='nombre' type='text' class='form-control' id='nombre' placeholder='Ingresa tu nombre(s)'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='asignatura'>Asignatura</label>");
        out.println("<input required name='asignatura' type='text' class='form-control' id='asignatura' placeholder='Ingresa la asignatura'>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='nombre'>Maestro</label>");
        out.println("<select class='form-control' id='maestrosSelect' name= 'idMaestro'>");
        out.println("<option value=0>------</option>");
        for(String maestro : maestros){
            String s[] = maestro.split(" ");
            out.println("<option value='"+s[0]+" "+s[1]+"_"+s[2]+"'>"+s[1]+" "+s[2].replace("_"," ")+"</option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='modal-footer'>");
        out.println("<button id='modalAgregar' type='submit' class='btn btn-success'>Agregar</button>");
        out.println("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Cancelar</button>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        //out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js' integrity='sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo' crossorigin='anonymous'></script>");
        out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>");
        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js'></script>");
        out.println("<script src='https://unpkg.com/feather-icons/dist/feather.min.js'></script>");
        out.println("<script src='https://code.jquery.com/ui/1.12.1/jquery-ui.js'></script>");
        out.println("<script src='https://unpkg.com/sweetalert/dist/sweetalert.min.js'></script>");

        out.println("<script>");
        out.println("feather.replace()");
        out.println("</script>");
        out.println("<script>");
        out.println("$(function () {");
        out.println("$('#myTab li:first-child a').tab('show')");
        out.println("})");
        out.println("</script>");
        
        /*Esta función es para obtener los grupos de un profesor*/
        out.println("<script>");
        out.println("$(document).ready(function () {");
        out.println("$('#maestroSelectAlumnos').change(function () {");
        out.println("var val = $(this).val();");
        out.println("if(val != 0){");
        out.println("$.ajax({");
        out.println("url:'ajaxGrupos',");
        out.println("data:{id:val},");
        out.println("type:'get',");
        out.println("cache:false,");
        out.println("success:(function(data){");
        out.println("if(data.length > 2){");
        out.println("var js = JSON.parse(data);");
        out.println("$('#alumnoSelectGrupos').empty();");
        out.println("$('#alumnoSelectGrupos').append('<option value=0>------</option>');");
        out.println("for(var x in js){");
        out.println("$('#alumnoSelectGrupos').append('<option value='+Object.values(js[x])[1]+'>'+Object.values(js[x])[0]+'</option>');");
        out.println("}");
        out.println("}else{");
        out.println("$('#alumnoSelectGrupos').append('<option>Sin grupos</option>')");
        out.println("}");
        out.println("})");
        out.println("})");
        out.println("}");
        out.println("});");
        out.println("});");
        out.println("</script>");
 
        
        out.println("<script>");
        out.println("$(document).ready(function () {");
        out.println("$('#editarMaestroAlumno').change(function () {");
        out.println("var val = $(this).val();");
        out.println("if(val != 0){");
        out.println("$.ajax({");
        out.println("url:'ajaxGrupos',");
        out.println("data:{id:val},");
        out.println("type:'get',");
        out.println("cache:false,");
        out.println("success:(function(data){");
        out.println("if(data != null){");
        out.println("var js = JSON.parse(data);");
        out.println("$('#alumnoEditarGrupo').empty();");
        out.println("$('#alumnoEditarGrupo').append('<option value=0>------</option>');");
        out.println("for(var x in js){");
        out.println("$('#alumnoEditarGrupo').append('<option value='+Object.values(js[x])[1]+'>'+Object.values(js[x])[0]+'</option>');");
        out.println("}");
        out.println("}else{");
        out.println("$('#alumnoEditarGrupo').append('<option>Sin grupos</option>')");
        out.println("}");
        out.println("})");
        out.println("})");
        out.println("}");
        out.println("});");
        out.println("});");
        out.println("</script>");
        
        
        
        out.println("<script>");
        out.println("$('#usuario').on('click touchend',function(e){");
        out.println("$.ajax({");
        out.println("url:'autocompleteAjax',");
        out.println("type:'get',");
        out.println("cache:false,");
        out.println("success:(function(data){");
        out.println("");
        out.println("})");
        out.println("})");
        out.println("});");
        out.println("</script>");
        
        out.println("</body>");
        out.println("</html>");
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Persona;
import consultas.obtenerXML;
import java.io.IOException;
import java.io.PrintWriter;
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

public class loginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            Persona user = new Persona();
            ServletContext context = request.getServletContext();
            String path = context.getRealPath("/baseDeDatos/xml");
            List<Persona> personas = null;
            int bandera = 0;
            PrintWriter out = response.getWriter();
            String usuario = request.getParameter("usuario");
            String pass = request.getParameter("pass");
            try {
                personas = obtenerXML.obtenerListaUsuarios(path);
            } catch (JAXBException ex) {
                Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0 ; i<personas.size();i++){
                if(personas.get(i).getNombre_de_usuario().equals(usuario)){
                  bandera = 1;
                    if (personas.get(i).getContra().equals(pass)){
                       bandera = 2;
                       user = personas.get(i);
                    }
                }
            }
            switch (bandera) {
                case 0:
                    {
                        String error = "El usuario no Existe";
                        pintar(error,out,response);
                        break;
                    }
                case 1:
                    {
                        String error = "La contraseña esta mal";
                        pintar(error,out,response);
                        break;
                    }
                default:
                    HttpSession session = request.getSession(); //Instanciamos un objeto tipo httpSession.
                    session.setAttribute("usuario",user); //guarda un valor clave - objeto, son las coockies
                    response.sendRedirect("dashboardServlet");
                    break;
            }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
             //ServletContext path = request.getServletContext();
            PrintWriter out = response.getWriter(); 
            String error = "";
            HttpSession session = request.getSession();
            session.removeAttribute("usuario");
            pintar(error,out,response);
        }
    
    public void pintar(String error, PrintWriter out, HttpServletResponse response){
            response.setContentType("text/html;charset=UTF-8"); //Responde al cliente. metodo mime
            out.println("<html dir='ltr' lang='en'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Iniciar Sesion</title>");
            out.println("<link rel='icon' type='image/png' href='img/fondo.png'/>");
            out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css'>");
            out.println("<link rel='stylesheet' href='css/estilo.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<main role='main' class='container'>");
            out.println("<div class='container align-items-center content'>");
            out.println("<div class='row'>");
            out.println("<div class='col-sm'>");
            out.println("</div>");
            out.println("<div class='col-sm top-buffer'>");
            out.println("<div class='jumbotron color-login'>");
            if(error != ""){
                out.println("<div class='alert alert-danger' role='alert'>");
                out.println("<strong>UPS!</strong> <a href='#' class='alert-link'></a>");
                out.println(error);
                out.println("</div>");
            }
            out.println("<h1>Inicia Sesión. </h1>");
            out.println("<form method='POST' action='loginServlet'>");
            out.println("<p class='lead'>");
            out.println("<div class='form-group'>");
            out.println("<label for='exampleInputEmail1'>Nombre de Usuario:</label>");
            out.println("<input name='usuario' type='text' class='form-control'  placeholder='Ingresa tu nombre de usuario'>");
            out.println("</div>");
            out.println("<div class='form-group'>");
            out.println("<label for='exampleInputPassword1'>Contraseña:</label>");
            out.println("<input name='pass' type='password' class='form-control' id='exampleInputPassword1' placeholder='Password'>");
            out.println("<small id='emailHelp' class='form-text text-muted'>No muestres a nadie tu contraseÃ±a.</small>");
            out.println("</div>");
            out.println("</p>");
            out.println("<button type='submit' class='btn btn-primary'>Submit</button>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div class='col-sm'></div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</main>");
            out.println("</body>"); 
            out.println("<script src='https://code.jquery.com/jquery-3.3.1.slim.min.js'></script>");
            out.println("<script src='https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js'></script>");
            out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js'></script>");
            out.println("</html>");     
    }
}

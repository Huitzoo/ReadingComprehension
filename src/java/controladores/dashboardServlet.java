package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Persona;
import beans.Usuario;

public class dashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Persona usuario = (Persona)session.getAttribute("usuario");
        int rol = usuario.getRol();
        if(rol == 1){
            //obtener lo de admin, es obtener todo jaja.
            session.setAttribute("usuario",usuario);
            response.sendRedirect("adminDashboardServlet");
        }else if(rol == 2){
            //obtener lo de maestro que son alumnos y actividades
            session.setAttribute("usuario",usuario);
            response.sendRedirect("maestroDashboardServlet");

        }else{
            //obtener lo de alumno que son sus actividades
        }
    }
}

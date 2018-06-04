/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

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
import javax.xml.bind.JAXBException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class autocompleteAjax extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml/usados.xml");
        List<String> usuario = null;
        try {
            usuario = obtenerXML.obtenerUsernames(path);
        } catch (JAXBException ex) {
            Logger.getLogger(autocompleteAjax.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONArray obj = new JSONArray();
        
        for(int i= 0;i<usuario.size();i++){
            obj.put(usuario.get(i));   
        }
        System.out.println(obj);
        out.write(obj.toString());
    }
}

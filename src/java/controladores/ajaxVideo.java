/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Persona;
import consultas.obtenerXML;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

public class ajaxVideo extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        ServletContext context = request.getServletContext();

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            ServletContext context = request.getServletContext();
            PrintWriter salida = response.getWriter();
            HttpSession session = request.getSession();
            Persona usuario = (Persona)session.getAttribute("usuario");
            System.out.println(session.getAttribute("usuario"));
            session.setAttribute("usuario",usuario);
            String path1 = context.getRealPath("baseDedatos/xml");
            //String path = context.getRealPath("/").replace("\\build\\web\\","");
            String path = context.getRealPath("/");
            //String img = "/media/img/"+usuario.getId();
            //String text = "/media/textos/"+usuario.getId();
            String video = "/media/videos/"+usuario.getId();

            //String img = "/web/media/img/"+usuario.getId();
            //String video = "/web/media/videos/"+usuario.getId();
            
            Boolean bandera = false;
            try {
                List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        // Process form file field (input type="file").
                        String fieldName = item.getFieldName();
                        String fileName = FilenameUtils.getName(item.getName());
                        path = path + video;
                        File file = new File(path);
                        if (!file.exists()) {
                            if (file.mkdir()) {
                                System.out.println("Directory is created!");
                            }
                        }
                        InputStream fileContent = item.getInputStream();
                        path = path+"/"+fileName;
                        OutputStream  actividad = new FileOutputStream(new File(path));
                        int read = 0;
                        byte[] bytes = new byte[1024];
                        while ((read = fileContent.read(bytes)) != -1) {
                                actividad.write(bytes, 0, read);
                        }
                        
                        int id = obtenerXML.ultimoIdVideos(path1);
                        String location = "media/videos/"+usuario.getId()+"/"+fileName;
                        cruds.crudVideos.crearVideo(path1,location,usuario.getId(),id, 2);
                        salida.write(1);
                        response.sendRedirect("maestroActividades2Servlet");
                    }
                }
            }catch (FileUploadException e) {
                throw new ServletException("Cannot parse multipart request.", e);
            } catch (JAXBException ex) {
            Logger.getLogger(ajaxVideo.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
}

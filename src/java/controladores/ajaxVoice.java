/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Persona;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.GetVoiceOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voices;
import com.ibm.watson.developer_cloud.text_to_speech.v1.util.WaveUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author huitz
 */
public class ajaxVoice extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter outp = response.getWriter();
            HttpSession session = request.getSession();
            
            String path = (String)session.getAttribute("path");
            Persona usuario = (Persona)session.getAttribute("usuario");
            String nombre = (String)session.getAttribute("nombre");
            
            TextToSpeech service = new TextToSpeech();
            service.setUsernameAndPassword("db918bac-50af-46da-a518-99074312baf0","ksoxidNpCZWm");
            ServletContext context = request.getServletContext();
            String pathA = context.getRealPath("/");            
            pathA = pathA.replace("\\build\\web\\","")+"/web/media/audios/"+usuario.getId();
            //pathA = pathA+"/media/audios/"+usuario.getId();
            
            File file = new File(pathA);
            if (!file.exists()) {
                if (file.mkdir()) {
                    System.out.println("Directory is created!");
                }
            }
            Charset charsetD = Charset.forName("UTF-8");
            String texto = readFile(path,charsetD);
            try {
            SynthesizeOptions synthesizeOptions = new SynthesizeOptions.Builder()
              .text(texto)
              .accept("audio/wav")
              .voice("es-LA_SofiaVoice")
              .build();
            InputStream stream = service.synthesize(synthesizeOptions).execute();
            InputStream in = WaveUtils.reWriteWaveHeader(stream);
            OutputStream out = new FileOutputStream(pathA+"/"+nombre+".wav");
           
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
              out.write(buffer, 0, length);
            }
                out.close();
                in.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            session.setAttribute("usuario", usuario);
            session.setAttribute("audio","media/audios/"+usuario.getId()+"/"+nombre+".wav");
            outp.write("media/audios/"+usuario.getId()+"/"+nombre+".wav");
            
    }
    static String readFile(String path, Charset encoding)throws IOException {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
    }    
}

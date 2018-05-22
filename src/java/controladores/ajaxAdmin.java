/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import beans.Alumno;
import beans.Maestro;
import beans.Persona;
import funciones.obtenerXML;
import funciones.crudMaestro;
import funciones.crudAlumno;
import funciones.crudPersonas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 *
 * @author huitz
 */
public class ajaxAdmin extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/baseDeDatos/xml"); 
        List<Persona> personas = null;
        List<Maestro> maestros = null;
        List<Alumno> alumnos = null;
        try{
               personas = obtenerXML.obtenerListaUsuarios(path);
               maestros = obtenerXML.obtenerListaMaestros(path);
               alumnos = obtenerXML.obtenerListaAlumnos(path);
        }catch (JAXBException ex) {
               Logger.getLogger(loginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Persona> personas1 = personas.stream().filter(e -> e.getId()!=id ).collect(Collectors.toList());
        List<Persona> salidaPersonas = new ArrayList();
        List<Maestro> salidaMaestros = maestros.stream().filter(e -> e.getId()!=id).collect(Collectors.toList());
        List<Alumno> quitarAlumnos = alumnos.stream().filter(e -> e.getIdMaestro()==id).collect(Collectors.toList());
        System.out.println("holis");
        System.out.println(quitarAlumnos);
        List<Alumno> salidaAlumnos = alumnos.stream().filter(e -> e.getIdMaestro() != id).collect(Collectors.toList());
        for (Alumno alumno : quitarAlumnos){
            for(Persona persona : personas1){
                if(persona.getId()!=alumno.getId()){
                    salidaPersonas.add(persona);
                }
            }
        }
        try {
            crudAlumno.eliminarAlumno(path,salidaAlumnos);
            crudMaestro.eliminarMaestro(path,salidaMaestros);
            crudPersonas.eliminarPersona(path,salidaPersonas);
        } catch (JAXBException ex) {
            Logger.getLogger(ajaxAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

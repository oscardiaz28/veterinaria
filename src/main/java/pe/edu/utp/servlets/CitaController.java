package pe.edu.utp.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.business.RegistroCita;
import pe.edu.utp.entities.CitaDto;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.utils.UTPBinary;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/api/crear_cita")
public class CitaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            sb.append(line);
        }
        String json = sb.toString();
        ObjectMapper objectMapper = new ObjectMapper();

        CitaDto cita = null;
        try {
            cita = objectMapper.readValue(json, CitaDto.class);
            System.out.println("Fecha: " + cita.getFecha());
            System.out.println("Hora: " + cita.getHora());
            System.out.println("Servicios: " + cita.getServicios());
            System.out.println("Mascotas: " + cita.getMascotas());
            System.out.println("Mensaje: " + cita.getMensaje());
            System.out.println("ID Cliente: " + cita.getId_cliente());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean emptyFields = cita.hasEmptyFields();

        if( emptyFields == true ){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            System.out.println( cita.toString() );
            resp.getWriter().write("{\"message\":\" Todos los campos son obligatorios \"}");
            return;
        }

        RegistroCita registroCita = new RegistroCita();

        Map<String, String> result = null;
        try {
            result = registroCita.crearCita(cita);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(result);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write( jsonResult );

        //Read json
        /*
        StringBuilder json = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while( (line = reader.readLine() ) != null ){
            json.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        CitaDto dto = mapper.readValue(json.toString(), CitaDto.class);

        Boolean emptyFields = dto.hasEmptyFields();

        if( emptyFields == true ){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"message\":\" Todos los campos son obligatorios \"}");
            return;
        }

        RegistroCita registroCita = new RegistroCita();

        Map<String, String> result = null;
        try {
            result = registroCita.crearCita(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String jsonResult = mapper.writeValueAsString(result);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write( jsonResult );
         */

    }


    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }


}

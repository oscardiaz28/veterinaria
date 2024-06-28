package pe.edu.utp.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.business.RegistroCita;
import pe.edu.utp.entities.CitaDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/crear_cita")
public class CitaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Read json
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

    }
}

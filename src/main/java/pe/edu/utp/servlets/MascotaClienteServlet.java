package pe.edu.utp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.service.MascotaService;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("getMascotas")
public class MascotaClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String cliente_dni = req.getParameter("cliente_dni");
            List<Mascota> mascotas = App.RegMascotas.getMascotasByDni(cliente_dni);
            //mascotas = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(mascotas);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(json);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

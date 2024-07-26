package pe.edu.utp.servlets;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;

@WebServlet (urlPatterns = "/listar_mascota")

public class ListarMascotaServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.getWriter().println(App.RegMascotas.getHtmlListarMascota());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
}

package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;

import java.io.IOException;
import java.sql.SQLException;

public class ReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String html = App.RegVentas.getTotal();
            resp.getWriter().println(html);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

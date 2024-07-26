package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/detalle_trabajador")
public class DetalleTrabajadorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String dni_trabajador = req.getParameter("dni_trabajador");
            if (dni_trabajador == null || dni_trabajador.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se encontró el trabajador");
                return;
            }
            String action = req.getParameter("action");
            if ("updateEstado".equals(action)) {
                actualizarEstadoProyecto(dni_trabajador);
                resp.sendRedirect(req.getContextPath() + "/detalle_trabajador?dni_trabajador=" + dni_trabajador);
                return;
            }
            resp.getWriter().println(App.RegTrabajador.getHtmlDetalleTrabajador(dni_trabajador));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void actualizarEstadoProyecto(String dni_trabajador) throws SQLException, IOException {
        try {
            App.RegTrabajador.actualizarEstadoColaborador(dni_trabajador);
            System.out.println("Estado del proyecto actualizado correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del proyecto.", e);
        }
    }
}

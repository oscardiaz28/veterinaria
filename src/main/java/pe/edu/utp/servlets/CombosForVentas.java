package pe.edu.utp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add_ventas")
public class CombosForVentas extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        try {
            //combo de trabajadores
            String combo = App.RegTrabajador.getComboCliente_Trabajadores();

            //combo de clientes
            resp.getWriter().println(combo);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

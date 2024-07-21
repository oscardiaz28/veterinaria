package pe.edu.utp.servlets;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Detalle;
import pe.edu.utp.model.Venta;
import pe.edu.utp.util.AppConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/register_venta")
public class VentaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String cliente_dni = req.getParameter("ComboCliente");
        String trabajador_dni = req.getParameter("ComboTrabajador");
        String fecha = req.getParameter("txtFecha");
        String metodo_pago = req.getParameter("ComboMetodo");
        String estado = req.getParameter("txtestado");

        try {
            Validator.validateNotEmpty(fecha, "fecha");

            // Registrar la venta
            Venta venta = new Venta(cliente_dni, trabajador_dni, fecha, metodo_pago);
            int codigo_venta = App.RegVentas.registrarVenta(venta);

            if (codigo_venta == 0) {
                throw new SQLException("Error al registrar la venta, ID no generado.");
            }

            List<Detalle> detalles = new ArrayList<>();
            String[] productosIds = req.getParameterValues("productos[0][id]");

            if (productosIds == null || productosIds.length == 0) {
                throw new IllegalArgumentException("No se recibieron productos.");
            }

            for (int i = 0; i < productosIds.length; i++) {
                String codigo_producto = req.getParameter("productos[" + i + "][id]");
                String cantidad = req.getParameter("productos[" + i + "][cantidad]");
                String precio_unitario = req.getParameter("productos[" + i + "][precio]");
                String subtotal = req.getParameter("productos[" + i + "][subtotal]");

                if (codigo_producto == null || cantidad == null || precio_unitario == null || subtotal == null) {
                    throw new IllegalArgumentException("Uno o más detalles de productos están vacíos.");
                }

                Integer codigo_productoInt = Integer.parseInt(codigo_producto);
                Integer cantidadInt = Integer.parseInt(cantidad);
                Double precio = Double.parseDouble(precio_unitario);
                Double subtotalDouble = Double.parseDouble(subtotal);

                Detalle detalle = new Detalle(codigo_venta, codigo_productoInt, cantidadInt, precio, subtotalDouble, estado);
                detalles.add(detalle);
            }

            // Registrar todos los detalles
            for (Detalle detalle : detalles) {
                App.RegDetalle.registrarDetalle(detalle);
            }

            resp.sendRedirect("/listar_detalle");

        } catch (IllegalArgumentException | SQLException e) {
            String errorPagePath = AppConfig.getErrorTemplate();
            String html_error = new String(Files.readAllBytes(Paths.get(errorPagePath)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }
}

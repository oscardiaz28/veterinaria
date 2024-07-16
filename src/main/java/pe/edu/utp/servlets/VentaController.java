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

@WebServlet(urlPatterns = "/register_venta")
public class VentaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Captura de datos Ventas
        String cliente_dni = req.getParameter("ComboCliente");
        String trabajador_dni = req.getParameter("ComboTrabajador");
        String fecha = req.getParameter("txtFecha");
        String metodo_pago = req.getParameter("ComboMetodo");

        //Captura de datos DetalleVenta
        String codigo_producto = req.getParameter("CodigoProducto");
        String cantidad = req.getParameter("Cantidad");
        String precio_unitario = req.getParameter("txtprecio_unitario");
        String subtotal = req.getParameter("txtsubototal");
        String estado = req.getParameter("txtestado");

        try {
            // Validaciones
            Validator.validateNotEmpty(fecha, "fecha");
            Validator.validateNotEmpty(estado, "estado");

            Integer codigo_productoStr = Integer.parseInt(codigo_producto);
            Integer cantidadStr = Integer.parseInt(cantidad);
            Double precioStr = Double.parseDouble(precio_unitario);
            Double subtotalStr = Double.parseDouble(subtotal);

            // Crear el objeto Venta y registrar la venta
            Venta venta = new Venta(cliente_dni, trabajador_dni,fecha,metodo_pago);
            App.RegVentas.registrarVenta(venta);

            // Obtener el ID del usuario generado
            int codigo_venta = venta.getCodigo_venta();

            if (codigo_venta == 0) {
                throw new SQLException("Error al registrar la venta, ID no generado.");
            }

            Detalle detalle = new Detalle(codigo_venta, codigo_productoStr,cantidadStr,precioStr,subtotalStr,estado);
            App.RegDetalle.registrarDetalle(detalle);

            resp.sendRedirect("/listar_detalle");

        } catch (IllegalArgumentException|SQLException e) {
            // Leer el HTML de error y reemplazar el marcador de posición con el mensaje de error
            String errorPagePath = AppConfig.getErrorTemplate();
            String html_error = new String(Files.readAllBytes(Paths.get(errorPagePath)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }
}

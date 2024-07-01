package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.utils.UTPBinary;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@WebServlet(urlPatterns = "/register_cliente")
public class ClienteController extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Captura de datos
        String dni_cliente = req.getParameter("txtdni");
        String usuario_id = req.getParameter("txtusuarioID");
        String nombre = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String direccion = req.getParameter("txtdireccion");
        String celular = req.getParameter("txtcelular");


        try {
            // Validaciones
            Validator.validateNotEmpty(usuario_id, "ID Usuario");
            Validator.validateNotEmpty(nombre, "Nombre del Cliente");
            Validator.validateNotEmpty(apellidos, "Apellidos");
            Validator.validateNotEmpty(direccion, "Direccion");
            Validator.validateNotEmpty(celular, "Celular");
            Validator.validateNotEmpty(dni_cliente, "Dni del Cliente");

            Integer usuarioID = Integer.parseInt(usuario_id);

            // Crear el objeto Producto y registrar el producto
            Cliente cliente = new Cliente(dni_cliente,usuarioID,nombre,apellidos,direccion,celular);
            App.RegCliente.registrarCliente(cliente);

            resp.sendRedirect("/listar_cliente");

        } catch (IllegalArgumentException e) {
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

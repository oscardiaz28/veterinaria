package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.business.RegistroCliente;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.service.UsuarioService;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;

@WebServlet("/login")
public class RegistrarClienteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("dni_cliente");
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String direccion = req.getParameter("direccion");
        String telefono = req.getParameter("telefono");
        String correo = req.getParameter("email");
        String password = req.getParameter("password");

        try{
            Validator.validateNotEmpty(dni, "DNI");
            Validator.validateNumberFormat(dni, "DNI");
            Validator.validateNotEmpty(nombre, "nombre");
            Validator.validateStringFormat(nombre, "nombre");
            Validator.validateNotEmpty(apellido, "apellido");
            Validator.validateStringFormat(apellido, "apellido");
            Validator.validateNotEmpty(direccion, "direccion");
            Validator.validateNotEmpty(telefono, "telefono");
            Validator.validateNumberFormat(telefono, "telefono");
            Validator.validateNotEmpty(correo, "correo");
            Validator.validateEmail(correo, "correo");
            Validator.validateNotEmpty(password, "password");

            Usuario usuario = new Usuario();
            usuario.setEmail(correo);

            String hashPassword = UsuarioService.md5(password);

            usuario.setContra(hashPassword);
            usuario.setToken("0");
            usuario.setEstado("activo");
            usuario.setRol("CLIENTE");

            Cliente cliente = new Cliente();
            cliente.setDni_cliente(dni);
            cliente.setNombre(nombre);
            cliente.setApellidos(apellido);
            cliente.setDireccion(direccion);
            cliente.setCelular(telefono);
            RegistroCliente registroCliente = new RegistroCliente();

            registroCliente.addCliente( cliente, usuario );

            resp.sendRedirect("/login.html");

        }catch(Exception e){
            String filename_error = "src\\main\\resources\\templates\\error.html";
            String html_error = TextUTP.read(filename_error);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }

    }

}

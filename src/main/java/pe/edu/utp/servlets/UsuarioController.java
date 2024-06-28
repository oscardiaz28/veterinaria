package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;


@WebServlet(urlPatterns = "/register_usuario")
public class UsuarioController extends HttpServlet  {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Capturar los datos del USUARIO
        String email = req.getParameter("txtEMAIL");
        String pass = req.getParameter("txtPASS");
        String estado = req.getParameter("txtESTADO");

        // Crear objeto avance
        try {
            // Validaciones
            Validator.validateNotEmpty(email , "Correo");
            Validator.validateNotEmpty(pass, "Contraseña");
            Validator.validateNotEmpty(estado, "Estado");

            Usuario usuarios = new Usuario(email, pass, estado);

            App.RegUsuario.registrarUsuario(usuarios);

            resp.sendRedirect("/listar_usuario");


        } catch (IllegalArgumentException e) {
            String filename_error = "src\\main\\resources\\templates\\error.html";
            String html_error = TextUTP.read(filename_error);
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }


    }


}

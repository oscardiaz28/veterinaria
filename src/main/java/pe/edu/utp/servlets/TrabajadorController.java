package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.App;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.util.AppConfig;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@WebServlet("/register_trabajador")
public class TrabajadorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Captura de datos
        String dniTrabajador = req.getParameter("txtdni");
        String usuario_id = req.getParameter("txtid_user");
        String nombre = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String cargo = req.getParameter("txtcargo");
        String salario = req.getParameter("txtsalario");
        String direccion = req.getParameter("txtdireccion");
        String celular = req.getParameter("txtcelular");
        String fecha_contrato = LocalDate.now().toString();
        String estado = req.getParameter("txtestado");

        try {
            // Validaciones
            Validator.validateNotEmpty(dniTrabajador, "Dni Trabajador");
            Validator.validateNotEmpty(nombre, "Nombre");
            Validator.validateNotEmpty(apellidos, "Apellidos");
            Validator.validateNotEmpty(cargo, "Cargo");
            Validator.validateNotEmpty(direccion, "Direccion");
            Validator.validateNotEmpty(salario, "Salario");
            Validator.validateNotEmpty(estado, "Estado");
            Validator.validateNotEmpty(celular, "Celular");
            Validator.validateNotEmpty(usuario_id, "Usuario");

            //Conversion
            Double salarioStr = Double.parseDouble(salario);
            Integer usuario_idStr = Integer.parseInt(usuario_id);

            // Crear el objeto Trabajador y registrar el trabajador
            Trabajador trabajador = new Trabajador(dniTrabajador,usuario_idStr,nombre,apellidos,cargo,salarioStr,direccion,celular,fecha_contrato, estado);
            App.RegTrabajador.registrarTrabajador(trabajador);

            resp.sendRedirect("/listar_trabajador");

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

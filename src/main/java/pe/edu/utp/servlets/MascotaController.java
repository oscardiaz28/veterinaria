package pe.edu.utp.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.utils.UTPBinary;

@WebServlet("/register_mascotas")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)
public class MascotaController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //UTF-8
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // Captura de datos mascota
        String dni_cliente = req.getParameter("txtdni_cliente");
        String nombre = req.getParameter("txtnombre_mascota");
        String especie = req.getParameter("txtespecie");
        String raza = req.getParameter("txtraza");
        String edad = req.getParameter("txtedad");
        String genero = req.getParameter("txtgenero");
        String destino = AppConfig.getImgDir();

        // Captura de datos cliente
        String usuario_id = req.getParameter("txtusuarioID");
        String nombreCliente = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String direccion = req.getParameter("txtdireccion");
        String celular = req.getParameter("txtcelular");

        // Captura de datos
        String email = req.getParameter("txtusername");
        String password = req.getParameter("txtPASS");
        String estadoUsuario = "Activo";

        try {

            
            // Obtener la imagen y guardarla en la carpeta upload
            Part filePart = req.getPart("txtFoto");

            if (filePart == null) {
                throw new IllegalArgumentException("El campo foto no puede estar vacío");
            }

            String foto = getFileName(filePart);

            if (foto.isEmpty()) {
                throw new IllegalArgumentException("El campo foto no puede estar vacío");
            }

            String fileFoto = destino + foto;
            byte[] data = filePart.getInputStream().readAllBytes();
            UTPBinary.echobin(data, fileFoto);

            // Registro Usuario
            Usuario usuario = new Usuario(email, password, estadoUsuario);
            App.RegUsuario.registrarUsuario(usuario);

            // Obtener el ID del usuario generado
            int idUsuario = usuario.getId();

            if (idUsuario == 0) {
                throw new SQLException("Error al registrar el usuario, ID no generado.");
            }

            // Registro Cliente
            Cliente cliente = new Cliente(dni_cliente,idUsuario,nombre,apellidos,direccion,celular);
            App.RegCliente.registrarCliente(cliente);

            // Registrar Mascota
            Mascota mascota = new Mascota(dni_cliente, nombre, especie, raza, edad, genero, foto);
            App.RegMascotas.registrarMascota(mascota);

            resp.sendRedirect("/listar_mascotas");

        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            String errorPagePath = AppConfig.getErrorTemplate();
            String html_error = new String(Files.readAllBytes(Paths.get(errorPagePath)), StandardCharsets.UTF_8);
            html_error = html_error.replace("${error}", e.getMessage());

            resp.setContentType("text/html");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(html_error);
        }
    }

    // Método para obtener el nombre del archivo
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
    
}

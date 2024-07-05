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
        maxFileSize = 1024 * 1024 * 2,
        maxRequestSize = 1024 * 1024 * 2 * 5
)
public class MascotaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String dni_cliente = req.getParameter("txtdni_cliente");
        String nombre = req.getParameter("txtnombre_mascota");
        String especie = req.getParameter("txtespecie");
        String raza = req.getParameter("txtraza");
        String edad = req.getParameter("txtedad");
        String genero = req.getParameter("txtgenero");
        String destino = AppConfig.getImgDir();

       
        String nombreCliente = req.getParameter("txtnombre");
        String apellidos = req.getParameter("txtapellidos");
        String direccion = req.getParameter("txtdireccion");
        String celular = req.getParameter("txtcelular");

        String email = req.getParameter("txtusername");
        String password = req.getParameter("txtPASS");
        String estadoUsuario = "Activo";

        try {
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


            Mascota mascota = new Mascota(nombre, especie, raza, edad, genero, foto);
            App.RegMascotas.registrarMascota(mascota);

            int idMascota = mascota.getCodigo();

            if (idMascota == 0) {
                throw new SQLException("Error al registrar la mascota, ID no generado.");
            }

            Usuario usuario = new Usuario(email, password, estadoUsuario);
            App.RegUsuario.registrarUsuario(usuario);

            int idUsuario = usuario.getId();

            if (idUsuario == 0) {
                throw new SQLException("Error al registrar el usuario, ID no generado.");
            }

            Cliente cliente = new Cliente(dni_cliente, idMascota, idUsuario, nombreCliente, apellidos, direccion, celular);
            App.RegCliente.registrarCliente(cliente);

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

    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}


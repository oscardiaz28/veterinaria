package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.model.*;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.utils.UTPBinary;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@WebServlet("/register_producto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB
)
public class ProductoController extends HttpServlet {

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
        String categoriaIdStr = req.getParameter("txtcategoriaId");
        //String categoriaNombre = req.getParameter("txtcategoriaNombre");
        String nombre = req.getParameter("txtnombre");
        String descripcion = req.getParameter("txtdescripcion");
        String precioStr = req.getParameter("txtprecio");
        String stockStr = req.getParameter("txtstock");
        String destino = AppConfig.getImgDir();

        try {
            // Validaciones
            Validator.validateNotEmpty(categoriaIdStr, "ID de Categoria");
            //Validator.validateNotEmpty(categoriaNombre, "Nombre de la Categoria");
            Validator.validateNotEmpty(nombre, "Nombre del Producto");
            Validator.validateNotEmpty(descripcion, "Descripcion del producto");
            Validator.validateNotEmpty(precioStr, "Precio del producto");
            Validator.validateNotEmpty(stockStr, "Stock del producto");

            int categoriaId = Integer.parseInt(categoriaIdStr);
            Double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

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

            // Crear el objeto Producto y registrar el producto
            Producto producto = new Producto(nombre, descripcion, precio, foto, stock, categoriaId);
            App.RegProducto.registrarProducto(producto);

            resp.sendRedirect("/listar_producto");

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

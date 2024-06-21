package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.business.RegistroProducto;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Producto;
import pe.edu.utp.service.ArchivoService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/create_producto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)
public class ProductoController extends HttpServlet {

    private RegistroProducto registroProducto = null;
    @Override
    public void init() throws ServletException {
        super.init();
        registroProducto = new RegistroProducto();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String categoria_id = req.getParameter("categoria");
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        String precio = req.getParameter("precio");
        String stock = req.getParameter("stock");

        Categoria categoria = new Categoria( Integer.parseInt(categoria_id) );
        Part filePart = req.getPart("imagen");
        String fileName = ArchivoService.almacenarArchivo(filePart);

        Producto producto = new Producto(nombre, descripcion, Double.parseDouble(precio),
                fileName, Integer.parseInt(stock), categoria );

        registroProducto.createProducto(producto);

        resp.sendRedirect("producto.html");
    }

}

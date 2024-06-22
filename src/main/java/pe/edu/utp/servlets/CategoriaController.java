package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.business.RegistroCategoria;
import pe.edu.utp.model.Categoria;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/create_categorias")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)
public class CategoriaController extends HttpServlet {

    private RegistroCategoria registroCategoria = null;

    @Override
    public void init() throws ServletException {
        super.init();
        registroCategoria = new RegistroCategoria();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}

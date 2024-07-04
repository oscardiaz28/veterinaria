package pe.edu.utp.business;


import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.service.CategoriaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import javax.naming.NamingException;

import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroCategoria {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static CategoriaService categoriaService = null;

    public RegistroCategoria() {
        try {
            categoriaService = new CategoriaService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }

    public static void registrarCategoria(Categoria categoria) throws IOException {
        try {
            categoriaService.addCategoria(categoria);
            System.out.println("Nuevo ok");
        } catch (AlreadyExistsException e) {
            String errorMsg = "AlreadyExistsException: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg,ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (SQLException e) {
            String errorMsg = "SQLException: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (RuntimeException e) {
            String errorMsg = "Error al crear: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //Listar Categoria
    public String getHtmlListarCategoria() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\categoria.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\listado_categoria.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Categoria> listaCategoria = categoriaService.getAllCategoria();

        for (Categoria categoria : listaCategoria) {

            //Tabla Categorias
            String item = htmlItem.replace("${nombre}", categoria.getNombre())
                    .replace("${foto}", categoria.getFoto())
                    .replace("${id}", Integer.toString(categoria.getId()));
            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsCategoria}", itemsHtml.toString());

        return reporteHtml;
    }

    //Combo para add_producto
    public String getHtmlComboCategorias() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar PRODUCTO
        String filename = "src\\main\\resources\\web\\add_producto.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de categorias
        String comboCategorias = categoriaService.getComboCategorias();

        // Reemplazar
        String resultHtml = html.replace("${comboCategorias}",comboCategorias);

        return resultHtml;
    }
}
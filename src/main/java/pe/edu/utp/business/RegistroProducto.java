package pe.edu.utp.business;

import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Producto;
import pe.edu.utp.service.CategoriaService;
import pe.edu.utp.service.ProductoService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroProducto {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ProductoService productoService = null;

    public RegistroProducto() {
        try {
            productoService = new ProductoService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }

    public void createProducto(Producto producto){
        try {
            productoService.addProducto(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHTMListarProducto() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\producto.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los items
        String filenameItems = "src\\main\\resources\\templates\\listado_producto.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar productos
        List<Producto> listado = productoService.getAllProducto();

        for (Producto producto : listado) {
            // Reemplazar los marcadores en la plantilla del item
            String item = htmlItem.replace("${id}", Integer.toString(producto.getId()))
                    .replace("${nombre}", producto.getNombre())
                    .replace("${categoriaNombre}", producto.getCategoriaNombre())
                    .replace("${descripcion}", producto.getDescripcion())
                    .replace("${precio}", Double.toString(producto.getPrecio()))
                    .replace("${foto}", producto.getImagen())
                    .replace("${stock}", Integer.toString(producto.getStock()));
            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsProducto}", itemsHtml.toString());

        return reporteHtml;
    }
}

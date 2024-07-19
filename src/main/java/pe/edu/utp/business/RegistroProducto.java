package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Producto;
import pe.edu.utp.service.ProductoService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroProducto {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ProductoService busquedaProductoService = null;

    public RegistroProducto() {

        try {
            busquedaProductoService = new ProductoService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }


    public static void registrarProducto(Producto producto) throws IOException {
        try {
            busquedaProductoService.addProducto(producto);
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
        List<Producto> listado = busquedaProductoService.getAllProducto();

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

    //Combo para add_ventas
    public String getComboProductos() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar PRODUCTO
        String filename = "src\\main\\resources\\web\\add_detalle_ventas.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de categorias
        String comboProductos = busquedaProductoService.getComboProductos();

        // Reemplazar
        String resultHtml = html.replace("${comboProductos}",comboProductos);

        return resultHtml;
    }
}

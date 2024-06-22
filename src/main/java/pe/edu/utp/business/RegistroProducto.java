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

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\listado_producto.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Producto> listado = productoService.getAllProductos();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Producto producto : listado) {

            //Tabla Clientes
            String item = htmlItem.replace("${nombre}", producto.getNombre())
            .replace("${id}", String.valueOf(producto.getId()) )
                    .replace("${categoria}", String.valueOf(producto.getCategoria().getNombre()) )
                    .replace("${descripcion}", producto.getDescripcion())
                    .replace("${precio}", String.valueOf(producto.getPrecio()))
                    .replace("${stock}", String.valueOf(producto.getStock()))
                    ;
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsProducto}", itemsHtml.toString());

        return reporteHtml;
    }


}

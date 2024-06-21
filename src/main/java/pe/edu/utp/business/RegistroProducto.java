package pe.edu.utp.business;

import pe.edu.utp.model.Producto;
import pe.edu.utp.service.CategoriaService;
import pe.edu.utp.service.ProductoService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

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

}

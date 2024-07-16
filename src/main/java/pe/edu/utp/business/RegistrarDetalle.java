package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Detalle;
import pe.edu.utp.service.DetalleService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistrarDetalle {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static DetalleService busquedaDetalle = null;

    public RegistrarDetalle() {

        try {
            busquedaDetalle = new DetalleService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarDetalle(Detalle detalle) throws IOException {
        try {
            busquedaDetalle.addDetalle(detalle);
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

    public String getHmtlListarDetalle() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\detalle.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los items
        String filenameItems = "src\\main\\resources\\templates\\detalle_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar productos
        List<Detalle> listado = busquedaDetalle.getAllDetalle();

        for (Detalle detalle : listado) {
            // Reemplazar los marcadores en la plantilla del item
            String item = htmlItem.replace("${cod_detalle}", Integer.toString(detalle.getCodigo_detalle()))
                    .replace("${cod_venta}", Integer.toString(detalle.getCodigo_venta()))
                    .replace("${cod_prod}", Integer.toString(detalle.getCodigo_producto()))
                    .replace("${cantidad}", Integer.toString(detalle.getCantidad()))
                    .replace("${precio_unitario}", Double.toString(detalle.getPrecio_unitario()))
                    .replace("${subtotal}", Double.toString(detalle.getSubtotal()))
                    .replace("${estado}", (detalle.getEstado()));
            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsDetalle}", itemsHtml.toString());

        return reporteHtml;
    }




}

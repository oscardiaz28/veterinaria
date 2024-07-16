package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.model.Venta;
import pe.edu.utp.service.TrabajadorService;
import pe.edu.utp.service.VentaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroVentas {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static VentaService BusquedaVentasService = null;


    public RegistroVentas() {
        try {
            BusquedaVentasService = new VentaService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarVenta(Venta venta) throws IOException {
        try {
            BusquedaVentasService.addVenta(venta);
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

    public String getHtmlListarVentas() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\venta.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los items
        String filenameItems = "src\\main\\resources\\templates\\listado_venta.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar productos
        List<Venta> listado = BusquedaVentasService.getAllVentas();

        for (Venta venta : listado) {
            // Reemplazar los marcadores en la plantilla del item
            String item = htmlItem.replace("${codigo_venta}", Integer.toString(venta.getCodigo_venta())
                    .replace("${cliente_dni}", venta.getCliente_dni())
                    .replace("${trabajador_dni}", venta.getTrabajador_dni())
                    .replace("${fecha}", venta.getFecha())
                    .replace("${metodo_pago}", venta.getMetodo_pago()));

            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsVentas}", itemsHtml.toString());

        return reporteHtml;
    }


}

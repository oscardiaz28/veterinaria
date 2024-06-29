package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Producto;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.service.ProductoService;
import pe.edu.utp.service.TrabajadorService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroTrabajador {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static TrabajadorService busquedaTrabajadorService = null;

    public RegistroTrabajador() {

        try {
            busquedaTrabajadorService = new TrabajadorService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    public static void registrarTrabajador(Trabajador trabajador) throws IOException {
        try {
            busquedaTrabajadorService.addTrabajador(trabajador);
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

    public String getHtmlListarTRABAJADOR() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\colaborador.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los items
        String filenameItems = "src\\main\\resources\\templates\\listado_colaborador.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar productos
        List<Trabajador> listado = busquedaTrabajadorService.getAllTrabajador();

        for (Trabajador trabajador : listado) {
            // Reemplazar los marcadores en la plantilla del item
            String item = htmlItem.replace("${dni}", trabajador.getDni())
                    .replace("${nombre_usuario}", trabajador.getUsuario_nombre())
                    .replace("${nombre}", trabajador.getNombre())
                    .replace("${apellido}", trabajador.getApellido())
                    .replace("${cargo}", trabajador.getCargo())
                    .replace("${salario}", Double.toString(trabajador.getSalario()))
                    .replace("${direccion}",trabajador.getDireccion())
                    .replace("${celular}",trabajador.getCelular())
                    .replace("${fecha_contrato}",trabajador.getFecha_contrato())
                    .replace("${estado}",trabajador.getEstado());
            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsTrabajador}", itemsHtml.toString());

        return reporteHtml;
    }
}



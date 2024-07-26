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
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.List;

import static pe.edu.utp.business.RegistroCliente.clienteService;

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
        String filenameItems = "src\\main\\resources\\templates\\listado_trabajador.html";
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

    public String getComboCliente_Trabajadores() throws IOException, SQLException {
        // Cargar la plantilla de la página de agregar PRODUCTO
        String filename = "src\\main\\resources\\web\\add_detalle_ventas.html";
        String html = TextUTP.read(filename);

        // Obtener las opciones del combo de trabajador
        String comboTrabajadores = busquedaTrabajadorService.getComboTrabajadores();

        String comboClientes = clienteService.getComboClientes();

        // Reemplazar
        String resultHtml = html.replace("${comboTrabajadores}",comboTrabajadores)
                .replace("${comboCliente}",comboClientes);

        return resultHtml;
    }

    public String getHtmlDetalleTrabajador(String dniTrabajador) throws IOException, SQLException {
        // Cargar la página de detalle del trabajador
        String filename = "src\\main\\resources\\web\\dashboard.html";
        String html = TextUTP.read(filename);

        // Obtener el trabajador por su DNI
        Trabajador trabajador = busquedaTrabajadorService.getTrabajadorByDNI(dniTrabajador);
        if (trabajador == null) {
            throw new SQLException("Trabajador no encontrado con DNI: " + dniTrabajador);
        }

        // Reemplazar los placeholders en la plantilla con los datos del trabajador
        String resultHtml = html.replace("${dni_trabajador}", trabajador.getDni())
                .replace("${usuario_id}", trabajador.getUsuario_id().toString())
                .replace("${nombre}", trabajador.getNombre())
                .replace("${apellido}", trabajador.getApellido())
                .replace("${cargo}", trabajador.getCargo())
                .replace("${salario}", trabajador.getSalario().toString())
                .replace("${direccion}", trabajador.getDireccion())
                .replace("${celular}", trabajador.getCelular())
                .replace("${fecha_contrato}", trabajador.getFecha_contrato())
                .replace("${estado}", trabajador.getEstado());

        return resultHtml;
    }

    public void actualizarEstadoColaborador(String dni_trabajador) throws SQLException, IOException {
        try {
            busquedaTrabajadorService.actualizarEstadoProyecto(dni_trabajador);
            System.out.println("Estado del proyecto actualizado correctamente.");
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw e;
        }
    }









}



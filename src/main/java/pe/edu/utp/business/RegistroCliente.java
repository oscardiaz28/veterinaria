package pe.edu.utp.business;


import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.service.ClienteService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroCliente {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ClienteService clienteService = null;

    public RegistroCliente() {
        try {
            clienteService = new ClienteService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }

    public static void registrarCliente(Cliente cliente) throws IOException {
        try {
            clienteService.addCliente(cliente);
            System.out.println("Nuevo ok");
        } catch (AlreadyExistsException e) {
            String errorMsg = "AlreadyExistsException: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
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
    public String getHtmlListarCliente() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\cliente.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\listado_cliente.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Cliente> listaCliente = clienteService.getAllCliente();

        for (Cliente cliente : listaCliente) {

            //Tabla Clientes
            String item = htmlItem.replace("${dni_cliente}", cliente.getDni_cliente())
                    .replace("${id_user}", Integer.toString(cliente.getUsuario_id()))
                    .replace("${nombre}", cliente.getNombre())
                    .replace("${apellidos}", cliente.getApellidos())
                    .replace("${direccion}", cliente.getDireccion())
                    .replace("${celular}", cliente.getCelular());
            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsClientes}", itemsHtml.toString());

        return reporteHtml;
    }
}

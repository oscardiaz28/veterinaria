package pe.edu.utp.business;


import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.service.ClienteService;
import pe.edu.utp.service.UsuarioService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroCliente {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ClienteService clienteService = null;
    public static UsuarioService usuarioService = null;

    public List<Cliente> getClienteByDni(String dni){
        List<Cliente> listado = new ArrayList<>();
        try {
            clienteService = new ClienteService(dao);
            listado = clienteService.getClienteByDni(dni);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
        return  listado;
    }

    public RegistroCliente() {
        try {
            clienteService = new ClienteService(dao);
            usuarioService = new UsuarioService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }
    public static void addCliente(Cliente cliente, Usuario usuario)  {
        try {

            boolean result = usuarioService.isUserExist(usuario.getEmail());

            if( result ){
                throw new IllegalArgumentException("El correo ya existe");
            }

            int usuarioId = usuarioService.registrarUsuario(usuario);

            if( usuarioId == -1 ){
                throw new IllegalArgumentException("Hubo un error");

            }else{
                cliente.setUsuario_id(usuarioId);
                clienteService.registroCliente(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
                    .replace("${codigo_mascota}", Integer.toString(cliente.getCodigo_mascota()))
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

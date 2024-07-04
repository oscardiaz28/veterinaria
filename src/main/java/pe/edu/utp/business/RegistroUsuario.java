package pe.edu.utp.business;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.service.UsuarioService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroUsuario {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static UsuarioService usuarioService = null;

    public RegistroUsuario() {
        try {
            usuarioService = new UsuarioService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }

    public static int registrarUsuario(Usuario usuario) throws IOException {
        try {
            usuarioService.addUsuario(usuario);
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
        return 0;
    }

    //ListarUsuario
    public String getHtmlListarUSUARIO() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\usuarios.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los items
        String filenameItems = "src\\main\\resources\\templates\\listado_usuario.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar usuarios
        List<Usuario> listaUsuario = usuarioService.getAllUsuarios();

        for (Usuario usuario : listaUsuario) {
            String token = usuario.getToken() != null ? usuario.getToken() : "";

            // Tabla USUARIO
            String item = htmlItem
                    .replace("${id}", Integer.toString(usuario.getId()))
                    .replace("${email}", usuario.getEmail())
                    .replace("${pass}", usuario.getContra())
                    .replace("${token}", token)
                    .replace("${estado}", usuario.getEstado());

            itemsHtml.append(item);
        }

        // Reemplazar el placeholder de items en la plantilla principal
        html = html.replace("${itemsUSUARIO}", itemsHtml.toString());

        return html;
    }

}

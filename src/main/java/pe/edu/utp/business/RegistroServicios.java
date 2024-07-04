package pe.edu.utp.business;

import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.model.Servicio;
import pe.edu.utp.service.MascotaService;
import pe.edu.utp.service.ServicioService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroServicios {
    public HttpServletResponse resp;
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static ServicioService servicioService = null;
    public RegistroServicios() {
        try {
            servicioService = new ServicioService(dao);
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    // Método para confirmar el registro del servicio
    public static void registrarServicio (Servicio servicio) throws IOException {

        try {
           servicioService.addservice(servicio);
            System.out.println("Nuevo ok");
        } catch (AlreadyExistsException e){
            System.out.println("AlreadyExistsException:" +e.getMessage());
        }
        catch (SQLException e) {
            System.out.println("SQLException:" +e.getMessage());
        }
        catch (RuntimeException e){
            System.out.println("Error al crear:" +e.getMessage());
        } catch (IOException e) {
            String errorMsg = String.format("IOException al registrar: %s", e.getMessage());
            ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            throw new RuntimeException(e);
        }
    }

    //Listar Servicio
    public String getHtmlListarServicio() throws IOException, SQLException {

        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\servicio.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\servicio_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Servicio> listaServicio = servicioService.getAllServicio();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Servicio servicio : listaServicio) {

            //Tabla Proyectos
            String item = htmlItem.replace("${id_servicio}", Integer.toString(servicio.getId_servicio()))
                    .replace("${nombre}", servicio.getNombre())
                    .replace("${descripcion}", servicio.getDescripcion())
                    .replace("${precio}", Double.toString(servicio.getPrecio()))
                    .replace("${foto}", servicio.getImagen());

            itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsServicios}", itemsHtml.toString());

        return reporteHtml;


    }
}

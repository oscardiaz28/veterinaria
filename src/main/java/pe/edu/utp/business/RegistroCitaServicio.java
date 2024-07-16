package pe.edu.utp.business;

import pe.edu.utp.model.CitaServicio;
import pe.edu.utp.service.CitaServicioService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroCitaServicio {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static CitaServicioService citaServicioService = null;

    public RegistroCitaServicio(){
        try {
            citaServicioService = new CitaServicioService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }

    public void registroCitaServicio(CitaServicio citaServicio){
        try {
            citaServicioService.addCitaServicio(citaServicio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHTMListarCitaServicios() throws IOException, SQLException {
    // Cargar plantilla principal
    String filename = "src\\main\\resources\\web\\cita_servicio.html";
    String html = TextUTP.read(filename);

    // Cargar plantilla para los items
    String filenameItems = "src\\main\\resources\\templates\\listado_cita_servicio.html";
    String htmlItem = TextUTP.read(filenameItems);

    // Recorrer la lista
    StringBuilder itemsHtml = new StringBuilder();

    // Listar cita servicios
    List<CitaServicio> listado = citaServicioService.getAllCitaServicios();

    for (CitaServicio citaServicio : listado) {
        // Reemplazar los marcadores en la plantilla del item
        String item = htmlItem.replace("${id}", Integer.toString(citaServicio.getId()))
                .replace("${cita_id}", Integer.toString(citaServicio.getCita_id()))
                .replace("${servicio_id}", Integer.toString(citaServicio.getServicio_id()))
                .replace("${estado}", citaServicio.getEstado());
        itemsHtml.append(item);
    }

    // Reemplazar en la plantilla principal
    String reporteHtml = html.replace("${itemsCitaServicio}", itemsHtml.toString());

    return reporteHtml;
}


}

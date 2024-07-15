package pe.edu.utp.business;

import pe.edu.utp.entities.CitaDto;
import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Cita;
import pe.edu.utp.model.CitaServicio;
import pe.edu.utp.service.CitaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistroCita {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static CitaService citaServicio = null;
    RegistroCitaServicio registroCitaServicio = null;

    public RegistroCita() {
        try {
            citaServicio = new CitaService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
        this.registroCitaServicio = new RegistroCitaServicio();
    }

    public Map<String, String> crearCita(CitaDto dto) throws SQLException, IOException {
        Cita cita = new Cita();
        cita.setCliente_dni( String.valueOf(dto.getId_cliente()) );
        cita.setFecha_registro(LocalDate.parse(dto.getFecha()) );
        cita.setHora(LocalTime.parse(dto.getHora()));
        cita.setMensaje( dto.getMensaje() );
        cita.setCodigo_mascota( Integer.parseInt(dto.getMascotas()) );

        boolean isAlreadyTaken = citaServicio.isTimeTaken(dto.getFecha(), dto.getHora());

        if( isAlreadyTaken ){
            Map<String, String> result = new HashMap<>();
            result.put("message", "La hora elegida ya esta tomada, eliga otra" );
            return result;
        }

        Long idCreated = citaServicio.registroCita(cita);

        List<Integer> servicios = dto.getServicios();

        servicios.forEach( id -> {
            CitaServicio citaServicio = new CitaServicio();
            citaServicio.setCita_id( idCreated.intValue() );
            citaServicio.setServicio_id( id );
            citaServicio.setEstado("en reserva");
            registroCitaServicio.registroCitaServicio(citaServicio);
        });

        Map<String, String> result = new HashMap<>();
        result.put("message", "Cita registrada correctamente" );
        return result;
    }

    /*
    public static void registrarCita(Cita cita) throws IOException {
        try {
            citaServicio.addCita(cita);
            System.out.println("Cita registrada con éxito");
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
            String errorMsg = "Error al registrar la cita: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
        */

    // Listar Citas
public String getHtmlListarCita() throws IOException, SQLException {

    // Cargar plantilla principal
    String filename = "src\\main\\resources\\web\\cita.html";
    String html = TextUTP.read(filename);

    // Cargar plantilla para los items
    String filenameItems = "src\\main\\resources\\templates\\listado_citas.html";
    String htmlItem = TextUTP.read(filenameItems);

    // Recorrer la lista de Citas
    StringBuilder itemsHtml = new StringBuilder();

    // Listar
    List <Cita> listarCitas = citaServicio.getAllCita();

    for (Cita cita : listarCitas) {

        // Tabla Citas
        String item = htmlItem.replace("${id_cita}", Integer.toString(cita.getId_cita()))
                .replace("${cliente_dni}", cita.getCliente_dni())
                .replace("${codigo_mascota}", Integer.toString(cita.getCodigo_mascota()))
                .replace("${dni_trabajador}", cita.getDni_trabajador())
                .replace("${fecha_registro}", cita.getFecha_registro().toString())
                .replace("${fecha}", cita.getFecha().toString())
                .replace("${hora}", cita.getHora().toString())
                .replace("${mensaje}", cita.getMensaje());
        
        itemsHtml.append(item);
    }

    // Reemplazar en la plantilla principal
    String reporteHtml = html.replace("${itemsCitas}", itemsHtml.toString());

    return reporteHtml;
}


}

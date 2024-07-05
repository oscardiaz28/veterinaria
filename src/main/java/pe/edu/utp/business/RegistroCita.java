package pe.edu.utp.business;

import pe.edu.utp.entities.CitaDto;
import pe.edu.utp.model.Cita;
import pe.edu.utp.model.CitaServicio;
import pe.edu.utp.service.CitaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;

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

}

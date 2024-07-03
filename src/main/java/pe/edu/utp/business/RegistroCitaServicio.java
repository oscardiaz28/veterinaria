package pe.edu.utp.business;

import pe.edu.utp.model.CitaServicio;
import pe.edu.utp.service.CitaServicioService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

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

}

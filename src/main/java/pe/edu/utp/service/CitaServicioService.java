package pe.edu.utp.service;

import pe.edu.utp.model.CitaServicio;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CitaServicioService {

    private final Connection cnn;
    public CitaServicioService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void addCitaServicio(CitaServicio citaServicio) throws IOException, SQLException {
        String sql = "INSERT INTO cita_servicio(cita_id, servicio_id, estado) VALUES(?, ?, ?)";

        try{
            PreparedStatement stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, citaServicio.getCita_id());
            stmt.setInt(2, citaServicio.getServicio_id());
            stmt.setString(3, citaServicio.getEstado());

            stmt.executeUpdate();

        }catch(SQLException e){
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }
}

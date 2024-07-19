package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.CitaServicio;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CitaServicioService {

    private final Connection cnn;

    public CitaServicioService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void addCitaServicio(CitaServicio citaServicio) throws IOException, SQLException {
        String sql = "INSERT INTO cita_servicio(cita_id, servicio_id, estado) VALUES(?, ?, ?)";

        try {
            PreparedStatement stmt = cnn.prepareStatement(sql);
            stmt.setInt(1, citaServicio.getCita_id());
            stmt.setInt(2, citaServicio.getServicio_id());
            stmt.setString(3, citaServicio.getEstado());

            stmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    // Metodo para listar CitaServicios
    public List<CitaServicio> getAllCitaServicios() throws SQLException, NotFoundException {
        List<CitaServicio> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarCitaServicios()");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                int id = rst.getInt("id");
                int cita_id = rst.getInt("cita_id");
                int servicio_id = rst.getInt("servicio_id");
                String estado = rst.getString("estado");

                CitaServicio citaServicio = new CitaServicio(id, cita_id, servicio_id, estado);
                lista.add(citaServicio);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ninguna cita de servicio en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

}

package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Cita;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;

import jakarta.validation.OverridesAttribute.List;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class CitaService {

    private final Connection cnn;

    public CitaService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public boolean isTimeTaken(String date, String time) throws IOException, SQLException {
        String sql = "select * from cita where fecha_registro = ? and hora = ?";
        try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
            stmt.setString(1, date);
            stmt.setString(2, time);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                /*
                 * while( rs.next() ){
                 * System.out.println( rs.getString("cliente_dni") );
                 * }
                 */
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    public Long registroCita(Cita cita) throws IOException, SQLException {
        String sql = "INSERT INTO cita(cliente_dni, fecha_registro, hora, mensaje, codigo_mascota) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cita.getCliente_dni());
            stmt.setDate(2, Date.valueOf(cita.getFecha_registro()));
            stmt.setTime(3, Time.valueOf(cita.getHora()));
            stmt.setString(4, cita.getMensaje());
            stmt.setString(5, String.valueOf(cita.getCodigo_mascota()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedkeys = stmt.getGeneratedKeys();
                if (generatedkeys.next()) {
                    long id = generatedkeys.getLong(1);
                    return id;
                }
            }

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
        return -1L;
    }

    /*
    public void addCita(Cita cita) throws SQLException {
        String consulta = "CALL registrarCita(?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = cnn.prepareStatement(consulta);
            stmt.setString(1, cita.getCliente_dni());
            stmt.setInt(2, cita.getCodigo_mascota());
            stmt.setString(3, cita.getDni_trabajador());
            stmt.setDate(4, Date.valueOf(cita.getFecha_registro()));
            stmt.setDate(5, Date.valueOf(cita.getFecha()));
            stmt.setTime(6, Time.valueOf(cita.getHora()));
            stmt.setString(7, cita.getMensaje());

            int num = stmt.executeUpdate();

            if (num == 0) {
                throw new SQLException("No se pudo registrar la cita");
            }

        } catch (SQLException e) {
            throw new SQLException("Error al registrar la cita: " + e.getMessage(), e);
        }
    }
        */

    public java.util.List<Cita> getAllCita() throws SQLException, NotFoundException {
        java.util.List<Cita> lista = new LinkedList<>();

        String consultaSQL = String.format("CALL listarCitas()");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(consultaSQL);
            int conteo = 0;

            while (rst.next()) {
                int id_cita = rst.getInt("id_cita");
                String cliente_dni = rst.getString("cliente_dni");
                int codigo_mascota = rst.getInt("codigo_mascota");
                String dni_trabajador = rst.getString("dni_trabajador");

                // Convertir Date a LocalDate (obligatorios)
                Date sqlFechaRegistro = rst.getDate("fecha_registro");
                if (sqlFechaRegistro == null) {
                    throw new SQLException("El campo 'fecha_registro' no puede ser nulo");
                }
                LocalDate fecha_registro = sqlFechaRegistro.toLocalDate();

                Date sqlFecha = rst.getDate("fecha");
                if (sqlFecha == null) {
                    throw new SQLException("El campo 'fecha' no puede ser nulo");
                }
                LocalDate fecha = sqlFecha.toLocalDate();

                // Convertir Time a LocalTime (obligatorio)
                Time sqlHora = rst.getTime("hora");
                if (sqlHora == null) {
                    throw new SQLException("El campo 'hora' no puede ser nulo");
                }
                LocalTime hora = sqlHora.toLocalTime();

                String mensaje = rst.getString("mensaje");

                Cita cita = new Cita(id_cita, cliente_dni, codigo_mascota, dni_trabajador, fecha_registro, fecha, hora, mensaje);
                cita.setId_cita(id_cita);
                lista.add(cita);
                conteo++;
            }

            if (conteo == 0) {
                throw new NotFoundException("No se encontraron resultados en la BD");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

}

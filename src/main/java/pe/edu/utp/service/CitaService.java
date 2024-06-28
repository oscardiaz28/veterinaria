package pe.edu.utp.service;

import pe.edu.utp.model.Cita;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;

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
            if (rs.next() ){
                /*
                while( rs.next()  ){
                    System.out.println( rs.getString("cliente_dni") );
                }
                 */
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    public Long registroCita(Cita cita) throws IOException, SQLException {
        String sql = "INSERT INTO cita(cliente_dni, fecha_registro, hora) VALUES (?, ?, ?)";

        try{
            PreparedStatement stmt = cnn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString( 1, cita.getCliente_dni() );
            stmt.setDate( 2, Date.valueOf( cita.getFecha_registro()) );
            stmt.setTime(3, Time.valueOf(cita.getHora()) );

            int affectedRows = stmt.executeUpdate();
            if( affectedRows > 0 ){
                ResultSet generatedkeys = stmt.getGeneratedKeys();
                if( generatedkeys.next() ){
                    long id = generatedkeys.getLong(1);
                    return id;
                }
            }

        }catch(SQLException e){
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
        return -1L;
    }
}

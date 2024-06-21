package pe.edu.utp.service;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CategoriaService {

    private final Connection cnn;
    public CategoriaService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar una categoria
    public void newCategoria(Categoria cat) throws SQLException, IOException {
        String consulta = String.format("CALL registrarCategoria(?, ?)");
        ErrorLog.log(consulta, ErrorLog.Level.INFO);
        String id_categoria = String.valueOf(cat.getId_categoria());

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, cat.getNombre());
            pstmt.setString(2, cat.getFoto());

            int num = pstmt.executeUpdate();
            pstmt.close();
            if (num == 0){
                String msg = String.format("Cuidado, no se confirma el registro de la categoria");
                throw new RuntimeException(msg);
            }
        } catch (SQLIntegrityConstraintViolationException e){
            String msg = String.format("No se puede crear la categoria porque ya existe uno igual");
            ErrorLog.log(msg, ErrorLog.Level.ERROR);
            throw new AlreadyExistsException(msg);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

}

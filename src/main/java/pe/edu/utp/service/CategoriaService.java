package pe.edu.utp.service;

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
    public void addCategoria(Categoria cat) throws SQLException, IOException {
        String consulta = String.format("CALL registrarCategoria(?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, cat.getNombre());
            pstmt.setString(2, cat.getFoto());

            int num = pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }
}

package pe.edu.utp.service;

import pe.edu.utp.model.Categoria;
import pe.edu.utp.util.DataAccess;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoriaService {

    private final Connection conn;

    public CategoriaService(DataAccess dao) throws SQLException, NamingException {
        this.conn =dao.getConnection();
    }

    public void addCategoria(Categoria categoria){
        String sql = String.format("CALL registrarCategoria(?, ?)");

        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getFoto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

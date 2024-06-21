package pe.edu.utp.service;

import pe.edu.utp.model.Producto;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoService {

    private final Connection cnn;
    public ProductoService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void addProducto(Producto producto) throws SQLException {
        String consulta = String.format("CALL registrarProducto(?, ?, ?, ?, ?, ?)");

        try {
            PreparedStatement stmt = cnn.prepareStatement(consulta);
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setString(4, producto.getImagen());
            stmt.setInt(5, producto.getCategoria().getId());
            stmt.setInt(6, producto.getStock());

            int num = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

}

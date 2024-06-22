package pe.edu.utp.service;

import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Producto;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

    public List<Producto> getAllProductos(){
        List<Producto> listado = new LinkedList<>();
        String consulta = String.format("CALL listarProductos()");

        try{
            ResultSet rs = cnn.createStatement().executeQuery(consulta);

            while(rs.next()){
                Producto producto = new Producto();

                producto.setId( rs.getInt("producto_id") );
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setImagen(rs.getString("producto_imagen"));
                producto.setStock(rs.getInt("stock"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNombre(rs.getString("categoria_nombre"));
                categoria.setFoto(rs.getString("categoria_foto"));

                producto.setCategoria(categoria);
                listado.add(producto);
            }
        }catch(Exception e){
        }
        return listado;
    }

}

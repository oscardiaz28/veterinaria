package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Producto;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ProductoService {

    private final Connection cnn;
    public ProductoService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void addProducto(Producto producto) throws SQLException {
        String consulta = "CALL registrarProducto(?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = cnn.prepareStatement(consulta);
            stmt.setInt(1, producto.getCategoria());// ID de la categoría
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getDescripcion());
            stmt.setDouble(4, producto.getPrecio());
            stmt.setInt(5, producto.getStock());
            stmt.setString(6, producto.getImagen());

            int num = stmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }


    //Metodo para listar Productos
    public List<Producto> getAllProducto() throws SQLException, NotFoundException {
        List<Producto> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarProductos()");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                int id_producto = rst.getInt("producto_id");
                String nombre = rst.getString("nombre");
                int categoria = rst.getInt("categoria_id");
                String descripcion = rst.getString("descripcion");
                Double precio = rst.getDouble("precio");
                String foto = rst.getString("producto_imagen");
                Integer stock = rst.getInt("stock");
                String categoriaNombre = rst.getString("categoria_nombre");

                Producto producto = new Producto(nombre,descripcion,precio,foto,stock,categoria,categoriaNombre);
                producto.setId(id_producto);
                lista.add(producto);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ningun producto en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

    //Metodo Combo Producto
    public String getComboProductos() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        String strSQL = "SELECT codigo_producto, nombre FROM producto";

        try {
            Statement stmt = cnn.createStatement();
            ResultSet rst = stmt.executeQuery(strSQL);

            while (rst.next()) {
                int codigo_producto = rst.getInt("codigo_producto");
                String nombre = rst.getString("nombre");
                sb.append(String.format("<option value=\"%d\">%s</option>", codigo_producto, nombre));
            }
            rst.close();
            stmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener la lista de los productos");
        }

        return sb.toString();
    }






}

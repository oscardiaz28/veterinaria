package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Detalle;
import pe.edu.utp.model.Producto;
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

public class DetalleService {

    private final Connection cnn;
    public DetalleService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar
    public void addDetalle(Detalle det) throws SQLException, IOException {
        String consulta = String.format("CALL registrarDetalle(?, ?, ?, ?, ?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setInt(1, det.getCodigo_venta());
            pstmt.setInt(2, det.getCodigo_producto());
            pstmt.setInt(3, det.getCantidad());
            pstmt.setDouble(4, det.getPrecio_unitario());
            pstmt.setDouble(5, det.getSubtotal());
            pstmt.setString(6, det.getEstado());

            int num = pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    //Metodo para listar Detalle
    public List<Detalle> getAllDetalle() throws SQLException, NotFoundException {
        List<Detalle> lista = new LinkedList<>();

        String strSQL = String.format("CALL listarDetalle()");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                int codigo_detalle = rst.getInt("codigo_detalle");
                int codigo_venta = rst.getInt("codigo_venta");
                int codigo_producto = rst.getInt("codigo_producto");
                int cantidad = rst.getInt("cantidad");
                Double precio = rst.getDouble("precio_unitario");
                Double subTotal = rst.getDouble("subtotal");
                String estado = rst.getString("estado");

                Detalle detalle = new Detalle(codigo_venta,codigo_producto,cantidad,precio,subTotal,estado);
                detalle.setCodigo_detalle(codigo_detalle);
                lista.add(detalle);
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

}

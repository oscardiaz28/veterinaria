package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Venta;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class VentaService {

    private final Connection cnn;

    public VentaService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public int addVenta(Venta vta) throws SQLException, IOException {
        String consulta = "{CALL registrarVenta(?, ?, ?, ?, ?)}";

        try {
            CallableStatement cstmt = cnn.prepareCall(consulta);
            cstmt.setString(1, vta.getCliente_dni());
            cstmt.setString(2, vta.getTrabajador_dni());
            cstmt.setString(3, vta.getFecha());
            cstmt.setString(4, vta.getMetodo_pago());
            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);

            cstmt.executeUpdate();

            // Obtener el ID generado
            int codigo_venta = cstmt.getInt(5);
            vta.setCodigo_venta(codigo_venta);
            return codigo_venta;

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    // Metodo para listar las Ventas
    public List<Venta> getAllVentas() throws SQLException, NotFoundException {
        List<Venta> lista = new LinkedList<>();

        String consulta = String.format("CALL ListarVentas()");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){

                int codigo_venta = rst.getInt("codigo_venta");
                String cliente_dni = rst.getString("cliente_dni");
                String trabajador_dni = rst.getString("trabajador_dni");
                String fecha = rst.getString("fecha");
                String metodo_pago = rst.getString("metodo_pago");

                Venta venta = new Venta(cliente_dni,trabajador_dni,fecha,metodo_pago);
                venta.setCodigo_venta(codigo_venta);
                lista.add(venta);
                conteo++;
            }
            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ninguna Venta");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

    public double getTotalVentas() throws SQLException {
        double total = 0;

        try {
            CallableStatement cstmt = cnn.prepareCall("{CALL obtenerTotalVentas(?)}");
            cstmt.registerOutParameter(1, Types.DOUBLE);
            cstmt.execute();

            total = cstmt.getDouble(1);

            cstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return total;
    }

    public int getTotalProductos() throws SQLException, IOException {
        int totalProductos = 0;

        try {
            CallableStatement cstmt = cnn.prepareCall("{CALL verTotalProductos()}");
            ResultSet rs = cstmt.executeQuery(); // Se ejecuta el procedimiento

            // Si hay resultados, mostrar el total de productos
            if (rs.next()) {
                totalProductos = rs.getInt("Total de Productos");
            }

            rs.close();
            cstmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener el total de productos");
        }

        return totalProductos;
    }

    public int getTotalCitas() throws SQLException, IOException {
        int totalCitas = 0;

        try {
            CallableStatement cstmt = cnn.prepareCall("{CALL verTotalCitas()}");
            ResultSet rs = cstmt.executeQuery(); // Se ejecuta el procedimiento

            // Si hay resultados, mostrar el total de productos
            if (rs.next()) {
                totalCitas = rs.getInt("Total de Citas");
            }

            rs.close();
            cstmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener el total de citas");
        }

        return totalCitas;
    }

    public int getTotalClientes() throws SQLException, IOException {
        int totalClientes = 0;

        try {
            CallableStatement cstmt = cnn.prepareCall("{CALL verTotalClientes()}");
            ResultSet rs = cstmt.executeQuery(); // Se ejecuta el procedimiento

            // Si hay resultados, mostrar el total de productos
            if (rs.next()) {
                totalClientes = rs.getInt("Total de Clientes");
            }

            rs.close();
            cstmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener el total de citas");
        }

        return totalClientes;
    }
}

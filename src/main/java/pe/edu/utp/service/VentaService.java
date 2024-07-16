package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Venta;
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

public class VentaService {

    private final Connection cnn;

    public VentaService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void addVenta(Venta vta) throws SQLException, IOException {
        String consulta = String.format("CALL registrarVenta(?, ?, ?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, vta.getCliente_dni());
            pstmt.setString(2, vta.getTrabajador_dni());
            pstmt.setString(3, vta.getFecha());
            pstmt.setString(4, vta.getMetodo_pago());

            int num = pstmt.executeUpdate();

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
}

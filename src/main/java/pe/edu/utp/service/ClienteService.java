package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Trabajador;
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

public class ClienteService {
    private final Connection cnn;
    public ClienteService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar un CLIENTE
    public void addCliente(Cliente cli) throws SQLException, IOException {
        String consulta = String.format("CALL registrarCliente(?, ?, ?, ?, ?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, cli.getDni_cliente());
            pstmt.setInt(2, cli.getUsuario_id());
            pstmt.setString(3, cli.getNombre());
            pstmt.setString(4, cli.getApellidos());
            pstmt.setString(5, cli.getDireccion());
            pstmt.setString(6, cli.getCelular());

            int num = pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    //Metodo para ListarCliente
    public List<Cliente> getAllCliente() throws SQLException, NotFoundException {
        List<Cliente> lista = new LinkedList<>();

        String strSQL = String.format("SELECT * FROM cliente");

        try {
            ResultSet rst = cnn.createStatement().executeQuery(strSQL);
            int count = 0;

            while (rst.next()) {
                String dni = rst.getString("dni_cliente");
                Integer usuarioId = rst.getInt("usuario_id");
                String nombre = rst.getString("nombre");
                String apellidos = rst.getString("apellidos");
                String direccion = rst.getString("direccion");
                String celular = rst.getString("celular");

                Cliente cliente = new Cliente(dni,usuarioId,nombre,apellidos,direccion,celular);
                lista.add(cliente);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ningun colaborador en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }




}

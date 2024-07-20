package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.model.Cliente;
import pe.edu.utp.model.Trabajador;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ClienteService {
    private final Connection cnn;
    public ClienteService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    public void registroCliente(Cliente cliente) throws IOException, SQLException {
        String sql = String.format("INSERT INTO cliente(dni_cliente, usuario_id, nombre, apellidos, direccion, " +
                "celular) VALUES (?, ?, ?, ?, ?, ?)");
        try{
            PreparedStatement stmt = cnn.prepareStatement(sql);
            stmt.setString(1, cliente.getDni_cliente());
            stmt.setInt(2, cliente.getUsuario_id());
            stmt.setString(3, cliente.getNombre());
            stmt.setString(4, cliente.getApellidos());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getCelular());

            stmt.executeUpdate();

        }catch(SQLException e){
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }

    }

    // Metodo para registrar un CLIENTE
    public void addCliente(Cliente cli) throws SQLException, IOException {
        String consulta = String.format("CALL registrarCliente(?, ?, ?, ?, ?, ?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, cli.getDni_cliente());
            pstmt.setInt(2, cli.getCodigo_mascota());
            pstmt.setInt(3, cli.getUsuario_id());
            pstmt.setString(4, cli.getNombre());
            pstmt.setString(5, cli.getApellidos());
            pstmt.setString(6, cli.getDireccion());
            pstmt.setString(7, cli.getCelular());


            int num = pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    public List<Cliente> getClienteByDni(String clienteDni) throws SQLException, NotFoundException {
        List<Cliente> lista = new LinkedList<>();

        String strSQL = String.format("SELECT * FROM cliente where dni_cliente = ?");

        try {
            PreparedStatement stmt = cnn.prepareStatement(strSQL);
            stmt.setString(1, clienteDni);
            ResultSet rst = stmt.executeQuery();
            int conteo = 0;
            int count = 0;

            while (rst.next()) {
                String dni = rst.getString("dni_cliente");
                Integer usuarioId = rst.getInt("usuario_id");
                String nombre = rst.getString("nombre");
                String apellidos = rst.getString("apellidos");
                String direccion = rst.getString("direccion");
                String celular = rst.getString("celular");
                Integer codigo_mascota = rst.getInt("codigo_mascota");

                Cliente cliente = new Cliente(dni,codigo_mascota,usuarioId,nombre,apellidos,direccion,celular);
                lista.add(cliente);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ningun cliente en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
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
                Integer codigo_mascota = rst.getInt("codigo_mascota");

                Cliente cliente = new Cliente(dni,codigo_mascota,usuarioId,nombre,apellidos,direccion,celular);
                lista.add(cliente);
                count++;
            }
            if (count == 0) {
                throw new NotFoundException("No se encontró ningun cliente en la bd");
            }
        } catch (SQLException e) {
            String msg = String.format("Ocurrió una excepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

    //Metodo Combo Clientes
    public String getComboClientes() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        String strSQL = "SELECT nombre, dni_cliente  FROM cliente";

        try {
            Statement stmt = cnn.createStatement();
            ResultSet rst = stmt.executeQuery(strSQL);

            while (rst.next()) {
                String dni_cliente = rst.getString("dni_cliente");
                String nombre = rst.getString("nombre");
                sb.append(String.format("<option value=\"%s\">%s</option>", dni_cliente, nombre ));
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

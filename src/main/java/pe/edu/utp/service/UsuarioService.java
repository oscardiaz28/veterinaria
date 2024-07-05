package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import java.sql.CallableStatement;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UsuarioService {

    private final Connection cnn;

    public UsuarioService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar un usuario
    public void addUsuario(Usuario use) throws SQLException, IOException {
        String consulta = "{CALL registrarUsuario(?, ?, ?, ?)}";
        try (CallableStatement cstmt = cnn.prepareCall(consulta)) {
            cstmt.setString(1, use.getEmail());
            cstmt.setString(2, use.getContra());
            cstmt.setString(3, use.getEstado());
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

            cstmt.executeUpdate();

            // Obtener el ID generado
            int idUsuario = cstmt.getInt(4);
            use.setId(idUsuario);
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }




    // Metodo para listar Usuarios
    public List<Usuario> getAllUsuarios() throws SQLException, NotFoundException {
        List<Usuario> lista = new LinkedList<>();

        String consulta = String.format("CALL ListarUsuario()");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){

                int id = rst.getInt("id");
                String email = rst.getString("email");
                String contra = rst.getString("password");
                String tooken = rst.getString("token");
                String estado = rst.getString("estado");

                Usuario usuario = new Usuario(id, email, contra, tooken, estado);
                lista.add(usuario);
                conteo++;
            }
            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ningun usuario");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }
}

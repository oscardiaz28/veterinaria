package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public boolean isUserExist(String email) throws IOException, SQLException {
        String sql = "select * from usuario where email = ?";
        try{
            PreparedStatement stmt = cnn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if( rs.next() ){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    public int registrarUsuario(Usuario use) throws SQLException, IOException {
        String consulta = "INSERT into usuario(email, password, token, estado) VALUES(?, ?, ?, ?)";
        System.out.println(consulta);
        try {
            PreparedStatement stmt = cnn.prepareStatement(consulta, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, use.getEmail());
            stmt.setString(2, use.getContra());
            stmt.setString(3, use.getToken());
            stmt.setString(4, use.getEstado());

            int affectedRows = stmt.executeUpdate();
            if( affectedRows > 0 ){
                ResultSet generatedkeys = stmt.getGeneratedKeys();
                if( generatedkeys.next() ){
                    long id = generatedkeys.getLong(1);
                    return (int)id;
                }
            }
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
        return -1;
    }

    // Metodo para registrar un usuario
    public int addUsuario(Usuario use) throws SQLException, IOException {
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
            return idUsuario;

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    public static String md5(String data) throws IOException {
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            MessageDigest msg = (MessageDigest) md.clone();
            msg.update(data.getBytes());
            return byteArrayToHex(msg.digest());
        } catch (CloneNotSupportedException | NoSuchAlgorithmException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            return data;
        }
    }
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
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

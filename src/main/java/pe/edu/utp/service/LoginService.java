package pe.edu.utp.service;

import pe.edu.utp.util.DataAccess;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LoginService {

    private final Connection conn;

    public LoginService(DataAccess dao) throws SQLException, NamingException {
        this.conn = dao.getConnection();
    }

    public Map<String, String> findByEmail(String email) throws SQLException {
        Map<String, String> result = null;
        String sql = "select u.id as \"id_usuario\", rol, u.email as \"email\", u.password as 'password', token, estado, dni_cliente, \n" +
                "nombre, apellidos, direccion, celular from usuario u inner join cliente c on u.id = c.usuario_id " +
                "where email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        System.out.println(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){
            result = getData(rs);
        }
        return result;
    }


    private Map<String, String> getData(ResultSet rs) throws SQLException {
        Map<String, String> values = new HashMap<>();
        values.put("id_usuario", rs.getString("id_usuario"));
        values.put("email", rs.getString("email"));
        values.put("token", rs.getString("token"));
        values.put("estado", rs.getString("estado"));
        values.put("password", rs.getString("password"));
        values.put("dni_cliente", rs.getString("dni_cliente"));
        values.put("nombre", rs.getString("nombre"));
        values.put("apellidos", rs.getString("apellidos"));
        values.put("direccion", rs.getString("direccion"));
        values.put("celular", rs.getString("celular"));
        values.put("rol", rs.getString("rol"));
        return values;
    }

}

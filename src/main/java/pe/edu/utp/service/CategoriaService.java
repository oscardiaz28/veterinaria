package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Categoria;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CategoriaService {

    private final Connection cnn;
    public CategoriaService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Metodo para registrar una categoria
    public void addCategoria(Categoria cat) throws SQLException, IOException {
        String consulta = String.format("CALL registrarCategoria(?, ?)");

        try {
            PreparedStatement pstmt = cnn.prepareStatement(consulta);
            pstmt.setString(1, cat.getNombre());
            pstmt.setString(2, cat.getFoto());

            int num = pstmt.executeUpdate();

        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException(e);
        }
    }

    // Metodo para listar las Categoria
    public List<Categoria> getAllCategoria() throws SQLException, NotFoundException{
        List<Categoria> lista = new LinkedList<>();

        String consulta = String.format("CALL ListarCategoria()");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){

                int id = rst.getInt("id_categoria");
                String nombre = rst.getString("nombre");
                String img = rst.getString("img");

                Categoria categoria = new Categoria(id, nombre, img);
                categoria.setId(id);
                lista.add(categoria);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ninguna Categoria");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }
    //Metodo Combo Categoria
    public String getComboCategorias() throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        String strSQL = "SELECT id_categoria, nombre FROM categoria";

        try {
            Statement stmt = cnn.createStatement();
            ResultSet rst = stmt.executeQuery(strSQL);

            while (rst.next()) {
                int id_categoria = rst.getInt("id_categoria");
                String nombre = rst.getString("nombre");
                sb.append(String.format("<option value=\"%d\">%s</option>", id_categoria, nombre));
            }
            rst.close();
            stmt.close();
        } catch (SQLException e) {
            ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
            throw new SQLException("Error al obtener la lista de las categorias");
        }

        return sb.toString();
    }


}

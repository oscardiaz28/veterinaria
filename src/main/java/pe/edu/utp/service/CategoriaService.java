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
    public List<Categoria> getAllCategoria() throws SQLException, NotFoundException {
        List<Categoria> lista = new LinkedList<>();

        String consulta = String.format("select * from categoria");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consulta);
            int conteo = 0;

            while (rst.next()){
                String foto = rst.getString("foto");
                String nombre = rst.getString("nombre");

                Categoria categoria = new Categoria(foto, nombre);
                lista.add(categoria);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro ninguna actividad en este proyecto");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }

}

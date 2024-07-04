package pe.edu.utp.service;

import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Servicio;
import pe.edu.utp.util.DataAccess;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ServicioService {

    private final Connection cnn;

    public ServicioService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }
    // Método para registrar a un servicio
    public void addservice (Servicio servicio) throws SQLException, IOException {
        String consultaSQL = "CALL registrarservicio(?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnn.prepareStatement(consultaSQL);
            pstm.setInt(1, servicio.getId_servicio());  // Codigo de la mascota
            pstm.setString(2, servicio.getNombre());
            pstm.setString(3, servicio.getDescripcion());
            pstm.setDouble(4, servicio.getPrecio());
            pstm.setString(5, servicio.getImagen());

            int num = pstm.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    // Método para listar a los servicios
    public List<Servicio> getAllServicio() throws SQLException, NotFoundException {
        List<Servicio> lista = new LinkedList<>();

        String consultaSQL = String.format("CALL listarservicio()");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consultaSQL);
            int conteo = 0;

            while (rst.next()){
                int id_servicio = rst.getInt("id_servicio");
                String nombre = rst.getString("nombre");
                String descripcion = rst.getString("descripcion");
                Double precio = rst.getDouble("precio");
                String imagen = rst.getString("imagen");

                Servicio servicio = new Servicio(id_servicio, nombre, descripcion, precio, imagen);
                servicio.setId_servicio(id_servicio);
                lista.add(servicio);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro resultados en la BD");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return lista;
    }
}

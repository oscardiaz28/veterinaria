package pe.edu.utp.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.util.DataAccess;

public class MascotaService {
    
    private final Connection cnn;
    
    public MascotaService (DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

    // Método para registrar a una mascota
    public void addMascota (Mascota masc) throws SQLException, IOException {
        String consultaSQL = "CALL registrarMascotas(?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = cnn.prepareStatement(consultaSQL);
            pstm.setInt(1, masc.getCodigo());  // Codigo de la mascota
            pstm.setString(2, masc.getDni_cliente());
            pstm.setString(3, masc.getNombre());
            pstm.setString(4, masc.getEspecie());
            pstm.setString(5, masc.getRaza());
            pstm.setString(6, masc.getEdad());
            pstm.setString(7, masc.getGenero());
            pstm.setString(8, masc.getFoto());

            int num = pstm.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    // Método para listar a las macotas
    public List<Mascota> getAllMascota() throws SQLException, NotFoundException{
        List<Mascota> lista = new LinkedList<>();

        String consultaSQL = String.format("CALL listarMascotas()");

        try{
            ResultSet rst = cnn.createStatement().executeQuery(consultaSQL);
            int conteo = 0;

            while (rst.next()){
                int codigo = rst.getInt("codigo");
                String dni_cliente = rst.getString("dni_cliente");
                String nombre = rst.getString("nombre");
                String especie = rst.getString("especie");
                String raza = rst.getString("raza");
                String edad = rst.getString("edad");
                String genero = rst.getString("genero");
                String foto = rst.getString("foto");

                Mascota mascota = new Mascota(codigo, dni_cliente, nombre, especie, raza, edad, genero, foto);
                mascota.setCodigo(codigo);
                lista.add(mascota);
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

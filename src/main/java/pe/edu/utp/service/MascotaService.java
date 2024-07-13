package pe.edu.utp.service;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import pe.edu.utp.exceptions.NotFoundException;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.util.ErrorLog;

public class MascotaService {
    
    private final Connection cnn;
    
    public MascotaService (DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }
    // Método para registrar a una mascota
    public int addMascota(Mascota mascota) throws SQLException, IOException {
            String consulta = "{CALL registrarMascota(?, ?, ?, ?, ?, ?, ?)}";
            try (CallableStatement cstmt = cnn.prepareCall(consulta)) {
                cstmt.setString(1, mascota.getNombre());
                cstmt.setString(2, mascota.getEspecie());
                cstmt.setString(3, mascota.getRaza());
                cstmt.setString(4, mascota.getEdad());
                cstmt.setString(5, mascota.getGenero());
                cstmt.setString(6, mascota.getFoto());

                // Registrar el parámetro de salida a
                cstmt.registerOutParameter(7, Types.INTEGER);

                cstmt.executeUpdate();

                // Obtener el código de la mascota generada
                int codigoMascota = cstmt.getInt(7);
                mascota.setCodigo(codigoMascota);
                return codigoMascota;

            } catch (SQLException e) {
                ErrorLog.log(e.getMessage(), ErrorLog.Level.ERROR);
                throw new SQLException(e);
            }
        }
    public List<Mascota> getMascotasByClienteDni(String cliente_dni) throws SQLException {
        List<Mascota> mascotas = new LinkedList<>();
        String sql = String.format("select * from mascota m where m.cliente_dni = ?");
        try{
            PreparedStatement stmt = cnn.prepareStatement(sql);
            stmt.setString(1, cliente_dni);
            ResultSet rst = stmt.executeQuery();
            int conteo = 0;

            while (rst.next()){
                int codigo = rst.getInt("codigo");
                String nombre = rst.getString("nombre");
                String especie = rst.getString("especie");
                String raza = rst.getString("raza");
                String edad = rst.getString("edad");
                String genero = rst.getString("genero");
                String foto = rst.getString("foto");
                String clienteDni = rst.getString("cliente_dni");

                Mascota mascota = new Mascota(codigo, nombre, especie, raza, edad, genero, foto);
                mascota.setCliente_dni(cliente_dni);
                mascota.setCodigo(codigo);
                mascotas.add(mascota);
                conteo++;
            }

            if (conteo == 0 ){
                throw new NotFoundException("No se encontro resultados en la BD");
            }
        }catch (SQLException e){
            String msg = String.format("Ocurrió una exepción SQL: %s", e.getMessage());
            throw new SQLException(msg);
        }
        return mascotas;
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
                String nombre = rst.getString("nombre");
                String especie = rst.getString("especie");
                String raza = rst.getString("raza");
                String edad = rst.getString("edad");
                String genero = rst.getString("genero");
                String foto = rst.getString("foto");

                Mascota mascota = new Mascota(codigo, nombre, especie, raza, edad, genero, foto);
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

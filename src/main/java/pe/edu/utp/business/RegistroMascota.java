package pe.edu.utp.business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import pe.edu.utp.exceptions.AlreadyExistsException;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.model.Usuario;
import pe.edu.utp.service.MascotaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import pe.edu.utp.util.ErrorLog;
import pe.edu.utp.utils.TextUTP;

public class RegistroMascota {
    
    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static MascotaService mascotaService = null;

    public RegistroMascota() {
        try {
            mascotaService = new MascotaService(dao); // Inicia busquedaService
        } catch (SQLException | NamingException e) {
            String msg = String.format("Error del motor de bd: %s%n", e.getMessage());
            System.out.printf(msg);
            System.exit(1);
        }
    }

    // Método para confirmar el registro de la mascota
    public static int registrarMascota(Mascota mascota) throws IOException {
        try {
            System.out.println("Nuevo ok");
            return mascotaService.addMascota(mascota);
        } catch (AlreadyExistsException e) {
            String errorMsg = "AlreadyExistsException: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg,ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (SQLException e) {
            String errorMsg = "SQLException: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        } catch (RuntimeException e) {
            String errorMsg = "Error al crear: " + e.getMessage();
            System.out.println(errorMsg);
            try {
                ErrorLog.log(errorMsg, ErrorLog.Level.ERROR);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return 0;
    }

    public List<Mascota> getMascotas() throws SQLException {
        return mascotaService.getAllMascota();
    }

    public List<Mascota> getMascotasByDni(String cliente_dni) throws SQLException {
        return mascotaService.getMascotasByClienteDni(cliente_dni);
    }

    //Listar Mascota
    public String getHtmlListarMascota() throws IOException, SQLException {

        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\mascota.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\mascota_listado.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista de Proyectos
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Mascota> listaMascotas = mascotaService.getAllMascota();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Mascota mascota : listaMascotas) {

            //Tabla Proyectos
            String item = htmlItem.replace("${codigo}", Integer.toString(mascota.getCodigo()))
                    .replace("${nombre}", mascota.getNombre())
                    .replace("${especie}", mascota.getEspecie())
                    .replace("${raza}", mascota.getRaza())
                    .replace("${edad}", mascota.getEdad())
                    .replace("${genero}", mascota.getGenero())
                    .replace("${foto}", mascota.getFoto());
                    itemsHtml.append(item);
        }

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsMascotas}", itemsHtml.toString());

        return reporteHtml;


    }
}

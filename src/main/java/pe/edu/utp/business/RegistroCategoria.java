package pe.edu.utp.business;


import pe.edu.utp.model.Categoria;
import pe.edu.utp.service.CategoriaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;
import javax.naming.NamingException;
import pe.edu.utp.utils.TextUTP;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistroCategoria {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static CategoriaService categoriaService = null;

    public RegistroCategoria() {
        try {
            categoriaService = new CategoriaService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
        }
    }

    public void createCategoria(Categoria categoria) {
        try {
            categoriaService.addCategoria(categoria);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Listar Avance
    public String getHtmlListarCategoria() throws IOException, SQLException {
        // Cargar plantilla principal
        String filename = "src\\main\\resources\\web\\categoria.html";
        String html = TextUTP.read(filename);

        // Cargar plantilla para los item
        String filenameItems = "src\\main\\resources\\templates\\listado_categoria.html";
        String htmlItem = TextUTP.read(filenameItems);

        // Recorrer la lista
        StringBuilder itemsHtml = new StringBuilder();

        // Listar
        List<Categoria> listaCategoria = categoriaService.getAllCategoria();
        //String comboClientes = busquedaServiceProyecto.getComboClientes();

        for (Categoria categoria : listaCategoria) {

            //Tabla Clientes
            String item = htmlItem.replace("${nombre}", categoria.getNombre())
                    .replace("${foto}", categoria.getFoto());
            itemsHtml.append(item);
        }
        // Reemplazar en la plantilla principal
        /*String reporteHtml = html.replace("${itemsProyecto}", itemsHtml.toString())
                .replace("${comboClientes}", comboClientes);*/

        // Reemplazar en la plantilla principal
        String reporteHtml = html.replace("${itemsCategoria}", itemsHtml.toString());

        return reporteHtml;
    }


}

package pe.edu.utp.business;

import pe.edu.utp.model.Categoria;
import pe.edu.utp.service.CategoriaService;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.util.DataAccessMariaDB;

import javax.naming.NamingException;
import java.sql.SQLException;

public class RegistroCategoria {

    String cnx = AppConfig.getConnectionStringCFN();
    DataAccessMariaDB dao = new DataAccessMariaDB(cnx);
    public static CategoriaService categoriaService = null;

    public RegistroCategoria(){
        try {
            categoriaService = new CategoriaService(dao);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    public void createCategoria(Categoria categoria){
        categoriaService.addCategoria(categoria);
    }

}

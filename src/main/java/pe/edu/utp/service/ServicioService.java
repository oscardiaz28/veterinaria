package pe.edu.utp.service;

import pe.edu.utp.util.DataAccess;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.SQLException;

public class ServicioService {

    private final Connection cnn;

    public ServicioService(DataAccess dao) throws SQLException, NamingException {
        this.cnn = dao.getConnection();
    }

}

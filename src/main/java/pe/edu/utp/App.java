package pe.edu.utp;
import jakarta.servlet.MultipartConfigElement;

import pe.edu.utp.servlets.CategoriaController;
import pe.edu.utp.servlets.ProductoController;
import pe.edu.utp.util.*;
import pe.edu.utp.utils.*;

import java.net.URL;


/**
 * Hello UTP!
 *
 */
public class App
{


    //public static  RegistroClientes rgClientes = new RegistroClientes();


    public static void main( String[] args ) throws Exception {

        DataAccess dao = new DataAccessMariaDB(AppConfig.getConnectionStringCFN());
        //busquedaService = new EmprendedoresService(dao);
        //busquedaClienteService = new ClientesService(dao);


        String path = "src\\main\\resources\\web\\";
        JettyAdvUTP webserver = new JettyAdvUTP(8085,path);

        //Inicio Sesion Moderador
        //webserver.addServlet(LoginModeradorServlet.class,"/logeo_admin");
        //webserver.addServlet(DashboardAdminServlet.class, "/admin_dashboard");

        webserver.addServlet(CategoriaController.class, "/create_categorias")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));

        webserver.addServlet(ProductoController.class, "/create_producto")
                .getRegistration().setMultipartConfig(new MultipartConfigElement("src\\main\\resources\\web\\upload"));


        URL myURL = new URL("http://localhost:8085/index.html");
        System.out.println("*********************************************************");
        System.out.println("CLICK AQUI PARA ABRIR LA APLICACION:" + myURL);
        System.out.println("*********************************************************");
        webserver.start();

    }

}

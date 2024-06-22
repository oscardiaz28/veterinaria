package pe.edu.utp.util;

import java.util.ResourceBundle;

public class AppConfig {

    static ResourceBundle rb = ResourceBundle.getBundle("config");
    public static String getConnectionStringCFN(){
        return rb.getString("conenctionString");
    }
    public static String getErrorLogFile(){
        return rb.getString("errorLog");
    }
    public static String getTemplateDir(){ return rb.getString("template_dir"); }

    //Imagenes
    public static String getImgDir(){ return rb.getString("upload_dir"); }

    //Web
    public static String getWebDir(){ return rb.getString("document_root"); }

    //Emprendedor
    public static String getDashboardTemplate() { return getTemplateDir() + "\\dashboard.html";}
    public static String getGenericErrorTemplate() { return getTemplateDir() + "\\generic_error.html";}
    public static String getInvalidUserTemplate() { return getTemplateDir() + "\\invalid_user.html";}

    //Cliente
    public static String getDashboardCliente() { return getWebDir() + "\\dashboard-cliente.html";}


    //Moderador
    public static String getDashboardAdmin() { return  getTemplateDir() + "\\admin_dashboard.html";}


    public static String getErrorTemplate() { return  getTemplateDir() + "\\error.html";}
}

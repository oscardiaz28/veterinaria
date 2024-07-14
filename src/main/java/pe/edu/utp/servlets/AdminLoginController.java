package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.utp.business.LoginBusiness;
import pe.edu.utp.service.UsuarioService;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/admin/login")
public class AdminLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/loginAdmin.html").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String correo = req.getParameter("correo");
        String password = req.getParameter("password");

        try{
            Validator.validateNotEmpty(correo, "correo");
            Validator.validateEmail(correo, "correo");
            Validator.validateNotEmpty(password, "password");

            LoginBusiness loginBusiness = new LoginBusiness();

            Map<String, String> usuario = loginBusiness.findByEmailAdmin(correo);

            String hashedPassword = UsuarioService.md5(password);
            boolean isPasswordCorrect = usuario.get("password").equals(hashedPassword);

            if( !isPasswordCorrect ){
                throw new IllegalArgumentException("La contraseña es incorrecta");
            }else{

                Cookie cookie = new Cookie( "dni_trabajador", usuario.get("dni_trabajador") );
                cookie.setMaxAge(60 * 60 * 24); // 1 día de duración
                cookie.setPath("/");
                resp.addCookie(cookie);

                String rol = usuario.get("rol");
                switch(rol){
                    case "TRABAJADOR":
                        resp.sendRedirect("/dashboard.html");
                        break;
                    default:
                        break;
                }
            }

        }catch(Exception e){
            String filename_error = "src\\main\\resources\\templates\\error.html";
            String html_error = TextUTP.read(filename_error);
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().println(html_error.replace("${error}", e.getMessage()));
        }

    }
}

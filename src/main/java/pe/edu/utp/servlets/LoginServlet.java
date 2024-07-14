package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pe.edu.utp.business.LoginBusiness;
import pe.edu.utp.service.UsuarioService;
import pe.edu.utp.util.DataAccess;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String correo = req.getParameter("correo");
        String password = req.getParameter("password");

        try{
            Validator.validateNotEmpty(correo, "correo");
            Validator.validateEmail(correo, "correo");
            Validator.validateNotEmpty(password, "password");

            LoginBusiness loginBusiness = new LoginBusiness();

            Map<String, String> usuario = loginBusiness.findByEmail(correo);

            String hashedPassword = UsuarioService.md5(password);
            boolean isPasswordCorrect = usuario.get("password").equals(hashedPassword);

            if( !isPasswordCorrect ){
                throw new IllegalArgumentException("La contraseña es incorrecta");
            }else{
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        cookie.setValue("");
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    }
                }

                Cookie cookie = new Cookie( "dni_cliente", usuario.get("dni_cliente") );
                cookie.setMaxAge(60 * 60 * 24); // 1 día de duración
                cookie.setPath("/");
                resp.addCookie(cookie);

                String rol = usuario.get("rol");
                switch(rol){
                    case "CLIENTE":
                        resp.sendRedirect("/index.html");
                        break;
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

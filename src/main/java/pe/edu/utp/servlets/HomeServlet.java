package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pe.edu.utp.utils.TextUTP;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/inicio")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtener todas las cookies
        /*
        Cookie[] cookies = req.getCookies();
        String dni = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("dni_cliente")) {
                    dni = cookie.getValue();
                    break;
                }
            }
        }

        String link = "/login.html";
        String colorLink = "font-bold hidden md:inline-block py-3 px-3 text-white rounded-md bg-[#FD4C82]";
        String textLink = "Iniciar Sesión";

        System.out.println("dni: " + dni);

        if( !dni.isEmpty() ){
            link = "/logout";
            colorLink = "font-bold hidden md:inline-block py-3 px-3 text-white rounded-md bg-blue-800";
            textLink = "Cerrar Sesión";
        }else{
            link = "/login.html";
            colorLink = "font-bold hidden md:inline-block py-3 px-3 text-white rounded-md bg-[#FD4C82]";
            textLink = "Iniciar Sesión";
        }

        String filename = "src\\main\\resources\\web\\index.html";
        String html = TextUTP.read(filename);

        html = html.replace("${link}", link);
        html = html.replace("${colorLink}", colorLink);
        html = html.replace("${textLink}", textLink);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().println(html);

         */
    }



}

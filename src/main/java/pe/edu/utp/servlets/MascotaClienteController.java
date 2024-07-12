package pe.edu.utp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pe.edu.utp.App;
import pe.edu.utp.model.Mascota;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@WebServlet("/add_cliente_mascota")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)
public class MascotaClienteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

        List<Mascota> mascotas = new ArrayList<>();

        Map<String, String[]> maps = req.getParameterMap();

        for( Map.Entry<String, String[]> entry : maps.entrySet() ){
            String key = entry.getKey();
            String[] values = entry.getValue();

            if( key.contains("[") ){
                //divide la cadena en funcion del separador ( [ ) , y obtiene el primer elemento del arreglo
                String fieldName = key.split("\\[")[0];
                //obtener index
                int index = Integer.parseInt(key.replaceAll("[^\\d]", ""));
                while( mascotas.size() <= index ){
                    mascotas.add(new Mascota());
                }
                if( fieldName.equals("nombre_mascota") ){
                    mascotas.get(index).setNombre(values[0]);
                    mascotas.get(index).setCliente_dni(dni);
                }else if( fieldName.equals("especie") ){
                    mascotas.get(index).setEspecie(values[0]);
                }else if( fieldName.equals("raza") ){
                    mascotas.get(index).setRaza(values[0]);
                }else if( fieldName.equals("edad") ){
                    mascotas.get(index).setEdad(values[0]);
                }else if( fieldName.equals("genero") ){
                    mascotas.get(index).setGenero(values[0]);
                }
            }else{
            }
        }

        List<String> imageNames = new ArrayList<>();
        Collection<Part> files = req.getParts();
        for( Part file : files ){
            if( file.getSubmittedFileName() != null ){
                String field = file.getName();
                String filename = field + "_" + getFileName(file);
                if(  getFileName(file).isEmpty() ){
                    imageNames.add("default.jpg");
                }else{
                    imageNames.add( filename );
                }
            }
        }

        for(int i = 0; i < imageNames.size(); i++){
            String name = imageNames.get(i);
            String result = name.split("\\[")[0];
            if( result.equals("default.jpg") ){
                mascotas.get(i).setFoto(result);
            }else{
                int index = Integer.parseInt(name.replaceAll("^.*\\[(\\d+)\\].*$", "$1"));
                mascotas.get(index).setFoto(name);
            }
        }



        List<Integer> idMascotas = new ArrayList<>();
        for( Mascota mascota : mascotas ){
            /*
            PrintWriter out = resp.getWriter();
            out.println(mascota.getNombre());
            out.println(mascota.getEspecie());
            out.println(mascota.getRaza());
            out.println(mascota.getEdad());
            out.println(mascota.getGenero());
            out.println(mascota.getFoto());
            out.println( mascota.getCliente_dni() );
             */
            idMascotas.add( App.RegMascotas.registrarMascota(mascota) );

        }
        resp.sendRedirect("/servicios.html");
    }


    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

}

package pe.edu.utp.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import pe.edu.utp.App;
import pe.edu.utp.business.RegistroCita;
import pe.edu.utp.entities.CitaDto;
import pe.edu.utp.model.Mascota;
import pe.edu.utp.util.AppConfig;
import pe.edu.utp.utils.UTPBinary;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/api/crear_cita")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 2,  // Ajusta este valor a 2 MB, por ejemplo
        maxRequestSize = 1024 * 1024 * 2 * 5  // Ajusta este valor a 10 MB, por ejemplo
)
public class CitaController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id_cliente = req.getParameter("id_cliente");
        String nombre = req.getParameter("nombre");
        String apellido = req.getParameter("apellido");
        String fecha = req.getParameter("fecha");
        String hora = req.getParameter("hora");
        String mensaje = req.getParameter("mensaje");
        String telefono = req.getParameter("telefono");

        CitaDto dto = new CitaDto();
        dto.setId_cliente( Integer.parseInt(id_cliente) );
        dto.setNombre(nombre);
        dto.setApellido(apellido);
        dto.setFecha(fecha);
        dto.setHora(hora);
        dto.setMensaje(mensaje);
        dto.setTelefono(telefono);

        String[] servicios = req.getParameterValues("servicios");
        if( servicios != null ){
            List<Integer> idServicios = Arrays.stream( servicios )
                    .map( s -> Integer.valueOf(s) )
                    .collect(Collectors.toList());

            dto.setServicios(idServicios);
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
                imageNames.add( filename );
            }
        }
        for( String name : imageNames){
            String fieldName = name.split("\\[")[0];
            int index = Integer.parseInt(name.replaceAll("^.*\\[(\\d+)\\].*$", "$1"));
            mascotas.get(index).setFoto(name);
        }

        Boolean emptyFields = dto.hasEmptyFields();

        if( emptyFields == true ){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            System.out.println( dto.toString() );
            resp.getWriter().write("{\"message\":\" Todos los campos son obligatorios \"}");
            return;
        }

        List<Integer> idMascotas = new ArrayList<>();
        for( Mascota mascota : mascotas ){
            idMascotas.add( App.RegMascotas.registrarMascota(mascota) );
        }

        dto.setMascotas(idMascotas);

        /***
         * SAVE IMAGES
         */
        String destino = AppConfig.getImgDir();
        try{
            files.forEach( file -> {
                if( file.getSubmittedFileName() != null ){
                    String field = file.getName();
                    String fileName = field + "_" + getFileName(file);
                    String fileFoto = destino + fileName;
                    System.out.println( fileFoto );
                    try{
                        byte[] data = file.getInputStream().readAllBytes();
                        UTPBinary.echobin(data, fileFoto);
                    }catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });
        }catch(Exception e){
            System.out.println( e.getMessage() );
        }

        RegistroCita registroCita = new RegistroCita();

        Map<String, String> result = null;
        try {
            result = registroCita.crearCita(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(result);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write( jsonResult );

        //Read json
        /*
        StringBuilder json = new StringBuilder();
        BufferedReader reader = req.getReader();
        String line;
        while( (line = reader.readLine() ) != null ){
            json.append(line);
        }

        ObjectMapper mapper = new ObjectMapper();
        CitaDto dto = mapper.readValue(json.toString(), CitaDto.class);

        Boolean emptyFields = dto.hasEmptyFields();

        if( emptyFields == true ){
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"message\":\" Todos los campos son obligatorios \"}");
            return;
        }

        RegistroCita registroCita = new RegistroCita();

        Map<String, String> result = null;
        try {
            result = registroCita.crearCita(dto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String jsonResult = mapper.writeValueAsString(result);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write( jsonResult );
         */

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

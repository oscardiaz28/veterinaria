package pe.edu.utp.service;

import jakarta.servlet.http.Part;
import pe.edu.utp.util.AppConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ArchivoService {

    public static String almacenarArchivo(Part part) throws IOException {
        String fileName = getFileName(part);
        String storageLocation = AppConfig.getImgDir();
        File uploadDirFile = new File(storageLocation); //creacion del directorio donde se almacenara el archivo.
        if(!uploadDirFile.exists()){
            uploadDirFile.mkdir();
        }
        //Guardar archivo
        File file = new File(uploadDirFile, fileName); //se crea un objeto File que representa el archivo que se va
        //guardar en el directorio especificado
        try( InputStream fileContent = part.getInputStream()) { //se obtiene los datos del archivo
            Files.copy(fileContent, file.toPath(), StandardCopyOption.REPLACE_EXISTING); //se copia el contenido
            // al directorio especificado.
        }
        return fileName;
    }
    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}

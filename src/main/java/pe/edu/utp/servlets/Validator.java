package pe.edu.utp.servlets;

import java.util.regex.Pattern;

public class Validator {

    public static void validateNotEmpty(String field, String fieldName) throws IllegalArgumentException {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + fieldName + " no puede estar vacío.");
        }
    }

    public static void validateEmail(String field, String fieldName){
        String regex = "^[a-z0-9][\\w\\.]+\\@\\w+?(\\.\\w+)+$";
        Pattern pattern = Pattern.compile(regex);
        boolean result = pattern.matcher(field).matches();
        if( !result ){
            throw new IllegalArgumentException("El formato de correo es inválido");
        }
    }

    public static void validateStringFormat(String field, String fieldName){
        String regex = "^[a-zA-Z\\s]+$";
        Pattern pattern = Pattern.compile(regex);
        boolean result = pattern.matcher(field).matches();
        if( !result ){
            throw new IllegalArgumentException("Formato no valido para el campo " + fieldName);
        }
    }
    public static void validateNumberFormat(String field, String fieldName){
        String regex = "^[0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        boolean result = pattern.matcher(field).matches();
        if( !result ){
            throw new IllegalArgumentException("Solo se permite numeros para el campo telefono");
        }
    }



}

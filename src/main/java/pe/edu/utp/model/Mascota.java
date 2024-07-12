package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Mascota {
    
    // Atributes 
    private int codigo;
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private String genero;
    private String foto;
    private String cliente_dni;
    
    // Constructor vacío 
    public Mascota() {
    }

    //Constructor para Listar
    public Mascota(int codigo, String nombre, String especie, String raza, String edad,
            String genero, String foto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.genero = genero;
        this.foto = foto;
    }

   
    // Constructor para registrar
    public Mascota(String nombre, String especie, String raza, String edad, String genero, String foto) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.genero = genero;
        this.foto = foto;
    }



}

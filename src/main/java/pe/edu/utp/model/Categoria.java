package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Categoria {

    //ATRIBUTOS CATEGORIA
    public Integer id;

    public String nombre;
    private String foto;

    public Categoria(){}


    //Constructor para Listar
    public Categoria(int id,String nombre, String foto){
        this.id = id;
        this.nombre = nombre;
        this.foto =foto;
    }

    // Constructor para registrar
    public Categoria(String nombre, String foto){
        this.nombre = nombre;
        this.foto =foto;
    }

}

package pe.edu.utp.model;

public class Categoria {

    //ATRIBUTOS CATEGORIA
    public int id_categoria;
    public String nombre;

    //CONSTRUCTOR
    public Categoria() {
    }

    //CONSTRUCTOR LLENO
    public Categoria(int id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    //GETTERS
    public int getId_categoria() {
        return id_categoria;
    }

    public String getNombre() {
        return nombre;
    }
}

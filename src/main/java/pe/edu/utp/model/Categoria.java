package pe.edu.utp.model;

public class Categoria {

    //ATRIBUTOS CATEGORIA
    public int id_categoria;
    public String nombre;
    public String foto;

    //CONSTRUCTOR
    public Categoria() {
    }

    //CONSTRUCTOR LLENO
    public Categoria(String  foto, String nombre) {
        this.nombre = nombre;
    }

    //GETTERS
    public String getFoto() {
        return foto;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}

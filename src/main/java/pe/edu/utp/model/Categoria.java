package pe.edu.utp.model;



public class Categoria {

    //ATRIBUTOS CATEGORIA
    public Integer id;
    public String nombre;
    private String foto;

    public Categoria(Integer id){
        this.id = id;
    }
    public Categoria(String nombre, String foto){
        this.nombre = nombre;
        this.foto =foto;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }
}

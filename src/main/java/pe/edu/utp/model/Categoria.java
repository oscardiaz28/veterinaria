package pe.edu.utp.model;

public class Categoria {

    //ATRIBUTOS CATEGORIA
    public Integer id;
    public String nombre;
    private String foto;

    public Categoria(){}

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
    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

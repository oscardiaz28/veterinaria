package pe.edu.utp.model;


public class Servicio {

    //ATRIBUTOS
    public int id_servicio;
    public String nombre;
    public String descripcion;
    public double precio;
    public String foto;

    //CONSTRUCTOR VACIO
    public Servicio() {
    }

    //CONSTRUCTOR LLENO


    public Servicio(int id_servicio, String nombre, String descripcion, double precio, String foto) {
        this.id_servicio = id_servicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.foto = foto;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id_servicio=" + id_servicio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", foto='" + foto + '\'' +
                '}';
    }
}

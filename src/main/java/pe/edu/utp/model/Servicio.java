package pe.edu.utp.model;

public class Servicio {

    //ATRIBUTOS
    public int id_servicio;
    public String nombre;
    public String descripcion;
    public double precio;
    public String imagen;

    //CONSTRUCTOR VACIO
    public Servicio() {
    }

    //CONSTRUCTOR LLENO
    public Servicio(String nombre, String descripcion, double precio, String imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }

    public Servicio(int id_servicio, String nombre, String descripcion, double precio, String imagen) {
        this.id_servicio = id_servicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }


    //GETTERS
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

    public String getImagen() {
        return imagen;
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

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Servicio{" +
                "id_servicio=" + id_servicio +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}

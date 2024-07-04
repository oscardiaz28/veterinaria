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

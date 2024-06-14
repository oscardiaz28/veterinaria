package pe.edu.utp.model;

public class Producto {


    //ATRIBUTOS PRODUCTOS
    public String codigo_producto;
    public int categoria;
    public String nombre;
    public String descripcion;
    public double precio;
    public String imagen;


    public String getCodigo_producto() {
        return codigo_producto;
    }

    public int getCategoria() {
        return categoria;
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
}

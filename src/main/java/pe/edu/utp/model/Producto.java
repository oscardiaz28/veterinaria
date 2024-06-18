package pe.edu.utp.model;

public class Producto {


    //ATRIBUTOS PRODUCTOS
    public String codigo_producto;
    public int categoria;
    public String nombre;
    public String descripcion;
    public double precio;
    public String imagen;

    //CONSTRUCTOR VACIO
    public Producto() {
    }

    //CONSTRUCTOR LLENO
    public Producto(String codigo_producto, int categoria, String nombre, String descripcion, double precio, String imagen) {
        this.codigo_producto = codigo_producto;
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }

    //GETTERS
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

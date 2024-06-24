package pe.edu.utp.model;

public class Producto {

    //ATRIBUTOS PRODUCTOS
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    public String imagen;
    private int categoria;
    private Integer stock;
    private String categoriaNombre;

    public Producto() {
    }

    //Constructor para el Listado del Producto
    public Producto(String nombre, String descripcion, Double precio, String imagen, Integer stock, int categoria, String categoriaNombre){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
        this.categoria = categoria;
        this.categoriaNombre = categoriaNombre;
    }

    // Constructor para el Registro del Producto
    public Producto(String nombre, String descripcion, double precio, String imagen, int stock, int categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
        this.categoria = categoria;
    }


    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public int getCategoria() {
        return categoria;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }


    //Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", imagen='" + imagen + '\'' +
                ", categoria=" + categoria +
                ", stock=" + stock +
                ", categoriaNombre='" + categoriaNombre + '\'' +
                '}';
    }
}

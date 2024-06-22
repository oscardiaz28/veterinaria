package pe.edu.utp.model;

public class Producto {

    //ATRIBUTOS PRODUCTOS
    public Integer id;

    public String nombre;
    public String descripcion;

    public Double precio;
    public String imagen;
    private int categoria;
    private Integer stock;
    private String categoriaNombre;

    public Producto() {
    }

    public Producto(String nombre, String descripcion, Double precio, String imagen, Integer stock, int categoria, String categoriaNombre){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
        this.categoria = categoria;
        this.categoriaNombre = categoriaNombre;
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

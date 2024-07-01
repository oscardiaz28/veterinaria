package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

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



}

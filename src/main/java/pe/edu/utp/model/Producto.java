package pe.edu.utp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Producto {

    //ATRIBUTOS PRODUCTOS
    public Integer id;
    public String nombre;
    public String descripcion;
    public Double precio;
    public String imagen;
    private Categoria categoria;
    private Integer stock;

    public Producto(String nombre, String descripcion, Double precio, String imagen, Integer stock, Categoria categoria){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.stock = stock;
        this.categoria = categoria;
    }

}

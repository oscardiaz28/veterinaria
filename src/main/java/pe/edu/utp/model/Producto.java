package pe.edu.utp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    //ATRIBUTOS PRODUCTOS
    public Integer id;
    public String nombre;
    public String descripcion;
    public Double precio;
    public String imagen;
    private Categoria categoria;

}

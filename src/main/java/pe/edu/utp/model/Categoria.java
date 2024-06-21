package pe.edu.utp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Categoria {

    //ATRIBUTOS CATEGORIA
    public Integer id;
    public String nombre;
    private String foto;

    public Categoria(Integer id){
        this.id = id;
    }
    public Categoria(String nombre, String foto){
        this.nombre = nombre;
        this.foto =foto;
    }

}

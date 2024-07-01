package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cliente {

    //ATRIBUTOS
    private String dni_cliente;
    private Integer usuario_id;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String celular;

    public Cliente() {
    }

    public Cliente(String dni_cliente, Integer usuario_id, String nombre, String apellidos, String direccion, String celular) {
        this.dni_cliente = dni_cliente;
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.celular = celular;
    }
}




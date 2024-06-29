package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Trabajador {

    private String dni;
    private Integer usuario_id;
    private String nombre;
    private String apellido;
    private String cargo;
    private Double salario;
    private String direccion;
    private String celular;
    private String fecha_contrato;
    private String estado;

    //Atributo de Usuario
    private String usuario_nombre;

    public Trabajador() {
    }

    //Para el RegistroTrabajador
    public Trabajador(String dni, Integer usuario_id, String nombre, String apellido, String cargo, Double salario, String direccion, String celular, String fecha_contrato, String estado) {
        this.dni = dni;
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salario = salario;
        this.direccion = direccion;
        this.celular = celular;
        this.fecha_contrato = fecha_contrato;
        this.estado = estado;
    }

    //Para el ListarTrabajador
    public Trabajador(String dni, String nombre, String apellido, String cargo, Double salario, String direccion, String celular, String fecha_contrato, String estado, String usuario_nombre) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salario = salario;
        this.direccion = direccion;
        this.celular = celular;
        this.fecha_contrato = fecha_contrato;
        this.estado = estado;
        this.usuario_nombre = usuario_nombre;
    }
}

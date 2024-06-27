package pe.edu.utp.model;

public class Colaborador {

    //ATRIBUTOS
    private String dni;
    private Integer id_usuario;
    private String nombre;
    private String apellidos;
    private String cargo;
    private Double salario;
    private String direccion;
    private String celular;
    private String fecha_contrato;
    private String estado;

    //Constructor
    public Colaborador() {
    }

    //Constructor Lleno
    public Colaborador(String dni, Integer id_usuario, String nombre, String apellidos, String cargo, Double salario, String direccion, String celular, String fecha_contrato, String estado) {
        this.dni = dni;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.salario = salario;
        this.direccion = direccion;
        this.celular = celular;
        this.fecha_contrato = fecha_contrato;
        this.estado = estado;
    }

    //To String
    @Override
    public String toString() {
        return "Colaborador{" +
                "dni='" + dni + '\'' +
                ", id_usuario=" + id_usuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", cargo='" + cargo + '\'' +
                ", salario=" + salario +
                ", direccion='" + direccion + '\'' +
                ", celular='" + celular + '\'' +
                ", fecha_contrato='" + fecha_contrato + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}



















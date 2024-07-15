package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter

public class Cita {

    private int id_cita;
    private String cliente_dni;
    private int codigo_mascota;
    private String dni_trabajador;
    private LocalDate fecha_registro;
    private LocalDate fecha;
    private LocalTime hora;
    private String mensaje;

    public Cita() {
    }

    public Cita(int id_cita, String cliente_dni, int codigo_mascota, String dni_trabajador, LocalDate fecha_registro,
            LocalDate fecha, LocalTime hora, String mensaje) {
        this.id_cita = id_cita;
        this.cliente_dni = cliente_dni;
        this.codigo_mascota = codigo_mascota;
        this.dni_trabajador = dni_trabajador;
        this.fecha_registro = fecha_registro;
        this.fecha = fecha;
        this.hora = hora;
        this.mensaje = mensaje;
    }
    

}

package pe.edu.utp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Cita {

    private Integer id_cita;
    private String cliente_dni;
    private Integer codigo_mascota;
    private String trabajador;
    private LocalDate fecha_registro;
    private LocalTime hora;
    private String mensaje;

}

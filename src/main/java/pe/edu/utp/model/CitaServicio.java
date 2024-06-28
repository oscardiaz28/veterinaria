package pe.edu.utp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CitaServicio {
    private Integer id;
    private Integer cita_id;
    private Integer servicio_id;
    private String estado;
}

package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CitaServicio {
    private Integer id;
    private Integer cita_id;
    private Integer servicio_id;
    private String estado;

    

    public CitaServicio() {
    }



    public CitaServicio(Integer id, Integer cita_id, Integer servicio_id, String estado) {
        this.id = id;
        this.cita_id = cita_id;
        this.servicio_id = servicio_id;
        this.estado = estado;
    }

 
}

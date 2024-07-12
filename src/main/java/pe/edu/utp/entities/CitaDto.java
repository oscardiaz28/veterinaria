package pe.edu.utp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.edu.utp.model.Mascota;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class CitaDto {

    private String id_cliente;
    private String fecha;
    private String hora;
    private String mensaje;
    private List<Integer> servicios;
    private String mascotas;

    public boolean hasEmptyFields() {
        return id_cliente == null ||
                fecha == null || fecha.isEmpty() ||
                hora == null || hora.isEmpty() ||
                mensaje == null || mensaje.isEmpty() ||
                servicios == null || servicios.isEmpty();
    }
}

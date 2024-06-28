package pe.edu.utp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class CitaDto {

    private Integer id_cliente;
    private String nombre;
    private String apellido;
    private String fecha;
    private String hora;
    private String mensaje;
    private List<Integer> servicios;
    private String telefono;

    public boolean hasEmptyFields() {
        return id_cliente == null ||
                nombre == null || nombre.isEmpty() ||
                apellido == null || apellido.isEmpty() ||
                fecha == null || fecha.isEmpty() ||
                hora == null || hora.isEmpty() ||
                mensaje == null || mensaje.isEmpty() ||
                servicios == null || servicios.isEmpty() ||
                telefono == null || telefono.isEmpty();
    }
}

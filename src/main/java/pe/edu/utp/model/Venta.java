package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Venta {

    private int codigo_venta;
    private String cliente_dni;
    private String trabajador_dni;
    private String fecha;
    private String metodo_pago;

    public Venta()
    {

    }

    public Venta(int codigo_venta, String cliente_dni, String trabajador_dni, String fecha, String metodo_pago) {
        this.codigo_venta = codigo_venta;
        this.cliente_dni = cliente_dni;
        this.trabajador_dni = trabajador_dni;
        this.fecha = fecha;
        this.metodo_pago = metodo_pago;
    }
}

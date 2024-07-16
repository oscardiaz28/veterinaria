package pe.edu.utp.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Detalle {
    private int codigo_detalle;
    private int codigo_venta;
    private int codigo_producto;
    private int cantidad;
    private double precio_unitario;
    private double subtotal;
    private String estado;

    public Detalle()
    {

    }

    public Detalle(int codigo_venta, int codigo_producto, int cantidad, double precio_unitario, double subtotal, String estado) {
        this.codigo_venta = codigo_venta;
        this.codigo_producto = codigo_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
        this.estado = estado;
    }


}

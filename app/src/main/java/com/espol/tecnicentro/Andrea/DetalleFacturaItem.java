package com.espol.tecnicentro.Andrea;

public class DetalleFacturaItem {
    public String nombreServicio;
    public int cantidad;
    public double precioUnitario;

    public DetalleFacturaItem(String nombreServicio, int cantidad, double precioUnitario) {
        this.nombreServicio = nombreServicio;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return cantidad * precioUnitario;
    }
}
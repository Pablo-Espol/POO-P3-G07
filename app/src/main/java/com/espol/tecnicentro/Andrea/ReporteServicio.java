package com.espol.tecnicentro.Andrea;


public class ReporteServicio {
    private final String servicio;
    private final double total;

    public ReporteServicio(String servicio, double total) {
        this.servicio = servicio;
        this.total = total;
    }
    public String getServicio() { return servicio; }
    public double getTotal()    { return total; }
}
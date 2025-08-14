package com.espol.tecnicentro.Andrea;

public class ReporteServicio {
    private String servicio;
    private int total;

    public ReporteServicio(String servicio, int total) {
        this.servicio = servicio;
        this.total = total;
    }

    public String getServicio() {
        return servicio;
    }

    public int getTotal() {
        return total;
    }
}
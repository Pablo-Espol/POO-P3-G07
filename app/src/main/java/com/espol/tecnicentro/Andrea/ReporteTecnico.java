package com.espol.tecnicentro.Andrea;

public class ReporteTecnico {
    private final String tecnico;
    private final double total;

    public ReporteTecnico(String tecnico, double total) {
        this.tecnico = tecnico;
        this.total = total;
    }

    public String getTecnico() { return tecnico; }
    public double getTotal() { return total; }
}
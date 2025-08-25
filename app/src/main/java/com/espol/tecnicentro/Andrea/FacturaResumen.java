
package com.espol.tecnicentro.Andrea;


import java.io.Serializable;
import java.time.LocalDate;
import com.espol.tecnicentro.modelo.Cliente;

public class FacturaResumen implements Serializable {
    private static final long serialVersionUID = 1L;

    private Cliente empresa;
    private int anio;   // 4 dígitos
    private int mes;    // 1..12
    private double total;
    private LocalDate fechaCreacion; // fecha en que se generó la factura (no el período)

    public FacturaResumen(Cliente empresa, int anio, int mes, double total, LocalDate fechaCreacion) {
        this.empresa = empresa;
        this.anio = anio;
        this.mes = mes;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente getEmpresa() { return empresa; }
    public int getAnio() { return anio; }
    public int getMes() { return mes; }
    public double getTotal() { return total; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public void setTotal(double total) { this.total = total; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    // helpers útiles si los necesitas en el adapter
    public String getEmpresaId() { return empresa != null ? empresa.getIdentificacion() : ""; }
    public String getEmpresaNombre() { return empresa != null ? empresa.getNombre() : ""; }
}

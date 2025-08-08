
package com.espol.tecnicentro.modelo;
import java.time.LocalDate;

/**
 *
 * @author Fmalu
 */
public class Insumo {
    
    private String descripcion;
    private LocalDate fechaRegistro;


    public Insumo() {
    }


    public Insumo(String descripcion, LocalDate fechaRegistro) {
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaRegistro() {
        return this.fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }



    @Override
    public String toString() {
        return "{" +
            " descripcion='" + getDescripcion() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            "}";
    }

}

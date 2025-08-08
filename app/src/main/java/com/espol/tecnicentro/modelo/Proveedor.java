/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.tecnicentro.modelo;

/**
 *
 * @author Fmalu
 */
public class Proveedor extends Personal {
    private String descripcion;
    
    public Proveedor(String identificacion, String nombre, String telefono, String descripcion) {
        super(identificacion, nombre, telefono);
        this.descripcion=descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Nombre: %s, Teléfono: %s, Descripción: %s", 
            getIdentificacion(), getNombre(), getTelefono(), getDescripcion());
}
}

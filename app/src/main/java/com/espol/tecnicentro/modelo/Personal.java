/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.tecnicentro.modelo;

import java.io.Serializable;

/**
 *
 * @author Fmalu
 */
public abstract class Personal implements Serializable {
    private String identificacion;
    private String nombre;
    private String direccion;
    private String telefono;

    public Personal(String identificacion, String nombre, String telefono, String direccion) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }
    public Personal(String identificacion, String nombre, String telefono) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.telefono = telefono;
    }
    public Personal(String identificacion) {
        this.identificacion = identificacion;
        
    }



    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Override
    public String toString() {
        return String.format("ID: %s, Nombre: %s, Dirección: %s, Teléfono: %s", 
                           identificacion, nombre, direccion, telefono);}
    
   
    }
    
    
    
    


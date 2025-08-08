/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.tecnicentro.modelo;

/**
 *
 * @author Fmalu
 */
public class Tecnico extends Personal {
    private String especialidad;

    public Tecnico (String identificacion){
        super(identificacion);
    }



    

    public Tecnico(String identificacion, String nombre, String telefono) {
        super(identificacion, nombre, telefono);
    }


    public Tecnico(String identificacion, String nombre, String telefono,String especialidad) {
        super(identificacion, nombre, telefono);
        this.especialidad=especialidad;
    }
    


    @Override
    public String toString() {
        return String.format("ID: %s, Nombre: %s, Teléfono: %s, Especialidad: %s", 
            getIdentificacion(), getNombre(), getTelefono(), getEspecialidad());
    }


    public String getEspecialidad() {
        return especialidad;
    }


    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    
}

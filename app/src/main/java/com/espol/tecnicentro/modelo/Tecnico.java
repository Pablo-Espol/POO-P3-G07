/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.tecnicentro.modelo;


import com.espol.tecnicentro.controladores.ControladorBase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Fmalu
 */
public class Tecnico extends Personal implements Serializable {

    public static final String nomArchivoTec = "Tecnico.ser";
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


    public static boolean crearDatosIniciales(File directorio) throws Exception{
        ArrayList<Tecnico> lista = ControladorBase.getInstance().getListTecni();
        boolean guardado = false;

        File f = new File(directorio, nomArchivoTec);
        if (lista.isEmpty()) {
            lista = ControladorBase.getInstance().getListTecni(); //  carga datos de ejemplo si la lista está vacía
        }

        //se escribe la lista serializada
        if (! f.exists()) { //si no existe se crea la lista
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                os.writeObject(lista);
                guardado = true;
            } catch (IOException e) {
                //quizas lanzar una excepcion personalizada
                throw new Exception(e.getMessage());
            }
        }else guardado = true;//si existe no hace nada
        return guardado;
    }
}

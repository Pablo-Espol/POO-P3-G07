/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.espol.tecnicentro.modelo;

import com.espol.tecnicentro.ListaBase.DatosBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Fmalu
 */
public class Proveedor extends Personal implements Serializable {
    private String descripcion;
    private static final String nomArchivoPr = "Proveedores.ser";

    
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

    public static ArrayList<Proveedor> cargaProveedores(File directorio){
        ArrayList<Proveedor> lista = new ArrayList<>();
        File f = new File(directorio, nomArchivoPr);
        //se escribe la lista serializada
        if ( f.exists()) { //si no existe se crea la lista
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                lista = (ArrayList<Proveedor>) is.readObject();

            } catch (Exception e) {
                //quizas lanzar una excepcion personalizada
                new Exception(e.getMessage());
            }
        }
        return lista;
}

    public static boolean guardarLista(File directorio,ArrayList<Proveedor> lista) throws Exception{
        boolean guardado = false;
        File f = new File(directorio, nomArchivoPr);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            guardado = true;
        } catch (IOException e) {

            //quizas lanzar una excepcion personalizada
            throw new Exception(e.getMessage());
        }
        return guardado;
    }

    public static boolean crearDatosIniciales(File directorio) throws Exception{
        ArrayList<Proveedor> lista = DatosBase.getInstance().getListSuplier();
        boolean guardado = false;

        File f = new File(directorio, nomArchivoPr);
        if (lista.isEmpty()) {
            lista = DatosBase.getInstance().getListSuplier(); //  carga datos de ejemplo si la lista está vacía
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

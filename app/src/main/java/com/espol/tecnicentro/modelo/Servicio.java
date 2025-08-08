
package com.espol.tecnicentro.modelo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

/**
 *
 * @author Pablo
 */
public class Servicio implements Serializable {
    public static final String nomArchivo = "Servicios.ser";
    public static int ultimoId = 0;
    private String codigo,nombre;
    private double precio;

    public Servicio() {
    }

    public Servicio(String nombre, double precio){
        this.nombre=nombre;
        this.precio=precio;
    }
    public Servicio(String codigo){
        this.codigo = codigo;
    }


    public Servicio(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;

    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Servicio servicio = (Servicio) obj;
        return codigo.equals(servicio.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }


    public static ArrayList<Servicio> obtenerServicio(){
        ArrayList<Servicio> listService = new ArrayList<>();


        listService.add(new Servicio("001", "Reparación de motores", 90.0));
        listService.add(new Servicio("002", "Instalación de software", 80.0));
        listService.add(new Servicio("003", "Mantenimiento preventivo", 40.0));
        listService.add(new Servicio("004", "Balanceo y alineación", 70.0));
        listService.add(new Servicio("005", "Cambio de neumaticos", 60.0));
        listService.add(new Servicio("006", "Cambio de aceite", 30.0));
        return  listService;
    }

    @Override
    public String toString() {
        return
                " codigo='" + getCodigo() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", precio='" + getPrecio() + "'";
    }

    //lee el archivo donde se encuentran los datos

    public static ArrayList<Servicio> cargarServicio(File directorio){
        ArrayList<Servicio> lista = new ArrayList<>();
        File f = new File(directorio, nomArchivo);
        //se escribe la lista serializada
        if ( f.exists()) { //si no existe se crea la lista
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                lista = (ArrayList<Servicio>) is.readObject();

            } catch (Exception e) {
                //quizas lanzar una excepcion personalizada
                new Exception(e.getMessage());
            }
        }
        return lista;
    }

    public static boolean crearDatosIniciales(File directorio) throws Exception{
        ArrayList<Servicio> lista = new ArrayList<>();
        boolean guardado = false;

        lista.add(new Servicio("001", "Reparación de motores", 90.0));
        lista.add(new Servicio("002", "Instalación de software", 80.0));
        lista.add(new Servicio("003", "Mantenimiento preventivo", 40.0));
        lista.add(new Servicio("004", "Balanceo y alineación", 70.0));
        lista.add(new Servicio("005", "Cambio de neumaticos", 60.0));
        lista.add(new Servicio("006", "Cambio de aceite", 30.0));

        File f = new File(directorio, nomArchivo);
        if (lista.isEmpty()) {
            lista = obtenerServicio(); //  carga datos de ejemplo si la lista está vacía
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

    public static boolean guardarLista(File directorio,ArrayList<Servicio> lista) throws Exception{
        boolean guardado = false;
        File f = new File(directorio, nomArchivo);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            guardado = true;
        } catch (IOException e) {

            //quizas lanzar una excepcion personalizada
            throw new Exception(e.getMessage());
        }
        return guardado;
    }
}

  

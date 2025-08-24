package com.espol.tecnicentro.modelo;
import com.espol.tecnicentro.ListaBase.DatosBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


public class OrdenServicio implements Serializable {
    public static final String nombArchOrden = "Ordenes.ser";
    private Cliente cliente;
    private Tecnico tecnico;
    private LocalDate fechaServicio;
    private String placaVehiculo;
    private double totalOrden;
    private TipoVehiculo tipoVehiculo;
    private ArrayList <DetalleServicio> listaOrdeServicios;


    public OrdenServicio(){}

    //constructor ordenes
    public OrdenServicio(Cliente cliente, LocalDate fechaServicio, TipoVehiculo tipoVehiculo, String placaVehiculo){
        this.cliente = cliente;
        this .tipoVehiculo = tipoVehiculo;
        this.placaVehiculo = placaVehiculo;
        this.fechaServicio= fechaServicio;
    }
    //Constructor para generar facturas (clientes empresariales)

    public OrdenServicio(Cliente cliente, Tecnico tecnico, LocalDate fechaServicio, String placaVehiculo, double totalOrden,
    TipoVehiculo tipoVehiculo, ArrayList<DetalleServicio> servicios) {
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.fechaServicio = fechaServicio;
        this.placaVehiculo = placaVehiculo;
        this.totalOrden = totalOrden;
        this.tipoVehiculo = tipoVehiculo;
        this.listaOrdeServicios = servicios;
    }
    
    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return this.tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getFechaServicio() {
        return this.fechaServicio;
    }

    public void setFechaServicio(LocalDate fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public String getPlacaVehiculo() {
        return this.placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public double getTotalOrden() {
        return this.totalOrden;
    }

    public void setTotalOrden(double totalOrden) {
        this.totalOrden = totalOrden;
    }


    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }


    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }


    public ArrayList<DetalleServicio> getServicios() {
        return listaOrdeServicios;
    }


    public void setServicios(ArrayList<DetalleServicio> servicios) {
        this.listaOrdeServicios = servicios;
    }

    @Override
    public String toString() {
        return "OrdenServicio [cliente=" + cliente + ", tecnico=" + tecnico + ", fechaServicio=" + fechaServicio
                + ", placaVehiculo=" + placaVehiculo + ", totalOrden=" + totalOrden + ", tipoVehiculo=" + tipoVehiculo
                + ", servicios=" + listaOrdeServicios + "]";
    }



    public static ArrayList<OrdenServicio> obtenerOrdenes(){

        return  DatosBase.getInstance().getListOrden();
    }

    public static ArrayList<OrdenServicio> cargarOrdenes(File directorio){
        ArrayList<OrdenServicio> lista = new ArrayList<>();
        File f = new File(directorio, nombArchOrden);
        //se escribe la lista serializada
        if ( f.exists()) { //si no existe se crea la lista
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                lista = (ArrayList<OrdenServicio>) is.readObject();

            } catch (Exception e) {
                //quizas lanzar una excepcion personalizada
                new Exception(e.getMessage());
            }
        }
        return lista;
    }

    public static boolean crearDatosIniciales(File directorio) throws Exception{
        ArrayList<OrdenServicio> lista = DatosBase.getInstance().getListOrden();
        boolean guardado = false;

        File f = new File(directorio, nombArchOrden);
        if (lista.isEmpty()) {
            lista = obtenerOrdenes(); //  carga datos de ejemplo si la lista está vacía
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

    public static boolean guardarLista(File directorio,ArrayList<OrdenServicio> lista) throws Exception{
        boolean guardado = false;
        File f = new File(directorio, nombArchOrden);
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

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
import java.util.Objects;

public class OrdenServicio implements Serializable {
    public static final String nombArchOrden = "Ordenes.ser";
    private Cliente cliente;
    private Tecnico tecnico;
    private LocalDate fechaServicio;
    private String placaVehiculo;
    private double totalOrden;
    private TipoVehiculo tipoVehiculo;
    private ArrayList<DetalleServicio> listaOrdeServicios;

    public OrdenServicio(){}

    //constructor ordenes
    public OrdenServicio(Cliente cliente, LocalDate fechaServicio, TipoVehiculo tipoVehiculo, String placaVehiculo){
        this.cliente = cliente;
        this.tipoVehiculo = tipoVehiculo;
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
        return DatosBase.getInstance().getListOrden();
    }

    public static ArrayList<OrdenServicio> cargarOrdenes(File directorio){
        ArrayList<OrdenServicio> lista = new ArrayList<>();
        File f = new File(directorio, nombArchOrden);
        if (f.exists()) {
            try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(f))) {
                lista = (ArrayList<OrdenServicio>) is.readObject();
            } catch (Exception e) {
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
            lista = obtenerOrdenes();
        }

        if (!f.exists()) {
            try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
                os.writeObject(lista);
                guardado = true;
            } catch (IOException e) {
                throw new Exception(e.getMessage());
            }
        } else guardado = true;
        return guardado;
    }

    public static boolean guardarLista(File directorio, ArrayList<OrdenServicio> lista) throws Exception{
        boolean guardado = false;
        File f = new File(directorio, nombArchOrden);
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f))) {
            os.writeObject(lista);
            guardado = true;
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
        return guardado;
    }

    // ===================== NUEVO: identidad para evitar duplicados =====================
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdenServicio)) return false;
        OrdenServicio that = (OrdenServicio) o;

        // Identificadores “naturales” disponibles
        String idCliente = null;
        try { idCliente = (cliente != null) ? cliente.getIdentificacion() : null; } catch (Exception ignored) {}

        String idTecnico = null;
        try { idTecnico = (tecnico != null) ? tecnico.getIdentificacion() : null; } catch (Exception ignored) {}

        return Objects.equals(fechaServicio, that.fechaServicio)
                && Objects.equals(placaVehiculo, that.placaVehiculo)
                && Double.compare(totalOrden, that.totalOrden) == 0
                && tipoVehiculo == that.tipoVehiculo
                && Objects.equals(idCliente, (that.cliente != null ? that.cliente.getIdentificacion() : null))
                && Objects.equals(idTecnico, (that.tecnico != null ? that.tecnico.getIdentificacion() : null));
    }

    @Override
    public int hashCode() {
        String idCliente = null;
        try { idCliente = (cliente != null) ? cliente.getIdentificacion() : null; } catch (Exception ignored) {}
        String idTecnico = null;
        try { idTecnico = (tecnico != null) ? tecnico.getIdentificacion() : null; } catch (Exception ignored) {}
        return Objects.hash(fechaServicio, placaVehiculo, totalOrden, tipoVehiculo, idCliente, idTecnico);
    }
    // ===================================================================================
}

package com.espol.tecnicentro.modelo;
import java.time.LocalDate;
import java.util.*;


public class OrdenServicio {
    private Cliente cliente;
    private Tecnico tecnico;
    private LocalDate fechaServicio;
    private String placaVehiculo;
    private double totalOrden;
    private TipoVehiculo tipoVehiculo;
    private ArrayList <DetalleServicio> servicios;


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
        this.servicios = servicios;
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
        return servicios;
    }


    public void setServicios(ArrayList<DetalleServicio> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String toString() {
        return "OrdenServicio [cliente=" + cliente + ", tecnico=" + tecnico + ", fechaServicio=" + fechaServicio
                + ", placaVehiculo=" + placaVehiculo + ", totalOrden=" + totalOrden + ", tipoVehiculo=" + tipoVehiculo
                + ", servicios=" + servicios + "]";
    }
    
    


}

package com.espol.tecnicentro.modelo;

import java.io.Serializable;

public class DetalleServicio implements Serializable {
    private int cantidad;
    private Servicio servicio;
    private double subtotal;


    public DetalleServicio(int cantidad, Servicio servicio, double subtotal) {
        this.cantidad = cantidad;
        this.servicio = servicio;
        this.subtotal = subtotal;
    }
    public DetalleServicio(int cantidad, Servicio servicio) {
        this.cantidad = cantidad;
        this.servicio = servicio;

    }

    public DetalleServicio(int cantidad, double subtotal){
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    

    public int getCantidad() {
        return cantidad;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public Servicio getServicio() {
        return servicio;
    }


    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }


    public double getSubtotal() {


        return  cantidad * servicio.getPrecio();
    }


    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }


    @Override
    public String toString() {
        return "DetalleServicio [cantidad=" + cantidad + ", servicio=" + servicio + ", subtotal=" + subtotal + "]";
    }

    

    
    
}

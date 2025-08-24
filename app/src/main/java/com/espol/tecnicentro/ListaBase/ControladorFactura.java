package com.espol.tecnicentro.ListaBase;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import com.espol.tecnicentro.modelo.*;

public class ControladorFactura extends DatosBase {
    
    public ControladorFactura(DatosBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public OrdenServicio clientePorId(String identificacion) {
        for (OrdenServicio clienteOrdenPorID : listOrden) {
            if (clienteOrdenPorID.getCliente().getIdentificacion().equals(identificacion.trim())) {
                return clienteOrdenPorID;
            }
        }
        return null;
    }

    public void generarFacturaEmpresa(OrdenServicio clientePorOrdenServicio, int anioBuscado, int mesBuscado) {
        boolean existeAnio = false;

        for (OrdenServicio orden : listOrden) {
            if (orden.getFechaServicio().getYear() == anioBuscado &&
                orden.getCliente().getIdentificacion().equals(clientePorOrdenServicio.getCliente().getIdentificacion())) {
                existeAnio = true;
                break;
            }
        }

        if (!existeAnio) {
            System.out.println("No existen órdenes para el año " + anioBuscado + " del cliente.");
            return;
        }

        boolean existeMes = false;
        Month mesPalabra = Month.of(mesBuscado);
        Locale locale = Locale.forLanguageTag("es-ES");
        String nombreMes = mesPalabra.getDisplayName(TextStyle.FULL, locale).toUpperCase();

        for (OrdenServicio orden : listOrden) {
            if (orden.getCliente().getIdentificacion().equals(clientePorOrdenServicio.getCliente().getIdentificacion())
                && orden.getFechaServicio().getYear() == anioBuscado
                && orden.getFechaServicio().getMonthValue() == mesBuscado) {
                existeMes = true;
                break;
            }
        }

        if (!existeMes) {
            System.out.println("No existen órdenes para el mes de " + nombreMes +
                " del año " + anioBuscado + " para el cliente.");
            return;
        }

        double sumaTotal = 0.0;
        for (OrdenServicio esaOrden : listOrden) {
            if (esaOrden.getCliente().getIdentificacion().equals(clientePorOrdenServicio.getCliente().getIdentificacion())) {
                String placa = esaOrden.getPlacaVehiculo();
                LocalDate fechaCompleta = esaOrden.getFechaServicio();
                int mes = fechaCompleta.getMonthValue();
                int dia = fechaCompleta.getDayOfMonth();
                TipoVehiculo tipoVehiculo = esaOrden.getTipoVehiculo();

                for (DetalleServicio eseServicio : esaOrden.getServicios()) {
                    String nombreServicio = eseServicio.getServicio().getNombre();
                    int cantidad = eseServicio.getCantidad();
                    double subtotal = eseServicio.getSubtotal();

                    String formato = "%-10s %-8s %-12s %-35s %8d %12.2f%n";
                    System.out.printf(formato, placa, dia + "-" + mes, tipoVehiculo, nombreServicio, cantidad, subtotal);
                }
                sumaTotal += esaOrden.getTotalOrden();
            }
        }
        System.out.println("Total a pagar: " + (sumaTotal + 50));
    }
}
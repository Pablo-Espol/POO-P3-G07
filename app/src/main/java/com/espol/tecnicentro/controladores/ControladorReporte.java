package com.espol.tecnicentro.controladores;

import java.time.LocalDate;
import java.util.*;
import com.espol.tecnicentro.modelo.*;

public class ControladorReporte extends ControladorBase {
    
    public ControladorReporte(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public void reporteIngresosPorServicio(int año, int mes) {
        ArrayList<Servicio> servicios = getListService();
        double[] totalServicio = new double[servicios.size()];

        for (OrdenServicio ordenes : listOrden) {
            LocalDate fecha = ordenes.getFechaServicio();

            if (fecha.getYear() == año && fecha.getMonthValue() == mes) {
                for (DetalleServicio detalles : ordenes.getServicios()) {
                    Servicio servicio = detalles.getServicio();

                    for (int i = 0; i < servicios.size(); i++) {
                        if (servicios.get(i).getCodigo().equals(servicio.getCodigo())) {
                            totalServicio[i] += detalles.getSubtotal();
                            break;
                        }
                    }
                }
            }
        }

        System.out.println("\n REPORTE DE INGRESOS PARA " + mes + " -- " + año);
        System.out.println("SERVICIO\t\t\tTOTAL");

        for (int i = 0; i < servicios.size(); i++) {
            if (totalServicio[i] > 0) {
                System.out.printf("%-30s\t%.2f\n", servicios.get(i).getNombre(), totalServicio[i]);
            }
        }
    }

    public Map<String, Double> reporteAtencionesporTecnico(int anio, int mes) {
        Map<String, Double> totalPorTecnico = new HashMap<>();

        for (OrdenServicio orden : listOrden) {
            LocalDate fecha = orden.getFechaServicio();
            int ordenAnio = fecha.getYear();
            int ordenMes = fecha.getMonthValue();

            if (ordenAnio == anio && ordenMes == mes) {
                String tecnicoNombre = orden.getTecnico().getNombre();
                double ordenTotal = orden.getTotalOrden();

                totalPorTecnico.put(tecnicoNombre, totalPorTecnico.getOrDefault(tecnicoNombre, 0.0) + ordenTotal);
            }
        }

        return totalPorTecnico;
    }
}
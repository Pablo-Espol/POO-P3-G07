package com.espol.tecnicentro.ListaBase;

import java.time.LocalDate;
import java.util.*;
import com.espol.tecnicentro.modelo.*;

public class ControladorReporte extends DatosBase {

    public ControladorReporte(DatosBase base) {
        // Copiamos las referencias de listas de la base inicializada
        this.listClient           = base.getListClient();
        this.listTecni            = base.getListTecni();
        this.listSuplier          = base.getListSuplier();
        this.listService          = base.getListService();
        this.listOrden            = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    /* ------------------------ Helpers ------------------------ */

    /** Verifica que la fecha pertenezca al año/mes dados (mes 1..12). */
    private boolean esDelMes(LocalDate fecha, int anio, int mes1a12) {
        return fecha != null && fecha.getYear() == anio && fecha.getMonthValue() == mes1a12;
    }

    /** Calcula el total de una orden: usa getTotalOrden(); si es <=0, suma subtotales de detalles. */
    private double totalDeOrden(OrdenServicio orden) {
        if (orden == null) return 0.0;

        // 1) Intentar usar el total de la orden
        try {
            double t = orden.getTotalOrden();
            if (t > 0) return t;
        } catch (Throwable ignored) { }

        // 2) Sumar subtotales de los detalles
        double suma = 0.0;
        try {
            List<DetalleServicio> dets = orden.getServicios();
            if (dets != null) {
                for (DetalleServicio d : dets) {
                    if (d == null) continue;
                    try {
                        suma += d.getSubtotal();  // <-- ÚNICA vía, acorde a tu modelo
                    } catch (Throwable ignored) { /* si no existe, queda en 0 */ }
                }
            }
        } catch (Throwable ignored) { }
        return Math.max(0, suma);
    }

    /** Obtiene el nombre del técnico de la orden, seguro contra nulos. */
    private String nombreTecnico(OrdenServicio orden) {
        try {
            Tecnico t = orden.getTecnico();
            if (t != null) {
                String n = t.getNombre();
                if (n != null && !n.isEmpty()) return n;
            }
        } catch (Throwable ignored) { }
        return "(Sin técnico)";
    }

    /* -------------------- Reportes públicos -------------------- */

    /** Total recaudado por TÉCNICO en (año, mes). */
    public Map<String, Double> reporteAtencionesporTecnico(int anio, int mes) {
        Map<String, Double> totalPorTecnico = new HashMap<>();
        if (mes < 1 || mes > 12 || listOrden == null) return totalPorTecnico;

        for (OrdenServicio orden : listOrden) {
            if (orden == null) continue;

            LocalDate f = null;
            try { f = orden.getFechaServicio(); } catch (Throwable ignored) { }
            if (!esDelMes(f, anio, mes)) continue;

            String tecnico = nombreTecnico(orden);
            double total   = totalDeOrden(orden);

            totalPorTecnico.put(tecnico, totalPorTecnico.getOrDefault(tecnico, 0.0) + total);
        }
        return totalPorTecnico;
    }

    /** Total recaudado por SERVICIO en (año, mes). Devuelve mapa servicio -> total. */
    public Map<String, Double> reporteIngresosPorServicio(int anio, int mes) {
        Map<String, Double> totalPorServicio = new LinkedHashMap<>();
        if (mes < 1 || mes > 12 || listOrden == null) return totalPorServicio;

        for (OrdenServicio orden : listOrden) {
            if (orden == null) continue;

            LocalDate f = null;
            try { f = orden.getFechaServicio(); } catch (Throwable ignored) { }
            if (!esDelMes(f, anio, mes)) continue;

            List<DetalleServicio> detalles = null;
            try { detalles = orden.getServicios(); } catch (Throwable ignored) { }
            if (detalles == null) continue;

            for (DetalleServicio d : detalles) {
                if (d == null) continue;

                String nombreServ = "(Servicio)";
                try {
                    Servicio s = d.getServicio();
                    if (s != null && s.getNombre() != null) nombreServ = s.getNombre();
                } catch (Throwable ignored) { }

                double sub = 0.0;
                try { sub = d.getSubtotal(); } catch (Throwable ignored) { }

                totalPorServicio.put(nombreServ, totalPorServicio.getOrDefault(nombreServ, 0.0) + sub);
            }
        }

        // Si no quieres mostrar servicios con 0, descomenta:
        // totalPorServicio.entrySet().removeIf(e -> e.getValue() == null || e.getValue() <= 0);

        return totalPorServicio;
    }
}
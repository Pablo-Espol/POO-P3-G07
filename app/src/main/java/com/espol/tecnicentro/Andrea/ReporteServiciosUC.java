package com.espol.tecnicentro.Andrea;

import com.espol.tecnicentro.Andrea.ReporteServicio;
import com.espol.tecnicentro.modelo.DetalleServicio;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.util.*;

public final class ReporteServiciosUC {
    private ReporteServiciosUC(){}

    /** anio: 2025, mes: 1..12 */
    public static List<ReporteServicio> generar(List<OrdenServicio> ordenes, int anio, int mes) {
        if (ordenes == null) ordenes = Collections.emptyList();

        Map<String, Double> sumaPorServicio = new HashMap<>();

        for (OrdenServicio o : ordenes) {
            if (o == null || o.getFechaServicio() == null) continue;
            if (o.getFechaServicio().getYear() != anio ||
                    o.getFechaServicio().getMonthValue() != mes) continue;

            List<DetalleServicio> dets = o.getServicios();
            if (dets == null) continue;

            for (DetalleServicio d : dets) {
                if (d == null || d.getServicio() == null) continue;

                String nombre = d.getServicio().getNombre();   // <- ajusta si tu getter se llama distinto
                double sub = d.getSubtotal();                  // cantidad * precio (según tu clase)
                sumaPorServicio.merge(nombre, sub, Double::sum);
            }
        }

        List<ReporteServicio> out = new ArrayList<>();
        for (Map.Entry<String, Double> e : sumaPorServicio.entrySet()) {
            out.add(new ReporteServicio(e.getKey(), e.getValue()));
        }
        out.sort((a,b) -> Double.compare(b.getTotal(), a.getTotal())); // mayor→menor
        return out;
    }
}
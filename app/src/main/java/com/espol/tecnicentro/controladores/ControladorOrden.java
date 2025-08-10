package com.espol.tecnicentro.controladores;

import java.time.LocalDate;
import java.util.*;
import com.espol.tecnicentro.modelo.*;

public class ControladorOrden extends ControladorBase {


    public ControladorOrden(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public void listaClientesEmpresariales(){

        for (Cliente cliente : listClient) {

                System.out.println("Cliente:"+ cliente.getNombre()+ " | ID(código):"+ cliente.getIdentificacion());
            }
        }
    

    public OrdenServicio agregarOrdenServicio(Cliente cliente, Tecnico tecnico, LocalDate fecha, 
                                            TipoVehiculo tipoVehiculo, String placaVehiculo, 
                                            ArrayList<DetalleServicio> detalles) {
        OrdenServicio nuevaOrden = new OrdenServicio(cliente, tecnico, fecha, placaVehiculo, 
                                                    calcularTotalOrden(detalles), tipoVehiculo, detalles);
        listOrden.add(nuevaOrden);
        return nuevaOrden;
    }

    public Tecnico seleccionarTecnicoAleatorio() {
        List<Tecnico> tecnicosDisponibles = new ArrayList<>();
        for (Tecnico tecnico : listTecni) {
            int ordenesAsignadas = 0;
            for (OrdenServicio orden : listOrden) {
                if (orden.getTecnico().getIdentificacion().equals(tecnico.getIdentificacion())) {
                    ordenesAsignadas++;
                }
            }
            if (ordenesAsignadas <= 2) {
                tecnicosDisponibles.add(tecnico);
            }
        }
        if (tecnicosDisponibles.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return tecnicosDisponibles.get(random.nextInt(tecnicosDisponibles.size()));
    }

    public Servicio servicioPorCodigo(String codigo) {
        for (Servicio cadaServicio : listService) {
            if (cadaServicio.getCodigo().trim().equalsIgnoreCase(codigo.trim())) {
                return cadaServicio;
            }
        }
        return null;
    }
}

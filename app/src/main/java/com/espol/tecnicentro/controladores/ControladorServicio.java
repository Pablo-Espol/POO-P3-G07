package com.espol.tecnicentro.controladores;

import java.util.Scanner;
import com.espol.tecnicentro.modelo.*;

public class ControladorServicio extends ControladorBase {
    private Scanner sc = new Scanner(System.in);
    
    public ControladorServicio(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public Servicio agregarServicio(String nombre, double precio) {
        Servicio ultimo = listService.get(listService.size() - 1);
        String code = ultimo.getCodigo();
        int codigo = Integer.parseInt(code);
        int nuevoCodigo = codigo + 1;
        String codeLista = String.format("%03d", nuevoCodigo);
        Servicio nuevoServicio = new Servicio(codeLista, nombre, precio);
        listService.add(nuevoServicio);
        return nuevoServicio;
    }

    public Servicio editarServicio(String codigo) {
        for (Servicio servicio : listService) {
            if (servicio.getCodigo().equals(codigo)) {
                double precioAntiguo = servicio.getPrecio();
                System.out.println("Ingrese el nuevo precio del servicio: ");
                double nuevoPrecio = sc.nextDouble();
                servicio.setPrecio(nuevoPrecio);
                //servicio.getListHistorialPrecios().add(new HistorialPrecios(precioAntiguo, nuevoPrecio));
                return servicio;
            }
        }
        return null;
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

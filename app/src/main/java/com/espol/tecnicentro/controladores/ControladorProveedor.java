package com.espol.tecnicentro.controladores;

import com.espol.tecnicentro.modelo.*;

public class ControladorProveedor extends ControladorBase {
    
    public ControladorProveedor(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public Proveedor agregarProveedor(String identificacion, String nombre, String telefono, String descripcion) {
        for (Proveedor proveedor : listSuplier) {
            if (proveedor.getIdentificacion().equals(identificacion)) {
                return proveedor; // Proveedor ya existe
            }
        }
        listSuplier.add(new Proveedor(identificacion, nombre, telefono, descripcion));
        return null; // Proveedor agregado exitosamente
    }
}
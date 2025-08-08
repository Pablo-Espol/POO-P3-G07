package com.espol.tecnicentro.controladores;

import com.espol.tecnicentro.modelo.*;

public class ControladorCliente extends ControladorBase {
    
    public ControladorCliente(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public Cliente agregarCliente(String identificacion, String nombre, String telefono, String direccion, TipoCliente tipoCliente) {
        for (Cliente cliente : listClient) {
            if (cliente.getIdentificacion().equals(identificacion)) {
                return cliente; // Cliente ya existe
            }
        }
        listClient.add(new Cliente(identificacion, nombre, telefono, direccion, tipoCliente));
        return null; // Cliente agregado exitosamente
    }
}


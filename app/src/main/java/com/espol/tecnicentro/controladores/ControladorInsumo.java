package com.espol.tecnicentro.controladores;

import java.time.LocalDate;
import com.espol.tecnicentro.modelo.*;

public class ControladorInsumo extends ControladorBase {
    
    public ControladorInsumo(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public Insumo registrarInsumo(String descripcion, Proveedor proveedor) {
        Insumo nuevoInsumo = new Insumo(descripcion, LocalDate.now());
        listInsumosFaltantes.add(nuevoInsumo);
        return nuevoInsumo;
    }
}

package com.espol.tecnicentro.controladores;

import java.util.*;
import com.espol.tecnicentro.modelo.*;

public class ControladorTecnico extends ControladorBase {
    
    public ControladorTecnico(ControladorBase base) {
        this.listClient = base.getListClient();
        this.listTecni = base.getListTecni();
        this.listSuplier = base.getListSuplier();
        this.listService = base.getListService();
        this.listOrden = base.getListOrden();
        this.listInsumosFaltantes = base.getListInsumosFaltantes();
    }

    public Tecnico agregarTecnico(String identificacion, String nombre, String telefono, String especialidad) {
        for (Tecnico tecnico : listTecni) {
            if (tecnico.getIdentificacion().equals(identificacion)) {
                return tecnico; // Técnico ya existe
            }
        }
        listTecni.add(new Tecnico(identificacion, nombre, telefono, especialidad));
        return null; // Técnico agregado exitosamente
    }

    public boolean eliminarTecnico(String identificacion, Scanner scanner) {
        for (int i = 0; i < listTecni.size(); i++) {
            Tecnico tecnico = listTecni.get(i);
            if (tecnico.getIdentificacion().equals(identificacion)) {
                System.out.println(tecnico);
                System.out.println("¿Estás seguro que deseas eliminar el técnico? (SI/NO)");
                String decision = scanner.nextLine().toUpperCase();

                if (decision.equals("SI")) {
                    listTecni.remove(i);
                    System.out.println("El técnico ha sido eliminado correctamente");
                    return true;
                } else {
                    System.out.println("El técnico no ha sido eliminado");
                    return false;
                }
            }
        }
        System.out.println("No se encontró un técnico con la identificación proporcionada.");
        return false;
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
}

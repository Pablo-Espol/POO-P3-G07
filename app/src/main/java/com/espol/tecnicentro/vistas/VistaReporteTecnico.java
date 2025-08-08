package com.espol.tecnicentro.vistas;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.espol.tecnicentro.controladores.ControladorReporte;
import com.espol.tecnicentro.modelo.OrdenServicio;

public class VistaReporteTecnico {

     ControladorReporte controlador;

    public VistaReporteTecnico(ControladorReporte controlador){
        this.controlador= controlador;
    }


    // Metodo reporte de atenciones por tecnico
    
    public void reporteAtencionesporTecnico(Scanner scanner){
        
        do{
            System.out.println("Ingrese el año que desea consultar: ");
            int anio = scanner.nextInt();
            
            int opcionMes;
            do{
                System.out.println("Ingrese el mes que desea consultar (ingrese un número del 1 - 12): ");
                int mess = scanner.nextInt();

                Map<String, Double> totalPorTecnico = new HashMap<>();
        
                for (OrdenServicio orden : controlador.getListOrden()){
            
                    LocalDate fecha = orden.getFechaServicio();
                    int ordenAnio = fecha.getYear();
                    int ordenMes = fecha.getMonthValue();

                    if (ordenAnio == anio && ordenMes == mess){
                        String tecnicoNombre = orden.getTecnico().getNombre();
                        double ordenTotal = orden.getTotalOrden();

                        totalPorTecnico.put(tecnicoNombre, totalPorTecnico.getOrDefault(tecnicoNombre,0.0)+ordenTotal);
                    }
                }
    
        if (totalPorTecnico.isEmpty()){
            System.out.println("No se encontraron reportes en ese periodo.");
        }else {
            System.out.println("\n Reporte de Atenciones por Tecnico ");
            System.out.printf("%-25s %10s\n", "Tecnico", "Total");
            for (Map.Entry<String, Double> entry : totalPorTecnico.entrySet()) {
                System.out.printf("%-25s $%9.2f\n", entry.getKey(), entry.getValue());
            }
        }
        
        //Regresar al menu principal
        System.out.println("\n¿Desea realizar otra consulta?");
        System.out.println("1. Consultar otro mes");
        System.out.println("2. Cambiar año");
        System.out.println("3. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        opcionMes = scanner.nextInt();

    if (opcionMes == 2){
        break;
       }
        } while (opcionMes == 1);

        if (opcionMes == 3) {
            break; // Sale del método y regresa al menú principal
        }
    } while (true);
}
}

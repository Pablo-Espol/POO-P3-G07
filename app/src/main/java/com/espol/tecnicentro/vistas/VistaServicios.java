package com.espol.tecnicentro.vistas;

import java.util.Scanner;

import com.espol.tecnicentro.controladores.ControladorServicio;
import com.espol.tecnicentro.modelo.HistorialPrecios;
import com.espol.tecnicentro.modelo.Servicio;

public class VistaServicios {

     ControladorServicio controlador;

    public VistaServicios(ControladorServicio controlador){
        this.controlador= controlador;
    }
    //Administrar servicios

    public void agregarServicio(Scanner scanner){
        System.out.println("Agregar Servicio ");
        scanner.nextLine();
        System.out.println("Ingrese el nombre del servicio:");
        String nombre= scanner.nextLine();
        System.out.println("Ingrese el precio del servicio:");
        double precio = scanner.nextDouble();
        controlador.agregarServicio(nombre, precio);
        System.out.println("El servicio fue agregado exitosamente");
        System.out.println("<-----LISTA DE SERVICIOS ACTUALIZADA----->");
        for (Servicio cadaServicio : controlador.getListService()) {
            System.out.println(cadaServicio.toString());
        }


    }

    public void editarServicio(Scanner scanner){
        System.out.println("Editar Servicio");
        scanner.nextLine();
        System.out.println("Ingrese el codigo del servicio a editar");
        String codigo = scanner.nextLine();
        Servicio servicio = controlador.editarServicio(codigo);

        if (servicio != null) {
            System.out.println("El servicio fue editado exitosamente");
            System.out.println("<-----LISTA DE SERVICIOS ACTUALIZADA----->");
            for (Servicio cadaServicio : controlador.getListService()) {
            System.out.println(cadaServicio.toString());
            }
            System.out.println("Historial de precios del servicio:");


        } else {
            System.out.println("El servicio no existe");
        }
        
    }

    public void administrarServicios(Scanner scanner){
        
        int opcion;

        do{
            System.out.println("1. Agregar Servicio");
            System.out.println("2. Editar Servicio");
            System.out.println("3. Lista de Servicios");
            System.out.println("4. Regresar al menu principal");
            System.out.println("Seleccione una opcion:");

            opcion= scanner.nextInt();
             
            switch (opcion) {
                case 1:
                agregarServicio(scanner);
                break;
                case 2:
                editarServicio(scanner);
                break;  
                case 3:
                System.out.println("<-----LISTA DE SERVICIOS------->");
                for (Servicio servicio : controlador.getListService()) {
                    System.out.println(servicio.toString());
                }
                break;
                case 4: 
                System.out.println("Saliendo del menu de servicios");}



          }  while (opcion != 4);  

    }
}

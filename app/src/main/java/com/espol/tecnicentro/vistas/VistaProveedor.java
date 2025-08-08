package com.espol.tecnicentro.vistas;

import java.util.Scanner;
import com.espol.tecnicentro.controladores.ControladorProveedor;
import com.espol.tecnicentro.modelo.Proveedor;

public class VistaProveedor {

    ControladorProveedor controlador;

    public VistaProveedor(ControladorProveedor controlador){
        this.controlador= controlador;
    }

//Metodo para Adminnistar proveedor
    //Agregar Proveedor
    public void agregarProveedor(Scanner scanner){
        System.out.print("Ingrese la identificacion del proveedor: ");
        scanner.nextLine(); 
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre del proveedor: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el telefono: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese la descripción: ");
        String descripcion = scanner.nextLine();
         

        Proveedor proveedor =controlador.agregarProveedor(id,nombre,telefono,descripcion);

        if (proveedor == null) {
            System.out.println("El proveedor se agregó exitosamente");
        } else {
            System.out.println("El proveedor ya existe");
        }
        
        System.out.println("<-----LISTA DE PROVEEDORES ACTUALIZADA----->");
        for (Proveedor cadaProveedor : controlador.getListSuplier()) {
            System.out.println(cadaProveedor.toString());
        }
    }

     public void administrarProveeddores(Scanner scanner){
        int opcion=0;

        do{
            System.out.println("1. Agregar Proveedor");
            System.out.println("2. Lista de Proveedores");
            System.out.println("3. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    agregarProveedor(scanner);
                    break;
                case 2: 
                    System.out.println("<-----LISTA DE PROVEEDORES------->");
                    for (Proveedor cadaProveedor : controlador.getListSuplier()) {
                        System.out.println(cadaProveedor.toString());
                    }
                    break;
                case 3:
                    System.out.println("Regresando al menu principal");
                    break;
                default:
                    System.out.println("Opcion no valida, intente nuevamente.");
            }
            
        }while (opcion != 3);
        
           

    }


}

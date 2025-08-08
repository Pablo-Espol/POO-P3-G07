package com.espol.tecnicentro.vistas;

import java.util.Scanner;


import com.espol.tecnicentro.controladores.ControladorCliente;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.TipoCliente;

public class VistaCliente {
    ControladorCliente controlador;

    public VistaCliente(ControladorCliente controlador){
        this.controlador= controlador;
    }

    //Metodo para agregar cliente

     public void agregarCliente(Scanner scanner){
              System.out.print("Ingrese la identificacion del cliente: ");
              scanner.nextLine(); //limpiar buffer
              String id = scanner.nextLine();
              System.out.print("Ingrese el nombre del cliente: ");
              String nombre = scanner.nextLine();
              System.out.print("Ingrese el telefono: ");
              String telefono = scanner.nextLine();
              System.out.print("Ingrese la dirección: ");
              String direccion = scanner.nextLine();

//Asegurando que el tipo cliente sea válido
            TipoCliente tipoCliente = null;
            while (tipoCliente == null) {
                System.out.print("Ingrese el tipo de cliente (PERSONAL o EMPRESARIAL): ");
                String tipoTextoc = scanner.nextLine().toUpperCase(); 

                for (TipoCliente tipo : TipoCliente.values()) {// Recorre los valores del enum guardados en una lista
                    if (tipo.name().equals(tipoTextoc)) { // compara el nombre del enum con el texto ingresado y si coincide le asigna
                        tipoCliente = tipo;
                        break;
                    }
                }
                if (tipoCliente == null) {
                    System.out.println("Tipo inválido. Intente nuevamente.");
                }
            }

            //return cliente o null
           Cliente cliente =  controlador.agregarCliente(id,nombre,telefono,direccion,tipoCliente);
            
            if (cliente == null) {
                System.out.println("El cliente se agregó exitosamente");
            }
            else{
                System.out.println("El cliente ya existe");
            }
            System.out.println("<-----LISTA DE CLIENTES ACTUALIZADA----->");
            for (Cliente cadaCliente : controlador.getListClient()) {
                System.out.println(cadaCliente.toString());
            }
    }

    public void administrarClientes(Scanner scanner){
        int opcion=0;

        do{
            System.out.println("1. Agregar Cliente");
            System.out.println("2. Lista de clientes");
            System.out.println("3. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    agregarCliente(scanner);
                    break;
                case 2: 
                    System.out.println("<-----LISTA DE CLIENTES------->");
                    for (Cliente cadaCliente : controlador.getListClient()){
                        System.out.println(cadaCliente.toString());
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del menu de clientes");
                    break;
                default:
                    System.out.println("Opcion no valida, intente nuevamente.");
            }
            
        }while (opcion != 3);
        
    }
}

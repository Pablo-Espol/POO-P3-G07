package com.espol.tecnicentro.vistas;

import java.util.Scanner;

import com.espol.tecnicentro.controladores.ControladorTecnico;
import com.espol.tecnicentro.modelo.Tecnico;

public class VistaTecnicos {
    
   ControladorTecnico controlador;

    public VistaTecnicos(ControladorTecnico controlador){
        this.controlador= controlador;
    }
    
    //Metodo para administrar tecnicos
    public void agregarTecnico(Scanner scanner){
        System.out.println("Agregar Tecnico");
        scanner.nextLine();
        System.out.println("Ingrese la identificacion del tecnico");
        String id= scanner.nextLine();
        System.out.println("Ingrese el nombre de Tecnico");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese el telefono del tecnico");
        String telefono= scanner.nextLine();
        System.out.println("Ingrese la especialidad del tecnico");
        String especialidad = scanner.nextLine();

        Tecnico tecnico = controlador.agregarTecnico(id, nombre, telefono,especialidad);
        
        if (tecnico == null){
            System.out.println("El tecnico fue agregado exitosamente");

        }else{
            System.out.println("El tecnico ya existe");
        }
        System.out.println("<-----LISTA DE TECNICOS ACTUALIZADA----->");
        for (Tecnico cadaTecnico : controlador.getListTecni()) {
            System.out.println(cadaTecnico.toString());
        }
        System.out.println();
    
    }
    public void eliminarTecnico(Scanner scanner) {
    System.out.println("Eliminar Técnico");
    scanner.nextLine(); // limpiar buffer
    System.out.print("Ingrese la identificación del técnico a eliminar: ");
    String id = scanner.nextLine();

    controlador.eliminarTecnico(id, scanner);
}

     public void administrarTecnico(Scanner scanner){
        int opcion=0;

        do{
            System.out.println("1. Agregar Tecnico");
            System.out.println("2. Eliminar Tecnico");
            System.out.println("3. Lista de Tecnicos");
            System.out.println("4. Regresar al menu principal");
            System.out.print("Seleccione una opcion: ");

            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    agregarTecnico(scanner);
                    break;
                case 2:
                    eliminarTecnico(scanner);
                    break;
                case 3:
                    System.out.println("<-----LISTA DE TÉCNICOS------->");
                    for (Tecnico cadaTecnico : controlador.getListTecni()) {
                        System.out.println(cadaTecnico.toString());
                    }
                    break;
                case 4: 
                    System.out.println("Saliendo del menu de servicios");
                default:
                    System.out.println("Opcion no valida, intente nuevamente.");
            }
            
        }while (opcion != 4);
             

    }

}

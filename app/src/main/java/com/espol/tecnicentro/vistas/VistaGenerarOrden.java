package com.espol.tecnicentro.vistas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.espol.tecnicentro.controladores.ControladorOrden;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.DetalleServicio;
import com.espol.tecnicentro.modelo.Servicio;
import com.espol.tecnicentro.modelo.Tecnico;
import com.espol.tecnicentro.modelo.TipoVehiculo;

public class VistaGenerarOrden {
    ArrayList<DetalleServicio> ordenACalcular;

    ControladorOrden controlador;

    VistaTecnicos vistaTecnicos;


    public VistaGenerarOrden(ControladorOrden controlador, VistaTecnicos vistaTecnicos){
        this.controlador = controlador;
        this.vistaTecnicos=  vistaTecnicos;
    }
//administrar Orden de servicios

    //Genera la orden
    protected void generarOrden(Scanner scanner){
        ordenACalcular = new ArrayList<>();

        System.out.println("------------Lista de Clientes y Códigos------------");
        controlador.listaClientesEmpresariales();

        scanner.nextLine(); //limpia buffer
        System.out.print("Ingrese ID del Cliente:");
        String id= scanner.nextLine();

        Cliente clienteSeleccionado = null;
        for (Cliente cliente : controlador.getListClient()){
            if (cliente.getIdentificacion().equals(id)){
                clienteSeleccionado = cliente;
                break;
            }
        }

        if (clienteSeleccionado == null){
            System.out.println("Cliente no encontrado. Operacion cancelada.");
            return;
        }

        System.out.print("Ingrese la fecha de la orden del Servicio dd/MM/yyyy: ");
        String fechaServicio= scanner.nextLine();  //fecha en formato texto
       
        LocalDate fechaDate;
        try {
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fechaDate = LocalDate.parse(fechaServicio.trim(),formato);
            
        } catch (Exception e){
            System.out.println("Formato de fehca invalido. Use dd/MM/yyyy. Operacion cancelada.");
            return;
        }

         TipoVehiculo tipoVehiculo = null;
        while(tipoVehiculo == null){
            System.out.println("Ingrese el tipo de vehiculo:"+ 
            "\n1.Automovil"+  
            "\n2.Motocicleta " +
            "\n3.Bus");
            System.out.print("Ingrese el tipo:");
            int tipoV = scanner.nextInt();
            scanner.nextLine();
            switch (tipoV) {
                case 1 :tipoVehiculo = TipoVehiculo.VEHICULO;
                break;
                case 2: tipoVehiculo = TipoVehiculo.MOTOCICLETA;
                break;
                case 3: tipoVehiculo = TipoVehiculo.BUS;
                break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente");
            }
        }
        
        System.out.print("Ingrese Placa de vehiculo: ");
        String placaVehiculo = scanner.nextLine(); // No se donde enviar esto, esta sin usar

        System.out.println("--------LISTA DE SERVICIOS--------");
        for (Servicio cadaServicio : controlador.getListService()) {
        System.out.println("Código: " + cadaServicio.getCodigo() + ", Precio: " + cadaServicio.getPrecio());        
    }

        System.out.print("Ingrese el código del servicio: ");

        String codigoString = scanner.nextLine();

        int cantidad;
        while (!codigoString.equals("-1")) {

            System.out.print("Cantidad: ");
            cantidad = scanner.nextInt();

            Servicio servicio = controlador.servicioPorCodigo(codigoString);
            if (servicio == null) {
                    System.out.println("Servicio no encontrado. Verifique el código ingresado.");
                    return;
            }


            double subtotal = cantidad* servicio.getPrecio();

            ordenACalcular.add(new DetalleServicio(cantidad,servicio,subtotal));

            //evitar bucle infinito
            System.out.print("Ingrese código del servicio: ");
            scanner.nextLine(); // limpia buffer
            codigoString = scanner.nextLine();
        }
            //AQUI VA CODIGO QUE COMUNICA CON EL CONTROLADOR
            System.out.println("Total a pagar:");
            System.out.println(controlador.calcularTotalOrden(ordenACalcular));

            //Tecnico

            Tecnico tecnicoAletorio= controlador.seleccionarTecnicoAleatorio();
            if (tecnicoAletorio == null) {
                System.out.println("No hay técnicos disponibles. Debe agregar un nuevo técnico.");
                vistaTecnicos.administrarTecnico(scanner);
                tecnicoAletorio = controlador.seleccionarTecnicoAleatorio();
                if (tecnicoAletorio == null) {
                    System.out.println("No se pudo agregar un técnico. Operación cancelada.");
                    return;
                }
            }
            //Guardando datos en sistema
            controlador.agregarOrdenServicio(clienteSeleccionado,tecnicoAletorio,fechaDate,tipoVehiculo,placaVehiculo,ordenACalcular);
            System.out.println("Se agregó la orden al sistema exitosamente!");

    }
    public void generarOrdenServicio(Scanner scanner){
        int opcion;
        do {    
            System.out.println("1. Generar Orden");
            System.out.println("2. Regresar al menu principal");
            System.out.print("Ingrese una opción: ");

            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    generarOrden(scanner);
                    break;
            
                case 2:
                    System.out.println("Volviendo al menu principal");
                    break;
            }
            
        } while (opcion !=2);

    }
}

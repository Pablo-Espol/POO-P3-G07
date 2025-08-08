package com.espol.tecnicentro.vistas;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

import com.espol.tecnicentro.controladores.ControladorFactura;
import com.espol.tecnicentro.modelo.OrdenServicio;
import com.espol.tecnicentro.modelo.TipoCliente;

public class VistaFacturaEmpresa {
     ControladorFactura controlador;

    public VistaFacturaEmpresa(ControladorFactura controlador){
        this.controlador= controlador;
    }

    //Metodo generar facturas de empresas

    protected void generarFacturasEmpresas(Scanner scanner){

        System.out.print("Ingrese el codigo de la Empresa (ID): ");
        scanner.nextLine();
        String codigoEmpresa = scanner.nextLine();

        OrdenServicio clienteOrdenPorID = controlador.clientePorId(codigoEmpresa);  //retorna el Cliente por el ID
        while (clienteOrdenPorID == null) {
            System.out.println("ID invalida, no existe en la base de Datos");
            System.out.print("Ingrese el codigo de la Empresa (ID):");
            codigoEmpresa = scanner.nextLine();
            clienteOrdenPorID = controlador.clientePorId(codigoEmpresa);
        }
        if (clienteOrdenPorID.getCliente().getTipoCliente().equals(TipoCliente.EMPRESARIAL)) {
            System.out.print("Ingrese el año:");
            String ano= scanner.nextLine();
            int anoNumero = Integer.parseInt(ano);
            System.out.print("Ingrese el mes: ");
            
            String mes = scanner.nextLine();
            int mesNumero = Integer.parseInt(mes);
            Month mesPalabra = Month.of(mesNumero); //Mes en Ingles
            Locale locale = Locale.forLanguageTag("es-ES"); 
            String nombreMes = mesPalabra.getDisplayName(TextStyle.FULL, locale).toUpperCase(); //Mes en español transformado

            System.out.println("Empresa: " + clienteOrdenPorID.getCliente().getNombre());
            System.out.println("Perido de facturación: " + nombreMes + "-"+ ano );
            System.out.println("Detalle de servicios:");
            System.out.printf("%-10s %-8s %-12s %-35s %-8s %12s%n",
    "#Placa", "Fecha", "Tipo", "Servicio", "Cantidad", "Total");

            controlador.generarFacturaEmpresa(clienteOrdenPorID,anoNumero,mesNumero);
        }else{
            System.out.println("Cliente empresarial no encontrado!. Asegurese que el cliente sea Empresarial");
        }

    
    }

    //Mini Menu

    public void facturasParaEmpresas(Scanner scanner){
    int opcion;
        do {    
            System.out.println("1. Generar Factura");
            System.out.println("2. Regresar al menu principal");
            System.out.print("Ingrese una opción: ");

            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    generarFacturasEmpresas(scanner);
                    break;
            
                case 2:
                    System.out.println("Volviendo al menu principal");
                    break;
            }
            
        } while (opcion !=2);

    }

    
}

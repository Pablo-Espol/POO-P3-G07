package com.espol.tecnicentro.vistas;

import java.util.Scanner;

//import javax.naming.ldap.ControlFactory;

import com.espol.tecnicentro.controladores.ControladorReporte;

public class VistaReporteServicio {

    ControladorReporte controlador;

    public VistaReporteServicio(ControladorReporte controlador){
        this.controlador= controlador;
    }


//metodos para reporte de Ingresos por servicio
    public void reporteIngresosPorServicio(Scanner scanner){
        System.out.println("Ingrese el año del servicio a consultar: ");
        int año= scanner.nextInt();
        scanner.nextLine();
        System.out.println("Ingrese el mes para el reporte 1--12: ");
        int mes= scanner.nextInt();
        scanner.nextLine();

        controlador.reporteIngresosPorServicio(año,mes);

    }
}

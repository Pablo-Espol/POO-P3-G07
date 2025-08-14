package com.espol.tecnicentro.controladores;

import java.util.*;
import com.espol.tecnicentro.modelo.*;
import java.time.LocalDate;

public class ControladorBase {
    protected ArrayList<Cliente> listClient;
    protected ArrayList<Tecnico> listTecni;
    protected ArrayList<Proveedor> listSuplier;
    protected ArrayList<Servicio> listService;
    protected ArrayList<OrdenServicio> listOrden;
    protected ArrayList<Insumo> listInsumosFaltantes;

    private static ControladorBase instance;

    public ControladorBase() {
        listClient = new ArrayList<>();
        listTecni = new ArrayList<>();
        listSuplier = new ArrayList<>();
        listService = new ArrayList<>();
        listOrden = new ArrayList<>();
        listInsumosFaltantes = new ArrayList<>();
        inicializarApp();
    }

    public static synchronized ControladorBase getInstance() {
        if (instance == null) {
            instance = new ControladorBase();
        }
        return instance;
    }

    // Getters y setters
    public ArrayList<Cliente> getListClient() { return listClient; }
    public ArrayList<Tecnico> getListTecni() { return listTecni; }
    public ArrayList<Proveedor> getListSuplier() { return listSuplier; }
    public ArrayList<Servicio> getListService() { return listService; }
    public ArrayList<OrdenServicio> getListOrden() { return listOrden; }
    public ArrayList<Insumo> getListInsumosFaltantes() { return listInsumosFaltantes; }

    public void setListClient(ArrayList<Cliente> listClient) { this.listClient = listClient; }
    public void setListTecni(ArrayList<Tecnico> listTecni) { this.listTecni = listTecni; }
    public void setListSuplier(ArrayList<Proveedor> listSuplier) { this.listSuplier = listSuplier; }
    public void setListService(ArrayList<Servicio> listService) { this.listService = listService; }
    public void setListOrden(ArrayList<OrdenServicio> listOrden) { this.listOrden = listOrden; }
    public void setListInsumosFaltantes(ArrayList<Insumo> listInsumosFaltantes) { this.listInsumosFaltantes = listInsumosFaltantes; }

    // Calcula total de la orden usando subtotal dinámico de cada DetalleServicio
    public double calcularTotalOrden(ArrayList<DetalleServicio> detalles) {
        double total = 0.0;
        for (DetalleServicio detalle : detalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }

    // Inicialización de datos
    public void inicializarApp() {
        // Clientes
        listClient.add(new Cliente("0988514745", "Pablo Zambrano", "0998997700", "Guasmo", TipoCliente.PERSONAL));
        listClient.add(new Cliente("0947254193", "Jose Molina", "0909852147", "Luis Urdeneta y Cordova", TipoCliente.PERSONAL));
        listClient.add(new Cliente("0900224455", "Pedro Alvarado", "0999910741", "9 de Octubre y Boyaca", TipoCliente.EMPRESARIAL));
        listClient.add(new Cliente("0999007845", "Miguel Rodriguez", "0910203040", "Rumichaca y Aguirre", TipoCliente.EMPRESARIAL));

        // Técnicos
        listTecni.add(new Tecnico("0987654321", "Carlos Perez", "0998765432", "Mecanica"));
        listTecni.add(new Tecnico("0987654322", "Ana Gomez", "0998765433", "Electronica"));

        // Proveedores
        listSuplier.add(new Proveedor("0987654323", "Proveedor A", "0998765434", "Suministros de herramientas"));
        listSuplier.add(new Proveedor("0987654324", "Proveedor B", "0998765435", "Repuestos de computadoras"));

        // Servicios
        listService.add(new Servicio("001", "Reparación de motores", 90.0));
        listService.add(new Servicio("002", "Instalación de software", 80.0));
        listService.add(new Servicio("003", "Mantenimiento preventivo", 40.0));
        listService.add(new Servicio("004", "Balanceo y alineación", 70.0));
        listService.add(new Servicio("005", "Cambio de neumaticos", 60.0));
        listService.add(new Servicio("006", "Cambio de aceite", 30.0));

        // Detalle de órdenes sin subtotal fijo
        ArrayList<DetalleServicio> listDetalle1 = new ArrayList<>();
        listDetalle1.add(new DetalleServicio(2, listService.get(0))); // 2*90=180
        listDetalle1.add(new DetalleServicio(1, listService.get(1))); // 1*80=80

        ArrayList<DetalleServicio> listDetalle2 = new ArrayList<>();
        listDetalle2.add(new DetalleServicio(3, listService.get(2))); // 3*40=120
        listDetalle2.add(new DetalleServicio(4, listService.get(3))); // 4*70=280

        ArrayList<DetalleServicio> listDetalle3 = new ArrayList<>();
        listDetalle3.add(new DetalleServicio(5, listService.get(4))); // 5*60=300
        listDetalle3.add(new DetalleServicio(1, listService.get(5))); // 1*30=30

        ArrayList<DetalleServicio> listDetalle4 = new ArrayList<>();
        listDetalle4.add(new DetalleServicio(3, listService.get(1))); // 3*80=240
        listDetalle4.add(new DetalleServicio(1, listService.get(2))); // 1*40=40

        // Órdenes con subtotal calculado correctamente
        listOrden.add(new OrdenServicio(listClient.get(0), listTecni.get(0),
                LocalDate.of(2025, 6, 10), "ABB785",
                calcularTotalOrden(listDetalle1), TipoVehiculo.BUS, listDetalle1));

        listOrden.add(new OrdenServicio(listClient.get(1), listTecni.get(1),
                LocalDate.of(2025, 4, 25), "ABB786",
                calcularTotalOrden(listDetalle3), TipoVehiculo.MOTOCICLETA, listDetalle3));

        listOrden.add(new OrdenServicio(listClient.get(2), listTecni.get(1),
                LocalDate.of(2025, 3, 1), "ABB787",
                calcularTotalOrden(listDetalle2), TipoVehiculo.VEHICULO, listDetalle2));

        listOrden.add(new OrdenServicio(listClient.get(3), listTecni.get(1),
                LocalDate.of(2024, 12, 25), "ABB788",
                calcularTotalOrden(listDetalle4), TipoVehiculo.VEHICULO, listDetalle4));
    }
}

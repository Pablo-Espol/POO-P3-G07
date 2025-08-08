package com.espol.tecnicentro.controladores;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import com.espol.tecnicentro.modelo.*;

public class ControlTecno {
    private ArrayList<Cliente> listClient;
    private ArrayList<Tecnico> listTecni;
    private ArrayList<Proveedor> listSuplier;
    private ArrayList<Servicio> listService;
    private ArrayList<OrdenServicio> listOrden;
    private ArrayList<Insumo> listInsumosFaltantes;
    Scanner sc =new Scanner(System.in);

    public ControlTecno() {
        listClient=new ArrayList<>();
        listTecni= new ArrayList<>();
        listSuplier= new ArrayList<>();
        listService= new ArrayList<>();
        listOrden= new ArrayList<>();    
        listInsumosFaltantes= new ArrayList<>();
    }
    public ArrayList<Cliente> getListClient() {
        return listClient;
    }

    public void setListClient(ArrayList<Cliente> listClient) {
        this.listClient = listClient;
    }
    public ArrayList<Tecnico> getListTecni() {
        return listTecni;
    } 
    public void setListTecni(ArrayList<Tecnico> listTecni) {
        this.listTecni = listTecni;
    }

    public ArrayList<Proveedor> getListSuplier() {
        return listSuplier;
    }
    public void setListSuplier(ArrayList<Proveedor> listSuplier) {
        this.listSuplier = listSuplier;
    }
    public ArrayList<Servicio> getListService() {
        return listService;
    }
    public void setListService(ArrayList<Servicio> listService) {
        this.listService = listService;
    }
    public ArrayList<OrdenServicio> getListOrden() {
        return listOrden;
    }
    public void setListOrden(ArrayList<OrdenServicio> listOrden) {
        this.listOrden = listOrden;
    }
    public ArrayList<Insumo> getListInsumosFaltantes() {
        return listInsumosFaltantes;
    }
    public void setListInsumosFaltantes(ArrayList<Insumo> listInsumosFaltantes) {
        this.listInsumosFaltantes = listInsumosFaltantes;
    }

       //Creación metodo inicializarApp()


    public void inicializarApp(){
        //Inicializa con los  clientes
        listClient.add(new Cliente("0988514745", "Pablo Zambrano", "0998997700", "Guasmo", TipoCliente.PERSONAL));
        listClient.add(new Cliente("0947254193", "Jose Molina", "0909852147", "Luis Urdeneta y Cordova", TipoCliente.PERSONAL));
        listClient.add(new Cliente("0900224455", "Pedro Alvarado", "0999910741", "9 de Octubre y Boyaca", TipoCliente.EMPRESARIAL));
        listClient.add(new Cliente("0999007845", "Miguel Rodriguez", "0910203040", "Rumichaca y Aguirre", TipoCliente.EMPRESARIAL));

        //Inicializa con los  tecnicos
        listTecni.add(new Tecnico("0987654321", "Carlos Perez", "0998765432", "Mecanica"));
        listTecni.add(new Tecnico("0987654322", "Ana Gomez", "0998765433", "Electronica"));
        

         //Inicializa con los  proveedores
        listSuplier.add(new Proveedor("0987654323", "Proveedor A", "0998765434", "Suministros de herramientas"));
        listSuplier.add(new Proveedor("0987654324", "Proveedor B", "0998765435", "Repuestos de computadoras"));

        //Inicializa con los servicios
        listService.add(new Servicio("001", "Reparación de motores", 90.0));
        listService.add(new Servicio("002", "Instalación de software", 80.0));
        listService.add(new Servicio("003", "Mantenimiento preventivo", 40.0));
        listService.add(new Servicio("004", "Balanceo y alineación", 70.0));
        listService.add(new Servicio("005", "Cambio de neumaticos", 60.0));     
        listService.add(new Servicio("006", "Cambio de aceite", 30.0));

        //Creando primero en detalles servicios para asignar los servicios a las ordenes de servicio ya que pide minimo 2 y 2 x tecnico
       ArrayList<DetalleServicio> listDetalle1= new ArrayList<>();
       listDetalle1.add(new DetalleServicio(2, listService.get(0), listService.get(0).getPrecio()*2)); // 2 * 90.0
       listDetalle1.add(new DetalleServicio(1, listService.get(1), listService.get(1).getPrecio())); // 1 * 80.0
       ArrayList<DetalleServicio> listDetalle2= new ArrayList<>();
       listDetalle2.add(new DetalleServicio(3, listService.get(2), listService.get(2).getPrecio()*3)); // 3 * 40.0
       listDetalle2.add(new DetalleServicio(4, listService.get(3), listService.get(3).getPrecio()*4)); // 4 * 70.0
       ArrayList<DetalleServicio> listDetalle3= new ArrayList<>();
       listDetalle3.add(new DetalleServicio(5, listService.get(4), listService.get(4).getPrecio()*5)); // 5 * 60.0
       listDetalle3.add(new DetalleServicio(1, listService.get(5), listService.get(5).getPrecio()*1)); // 1 * 30.0
       ArrayList<DetalleServicio> listDetalles4 = new ArrayList<>();
       listDetalles4.add(new DetalleServicio(3, listService.get(1), listService.get(1).getPrecio() * 3));//3*80
       listDetalles4.add(new DetalleServicio(1, listService.get(2), listService.get(2).getPrecio()));//1*80
        
       //Inicializa con las ordenes de servicio -- yyy,mmm,ddd
       listOrden.add(new OrdenServicio(listClient.get(0), listTecni.get(0), LocalDate.of(2025,6,10), "ABB785", calcularTotalOrden(listDetalles4) ,TipoVehiculo.BUS,listDetalle1));
       listOrden.add(new OrdenServicio(listClient.get(1), listTecni.get(1), LocalDate.of(2025,4,25), "ABB786", calcularTotalOrden(listDetalle3) ,TipoVehiculo.MOTOCICLETA,listDetalle3));
       listOrden.add(new OrdenServicio(listClient.get(2), listTecni.get(1), LocalDate.of(2025,3,1), "ABB787", calcularTotalOrden(listDetalle2) ,TipoVehiculo.VEHICULO,listDetalle2));
       listOrden.add(new OrdenServicio(listClient.get(3), listTecni.get(1), LocalDate.of(2024,12,25), "ABB788", calcularTotalOrden(listDetalles4) ,TipoVehiculo.VEHICULO,listDetalles4));

    }


    
    
//METODOS PARA EL FUNCIONAMIENTO DEL PROGRAMA ------ NO TOCAR, SIIII FUNCIONA!!!!!!


    //Metodos para administrar clientes
     public  Cliente agregarCliente(String identificacion,String nombre,String telefono,String direccion,TipoCliente tipoCliente){
        for (Cliente cliente: listClient){
            if(cliente.getIdentificacion().equals(identificacion)){    
                return cliente;// retorna esto y sale el metodo
            }
        }
        listClient.add(new Cliente(identificacion,nombre, telefono,direccion,tipoCliente));
        return null;//todo bien no necesito enviar algún mensaje
        
}

    //Metodos para administrar proveedores 

    public Proveedor agregarProveedor(String identificacion,String nombre,String telefono,String descripcion){
        for (Proveedor proveedor: listSuplier){
            if(proveedor.getIdentificacion().equals(identificacion)){    
                return proveedor;// retorna esto y sale el metodo
            }
        }
        listSuplier.add(new Proveedor(identificacion,nombre, telefono,descripcion));
        return null;//todo bien no necesito enviar algún mensaje
        
        
    }

    //Metodos para administrar tecnicos
    public Tecnico agregartTecnico(String identificacion,String nombre, String telefono,String especialidad){
        for (Tecnico tecnico:listTecni){
            if (tecnico.getIdentificacion().equals(identificacion)){
                return tecnico; // retorna esto y sale el metodo
            }
        }
        listTecni.add(new Tecnico(identificacion, nombre, telefono,especialidad));
        return null;
    }
//Metodo para Eliminar un Tecnico de la Lista
    public boolean eliminarTecnico(String identificacion, Scanner scanner){
        for (int i=0;i< listTecni.size();i++){
            Tecnico tecnico = listTecni.get(i);
            if (tecnico.getIdentificacion().equals(identificacion)){
                System.out.println(tecnico);
                System.out.println("Estas seguro que deseas eliminar el tecnico (SI/NO)");
                String desicion= scanner.nextLine().toUpperCase();

                String opcion ="SI";
                if (desicion.equals(opcion)){
                    listTecni.remove(i);
                    System.out.println("El tecnico ha sido eliminado correctamente");
                    return true; //El técnico fue eliminado 
                } else {
                    System.out.println("El tecnico no ha sido eliminado");
                    return false; // El técnico no fue eliminado


                }


            }
        }
        System.out.println("No se encontró un técnico con la identificación proporcionada." );
        return false; 

        
    }
   
    //Metodos para administrar servicios
    public Servicio agregarServicio(String nombre, double precio){
        Servicio ultimo = listService.get(listService.size()-1);
        String code= ultimo.getCodigo();
        int coidgo= Integer.parseInt(code);
        int nuevoCodigo = coidgo + 1;
        String codeLista= String.format("%03d", nuevoCodigo);
        Servicio nuevoServicio= new Servicio( codeLista, nombre, precio);
        listService.add(nuevoServicio);
        return null;

    }

    public Servicio editarServicio(String codigo){
        for (Servicio servicio: listService){
            if(servicio.getCodigo().equals(codigo)){
                double precioAntiguo= servicio.getPrecio();
                System.out.println("Ingrese el nuevo precio del servicio: ");
                double nuevoPrecio = sc.nextDouble();
                servicio.setPrecio(nuevoPrecio);
                //servicio.getListHistorialPrecios().add(new HistorialPrecios(precioAntiguo, nuevoPrecio));
                
                return servicio; 
            }
        }
        return null;
    }

    public void mostrarHistorialPreciosDeTodos() {
    

    System.out.println("Historial de precios por servicio:");
/*
    for (Servicio servicio : listService) {
        System.out.println("Servicio: " + servicio.getNombre() + " (Código: " + servicio.getCodigo() + ")");
        if (servicio.getListHistorialPrecios().isEmpty()) {
            System.out.println("  No hay historial de precios para este servicio.");
        } else {
            for (HistorialPrecios h : servicio.getListHistorialPrecios()) {
                System.out.println("  Precio anterior: " + h.getPrecioAntiguo() + ", Nuevo precio: " + h.getPrecioNuevo() + ", Fecha: " + h.getFechaCambio());
            }
        }
    }
*/

}

//Metodo para calcular el total de una orden de servicio
public double calcularTotalOrden(ArrayList<DetalleServicio> detalles) {
    double total = 0.0;
    for (DetalleServicio detalle : detalles) {
        total += detalle.getSubtotal();
    }
    return total;

}

//metodos para generar Orden de Servicio
  

    //metodo para indexar servicio

    public Servicio servicioPorCodigo(String codig){
            for (Servicio cadaServicio : listService) {
                if (cadaServicio.getCodigo().trim().equalsIgnoreCase(codig.trim())) {
            return cadaServicio;  
        }
    }
    return null;
    }

    

    // Selecciona un técnico aleatorio que tenga menos de 2 órdenes asignadas
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


    //metodo para agregar la orden de Servicio al sistema
    public OrdenServicio listaOrdenEnSistema(Cliente cliente, Tecnico tecnico, LocalDate fecha, TipoVehiculo tipoVehiculo,String placaVehiculo, ArrayList<DetalleServicio> detalles) {
        OrdenServicio nuevaOrden = new OrdenServicio(cliente, tecnico, fecha, placaVehiculo, calcularTotalOrden(detalles), tipoVehiculo, detalles);
        listOrden.add(nuevaOrden);
        return nuevaOrden;
    }


    //METODO REGISTRO DE INSUMOOSS
    public Insumo registroInsumo(String descripcion, Proveedor proveedor){
        Insumo nuevoInsumo = new Insumo(descripcion, LocalDate.now());
        listInsumosFaltantes.add(nuevoInsumo);
        System.out.println("Se ha registrado el insumo faltante");

        return nuevoInsumo;

    }

    //Metodos para generar factura
    //cliente por ID
    public OrdenServicio clientePorId(String identificacion){
        for (OrdenServicio clienteOrdenPorID : listOrden) {
            if (clienteOrdenPorID.getCliente().getIdentificacion().equals(identificacion.trim())) {
                return clienteOrdenPorID;
            }
        }
        return null; //si no encuentra la identificacion del cliente
    }

 public void panelDeFacturas(OrdenServicio clientePorOrdenServicio,int anioBuscado,int mesBuscado) { //recibe el tipo cliente empresarial

    boolean existeAnio = false;

    // Primero se verifica si hay al menos una orden con ese año
    for (OrdenServicio orden : listOrden) {
        if (orden.getFechaServicio().getYear() == anioBuscado &&
            orden.getCliente().getIdentificacion().equals(clientePorOrdenServicio.getCliente().getIdentificacion())) {
            existeAnio = true;
            break;
        }
    }

    if (!existeAnio) {
        System.out.println("No existen órdenes para el año " + anioBuscado + " del cliente.");
        return; // se detiene el método
    }
    // Verificar si existe al menos una orden con ese mes para el cliente en el año buscado
    boolean existeMes = false;
    Month mesPalabra = Month.of(mesBuscado); //Mes en ingles
    Locale locale = Locale.forLanguageTag("es-ES"); 
    String nombreMes = mesPalabra.getDisplayName(TextStyle.FULL, locale).toUpperCase(); //Mes en Español
    for (OrdenServicio orden : listOrden) {


        if (orden.getCliente().getIdentificacion().equals(clientePorOrdenServicio.getCliente().getIdentificacion())
            && orden.getFechaServicio().getYear() == anioBuscado
            && orden.getFechaServicio().getMonthValue() == mesBuscado) {
            existeMes = true;
            mesPalabra = orden.getFechaServicio().getMonth();
            break;
        }
    }
    if (!existeMes) {
        System.out.println("No existen órdenes para el mes de " + nombreMes +
            " del año " + anioBuscado + " para el cliente.");
        return;
    }
        double sumaTotal = 0.0;
        for (OrdenServicio esaOrden : listOrden) {
            if (esaOrden.getCliente().getIdentificacion().equals(clientePorOrdenServicio.getCliente().getIdentificacion())) {
                String placa = esaOrden.getPlacaVehiculo();
                LocalDate fechaCompleta = esaOrden.getFechaServicio();
                int mes = fechaCompleta.getMonthValue();
                int dia = fechaCompleta.getDayOfMonth();
                TipoVehiculo tipoVehiculo = esaOrden.getTipoVehiculo();

                // Para cada detalle de servicio en la orden, imprime nombre y cantidad
                for (DetalleServicio eseServicio : esaOrden.getServicios()) {
                    String nombreServicio = eseServicio.getServicio().getNombre();
                    int cantidad = eseServicio.getCantidad();
                    double subtotal = eseServicio.getSubtotal();

                    double total = esaOrden.getTotalOrden();
                    String formato = "%-10s %-8s %-12s %-35s %8d %12.2f%n";
                    System.out.printf(formato, placa, dia + "-" + mes, tipoVehiculo, nombreServicio, cantidad, subtotal);
                }
                sumaTotal += esaOrden.getTotalOrden();
            }
        }
        System.out.println("Total a pagar: " + (sumaTotal+50));
    }
       
        
    //METODO REPORTE DE INGRESOS POR SERVICIO
    public void freporteIngresosxServicio(int año, int mes){
        ArrayList<Servicio> servicios= getListService();
        double [] totalServicio = new double[servicios.size()];

        for (OrdenServicio ordenes: listOrden){
            LocalDate fecha= ordenes.getFechaServicio();

            if (fecha.getYear()==año && fecha.getMonthValue()==mes){
                for (DetalleServicio detalles: ordenes.getServicios()){
                    Servicio servicio = detalles.getServicio();

                    for (int i=0 ;i< servicios.size();i++){
                        if(servicios.get(i).getCodigo().equals(servicio.getCodigo())){
                            totalServicio[i]+=detalles.getSubtotal();
                            break;
                            
                        }

                        
                    }
                }
 
            }
            
                
            }
            System.out.println("\n REPORTE DE INGRESOS PARA "+ mes + " -- "+ año );
            System.out.println("SERVICIO\t\t\tTOTAL");

            for (int i=0; i< servicios.size();i++){
                if (totalServicio[i]>0){
                    System.out.printf("%-30s\t%.2f\n", servicios.get(i).getNombre(), totalServicio[i]);
                }

            }

        }

}

    
    




    

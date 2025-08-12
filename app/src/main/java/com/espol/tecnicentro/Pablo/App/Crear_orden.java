package com.espol.tecnicentro.Pablo.App;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Pablo.adapters.Crear_Orden_Adapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.DetalleServicio;
import com.espol.tecnicentro.modelo.OrdenServicio;
import com.espol.tecnicentro.modelo.Servicio;
import com.espol.tecnicentro.modelo.Tecnico;
import com.espol.tecnicentro.modelo.TipoCliente;
import com.espol.tecnicentro.modelo.TipoVehiculo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Crear_orden extends AppCompatActivity implements Crear_Orden_Adapter.OnDeleteIcClickListener {
    private Spinner spTipo, sp_TipoV, sp_Tipo_Serv;

    private EditText idFecha_Orden, idCant_Serv, idCliente_Orden, idPlaca_Orden;

    private RecyclerView recyclerViewCOrden;
    private Crear_Orden_Adapter crearOrdenAdapter;


    private ArrayList<DetalleServicio> listaDeServicios = new ArrayList<>();
//variables que se van a enviar al constructor de OrdenServicio
    private LocalDate fechaServicioSeleccionada;
    private Tecnico tecnicoAletorio;
    private Cliente clienteSeleccionado;
    private TipoCliente tipoClienteSelect;
    private String placaSeleccionada;
    private double subtotalOrden;
    private TipoVehiculo tipoVehiculoSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_orden);

        //datos identificados
        idCliente_Orden = findViewById(R.id.idCliente_Orden);
        idPlaca_Orden = findViewById(R.id.idPlaca_Orden);
        sp_TipoV = findViewById(R.id.sp_TipoV);
        spTipo = findViewById(R.id.spTipo);
        sp_Tipo_Serv = findViewById(R.id.sp_Tipo_Serv);
        idCant_Serv = findViewById(R.id.idCant_Serv);


        //configuramos la fecha para que salga como calendario
        idFecha_Orden = findViewById(R.id.idFecha_Orden);
        idFecha_Orden.setFocusable(false);
        idFecha_Orden.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Crear_orden.this,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        // Convertir a LocalDate
                        LocalDate fechaSeleccionada = LocalDate.of(year1, monthOfYear + 1, dayOfMonth);

                        // Mostrar la fecha en formato dd/MM/yyyy en el EditText
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        idFecha_Orden.setText(fechaSeleccionada.format(formatter));

                        // Aquí ya tienes un LocalDate listo para pasar al constructor
                        // Ejemplo: guardar en una variable global
                        fechaServicioSeleccionada = fechaSeleccionada;
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        //llamar metodo llenar lista
        llenarLista();


        //Spinner de Tipo cliente-----------------------
        if (spTipo != null) {
            ArrayAdapter<TipoCliente> adapterC = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    TipoCliente.values()
            );
            adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTipo.setAdapter(adapterC);
        }
        //------------------------------

        //Spinner de Tipo Vehiculo-----------------
        if (sp_TipoV != null) {
            ArrayAdapter<TipoVehiculo> adapterV = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    TipoVehiculo.values()
            );
            adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_TipoV.setAdapter(adapterV);
        }
        //----------------------

        //Spinner servicios------------------------
        if (sp_Tipo_Serv != null) {
            ArrayAdapter<Servicio> adapterS = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    ControladorBase.getInstance().getListService()
            );
            adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_Tipo_Serv.setAdapter(adapterS);
        }
        //-----------------------------



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Agregar servicio a la lista
        Button btnAgServ = findViewById(R.id.btnAgProv);

        btnAgServ.setOnClickListener(view -> {

            String cantString = idCant_Serv.getText().toString().trim();

            // Validación: si está vacío, mostramos Toast y salimos
            if (cantString.isEmpty()) {
                Toast.makeText(this, "Debe ingresar una cantidad", Toast.LENGTH_SHORT).show();
                return; // No continúa
            }

            // Validación: cantidad mayor a 0
            int cant;
            try {
                cant = Integer.parseInt(cantString);
                if (cant <= 0) {
                    Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Cantidad inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            Servicio servicioSeleccionado = (Servicio) sp_Tipo_Serv.getSelectedItem();
            double precio = servicioSeleccionado.getPrecio();
            Log.d("PRECIO", "El precio es: " + precio);

            double subtotal = cant * precio;

            // Agregar a la lista
            listaDeServicios.add(new DetalleServicio(cant, servicioSeleccionado, subtotal));
            crearOrdenAdapter.notifyDataSetChanged();
        });


        //Guardar lista----------------------------

        Button btnGlistaServ = findViewById(R.id.btnGlistaServ);

        btnGlistaServ.setOnClickListener(view -> {

            String idclientActual = idCliente_Orden.getText().toString().trim();

            clienteSeleccionado = null;
            for (Cliente cliente : ControladorBase.getInstance().getListClient()){
                if (cliente.getIdentificacion().equals(idclientActual)){
                    clienteSeleccionado = cliente;
                    break;
                }
            }

            //verifica que el cliente exista
            if (clienteSeleccionado == null) {
                Toast.makeText(this, "Debe seleccionar un cliente válido", Toast.LENGTH_SHORT).show();
                return; // Evita continuar si no hay cliente válido
            }

            tipoClienteSelect = (TipoCliente) spTipo.getSelectedItem();

            tipoVehiculoSelect =(TipoVehiculo) sp_TipoV.getSelectedItem();

            placaSeleccionada = idPlaca_Orden.getText().toString().trim();

            tecnicoAletorio= seleccionarTecnicoAleatorio();

            if (tecnicoAletorio == null) {
                Toast.makeText(this, "No hay técnicos disponibles. Debe agregar un nuevo técnico.", Toast.LENGTH_SHORT).show();

            }

            subtotalOrden= calcularTotalOrden(listaDeServicios);

 //(Cliente cliente, Tecnico tecnico, LocalDate fechaServicio, String placaVehiculo, double totalOrden,
  //          TipoVehiculo tipoVehiculo, ArrayList<DetalleServicio> servicios)
            ControladorBase.getInstance().getListOrden().add(new OrdenServicio(clienteSeleccionado,tecnicoAletorio, fechaServicioSeleccionada, placaSeleccionada,subtotalOrden ,tipoVehiculoSelect,listaDeServicios));
            Intent intent = new Intent(Crear_orden.this, MainActivity_Orden.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();


        });

    }

    @Override
    public void onDeleteIcClick(DetalleServicio servicio, int position){

        // Verificamos que la posición sea válida para evitar errores.
        if (position >= 0 && position < listaDeServicios.size()) {
            listaDeServicios.remove(position);

            // Esto le dice al RecyclerView que el elemento en esa posición ha sido eliminado.
            if (crearOrdenAdapter != null) {
                crearOrdenAdapter.notifyItemRemoved(position);
                // También puedes usar notifyDataSetChanged() si no te importa el efecto de animación.
                // crearOrdenAdapter.notifyDataSetChanged();
            }
        }
    }

    private void llenarLista(){
        recyclerViewCOrden = findViewById(R.id.recyclerViewCOrden);
        recyclerViewCOrden.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador

        crearOrdenAdapter = new Crear_Orden_Adapter(listaDeServicios,this,this);
        recyclerViewCOrden.setAdapter(crearOrdenAdapter);
    }



    public Tecnico seleccionarTecnicoAleatorio() {
        List<Tecnico> tecnicosDisponibles = new ArrayList<>();
        for (Tecnico tecnico : ControladorBase.getInstance().getListTecni()) {
            int ordenesAsignadas = 0;
            for (OrdenServicio orden : ControladorBase.getInstance().getListOrden()) {
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

    private double calcularTotalOrden(ArrayList<DetalleServicio> listdetalles) {
        double total = 0.0;
        for (DetalleServicio detalle : listdetalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }




@Override
    public void onResume(){
        super.onResume();
        llenarLista();
        Log.d("AppLista Servicios", "En onResume");//muestra la lista en el log

    ArrayList<Servicio> listaArchivo = Servicio.cargarServicio(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
    if (listaArchivo != null) {
        ControladorBase.getInstance().setListService(listaArchivo);
    }

    ArrayAdapter<Servicio> adapterS = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            ControladorBase.getInstance().getListService()
    );
    adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sp_Tipo_Serv.setAdapter(adapterS);

}

}
package com.espol.tecnicentro.Pablo.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.espol.tecnicentro.ListaBase.DatosBase;
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
    private Spinner spTipo, sp_TipoV, sp_Tipo_Serv,spidCliente_Orden;
    private EditText idFecha_Orden, idCant_Serv, idPlaca_Orden;
    private RecyclerView recyclerViewCOrden;
    private Crear_Orden_Adapter crearOrdenAdapter;
    private ArrayList<DetalleServicio> listaDeServicios = new ArrayList<>();
    private LocalDate fechaServicioSeleccionada;
    private Tecnico tecnicoAletorio;
    private Cliente clienteSeleccionado;
    private TipoCliente tipoClienteSelect;
    private String placaSeleccionada;
    private double subtotalOrden;
    private TipoVehiculo tipoVehiculoSelect;
    private ArrayList<OrdenServicio> listaPrincipal= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_orden);

        //datos identificados
        spidCliente_Orden = findViewById(R.id.spidCliente_Orden);
        idPlaca_Orden = findViewById(R.id.idPlaca_Orden);
        idPlaca_Orden.setFilters(new InputFilter[]{new InputFilter.AllCaps()});//Forzamos a usar mayusculas
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
                        LocalDate fechaSeleccionada = LocalDate.of(year1, monthOfYear + 1, dayOfMonth);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        idFecha_Orden.setText(fechaSeleccionada.format(formatter));
                        fechaServicioSeleccionada = fechaSeleccionada;
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        //llamar metodo llenar lista
        llenarLista();



        // Spinner de Tipo cliente-----------------------
        // Este se llena primero para que esté listo cuando el otro Spinner lo necesite.
        if (spTipo != null) {
            ArrayAdapter<TipoCliente> adapterTipoCliente = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    TipoCliente.values()
            );
            adapterTipoCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spTipo.setAdapter(adapterTipoCliente);
        }

        // Spinner de ID Clientes---------------
        List<Cliente> listaClientes = new ArrayList<>(DatosBase.getInstance().getListClient());

        // siempre añadimos un cliente "dummy" en la posición 0 solo para el spinner
        listaClientes.add(0, new Cliente("Seleccione un Cliente", "", "", "", null));

        if (spidCliente_Orden != null) {
            ArrayAdapter<Cliente> adapterClientes = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    listaClientes
            );
            adapterClientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spidCliente_Orden.setAdapter(adapterClientes);
        }


        // Listener para el Spinner de ID de cliente
        spidCliente_Orden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el objeto Cliente seleccionado
                Cliente clienteSeleccionado = (Cliente) parent.getItemAtPosition(position);

                // Si el cliente no es nulo, obtenemos su TipoCliente
                if (clienteSeleccionado != null) {
                    TipoCliente tipoDelCliente = clienteSeleccionado.getTipoCliente();
                    // Llama al nuevo metodo para seleccionar y bloquear el Spinner de TipoCliente
                    seleccionarYBloquearSpinnerTipo(tipoDelCliente);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Si la selección se borra, puedes habilitar el Spinner
                spTipo.setEnabled(true);
            }
        });

        // FIN DE CAMBIOS

        // Spinner de Tipo Vehiculo
        if (sp_TipoV != null) {
            ArrayAdapter<TipoVehiculo> adapterV = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    TipoVehiculo.values()
            );
            adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_TipoV.setAdapter(adapterV);
        }

        // Spinner servicios
        if (sp_Tipo_Serv != null) {
            ArrayAdapter<Servicio> adapterS = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    DatosBase.getInstance().getListService()
            );
            adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp_Tipo_Serv.setAdapter(adapterS);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Agregar servicio a la lista
        Button btnAgServ = findViewById(R.id.btnAgProv);
        btnAgServ.setOnClickListener(view -> {
            String cantString = idCant_Serv.getText().toString().trim();
            if (cantString.isEmpty()) {
                Toast.makeText(this, "Debe ingresar una cantidad", Toast.LENGTH_SHORT).show();
                return;
            }
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
            listaDeServicios.add(new DetalleServicio(cant, servicioSeleccionado));
            crearOrdenAdapter.notifyDataSetChanged();
        });

        //Guardar Orden
        Button btnGlistaServ = findViewById(R.id.btnGlistaServ);
        btnGlistaServ.setOnClickListener(view -> {
            if (!verificacionDeCampos()) {
                return;
            }
            listaPrincipal = DatosBase.getInstance().getListOrden();

            tipoClienteSelect = (TipoCliente) spTipo.getSelectedItem();
            tipoVehiculoSelect =(TipoVehiculo) sp_TipoV.getSelectedItem();
            clienteSeleccionado = (Cliente) spidCliente_Orden.getSelectedItem();
            placaSeleccionada = idPlaca_Orden.getText().toString().trim();
            tecnicoAletorio= seleccionarTecnicoAleatorio();
            subtotalOrden= calcularTotalOrden(listaDeServicios);

            if (tecnicoAletorio == null) {
                Toast.makeText(this, "No hay técnicos disponibles. Debe agregar un nuevo técnico.", Toast.LENGTH_SHORT).show();
                return; // Añadir un return aquí para evitar errores si no hay técnico
            }

            try {
                OrdenServicio nuevaOrden = new OrdenServicio(clienteSeleccionado,tecnicoAletorio, fechaServicioSeleccionada, placaSeleccionada,subtotalOrden ,tipoVehiculoSelect,listaDeServicios);
                listaPrincipal.add(nuevaOrden);
                Log.d("AppOrdenes", nuevaOrden.toString());
                OrdenServicio.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
                Toast.makeText(getApplicationContext(), "Orden de Servicios Creada", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("AppOrdenes", "Error al guardar datos: " + e.getMessage());
            }

            Intent intent = new Intent(Crear_orden.this, MainActivity_Orden.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    // Nuevo método para manejar la selección y bloqueo del Spinner de TipoCliente
    private void seleccionarYBloquearSpinnerTipo(TipoCliente tipoCliente) {
        ArrayAdapter<TipoCliente> adapterTipo = (ArrayAdapter<TipoCliente>) spTipo.getAdapter();

        if (adapterTipo == null) {
            return;
        }

        int position = adapterTipo.getPosition(tipoCliente);

        if (position >= 0) {
            spTipo.setSelection(position);
            spTipo.setEnabled(false); // Bloquea el Spinner
        }
    }

    @Override
    public void onDeleteIcClick(DetalleServicio servicio, int position){
        if (position >= 0 && position < listaDeServicios.size()) {
            listaDeServicios.remove(position);
            if (crearOrdenAdapter != null) {
                crearOrdenAdapter.notifyItemRemoved(position);
            }
        }
    }

    private void llenarLista(){
        recyclerViewCOrden = findViewById(R.id.recyclerViewCOrden);
        recyclerViewCOrden.setLayoutManager(new LinearLayoutManager(this));
        crearOrdenAdapter = new Crear_Orden_Adapter(listaDeServicios,this,this);
        recyclerViewCOrden.setAdapter(crearOrdenAdapter);
    }

    public Tecnico seleccionarTecnicoAleatorio() {
        List<Tecnico> tecnicosDisponibles = new ArrayList<>();
        for (Tecnico tecnico : DatosBase.getInstance().getListTecni()) {
            int ordenesAsignadas = 0;
            for (OrdenServicio orden : DatosBase.getInstance().getListOrden()) {
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

    //metodo para calcular el total de la orden
    private double calcularTotalOrden(ArrayList<DetalleServicio> listdetalles) {
        double total = 0.0;
        for (DetalleServicio detalle : listdetalles) {
            total += detalle.getSubtotal();
        }
        return total;
    }

    //metodo para verficar que los campos esten llenos
    private boolean verificacionDeCampos() {
        if (spidCliente_Orden.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Debe seleccionar un cliente.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (idFecha_Orden.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Debe seleccionar la fecha de servicio.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (sp_TipoV.getSelectedItem() == null) {
            Toast.makeText(this, "Debe seleccionar un tipo de vehículo.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (idPlaca_Orden.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "La placa del vehículo es obligatoria.", Toast.LENGTH_SHORT).show();
            idPlaca_Orden.requestFocus();
            return false;
        }

        if (spTipo.getSelectedItem() == null) {
            Toast.makeText(this, "Debe seleccionar un tipo de cliente.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (listaDeServicios.isEmpty()) {
            Toast.makeText(this, "Debe agregar al menos un servicio.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        llenarLista();
        Log.d("AppLista Servicios", "En onResume");

        ArrayList<Servicio> listaArchivo = Servicio.cargarServicio(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        if (listaArchivo != null) {
            DatosBase.getInstance().setListService(listaArchivo);
        }

        ArrayAdapter<Servicio> adapterS = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                DatosBase.getInstance().getListService()
        );
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Tipo_Serv.setAdapter(adapterS);
    }
}
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

    private Spinner spTipo, sp_TipoV, sp_Tipo_Serv, spidCliente_Orden;
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
    private ArrayList<OrdenServicio> listaPrincipal = new ArrayList<>();
    private ArrayAdapter<Cliente> adapterClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_orden);

        // ----- Inicialización de vistas -----
        spidCliente_Orden = findViewById(R.id.spidCliente_Orden);
        idPlaca_Orden = findViewById(R.id.idPlaca_Orden);
        idPlaca_Orden.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        sp_TipoV = findViewById(R.id.sp_TipoV);
        spTipo = findViewById(R.id.spTipo);
        sp_Tipo_Serv = findViewById(R.id.sp_Tipo_Serv);
        idCant_Serv = findViewById(R.id.idCant_Serv);
        idFecha_Orden = findViewById(R.id.idFecha_Orden);
        idFecha_Orden.setFocusable(false);

        // ----- Configurar calendario -----
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

        // ----- Spinner Tipo Cliente -----
        ArrayAdapter<TipoCliente> adapterTipoCliente = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                TipoCliente.values()
        );
        adapterTipoCliente.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapterTipoCliente);

        // ----- Spinner Clientes -----
        llenarSpinnerClientes();

        spidCliente_Orden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cliente cliente = (Cliente) parent.getItemAtPosition(position);
                if (cliente != null) {
                    seleccionarYBloquearSpinnerTipo(cliente.getTipoCliente());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spTipo.setEnabled(true);
            }
        });

        // ----- Spinner Tipo Vehículo -----
        ArrayAdapter<TipoVehiculo> adapterV = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TipoVehiculo.values());
        adapterV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_TipoV.setAdapter(adapterV);

        // ----- Spinner Servicios -----
        ArrayAdapter<Servicio> adapterS = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DatosBase.getInstance().getListService());
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Tipo_Serv.setAdapter(adapterS);

        // ----- RecyclerView -----
        recyclerViewCOrden = findViewById(R.id.recyclerViewCOrden);
        recyclerViewCOrden.setLayoutManager(new LinearLayoutManager(this));
        crearOrdenAdapter = new Crear_Orden_Adapter(listaDeServicios, this, this);
        recyclerViewCOrden.setAdapter(crearOrdenAdapter);

        // ----- Agregar servicio -----
        Button btnAgServ = findViewById(R.id.btnAgProv);
        btnAgServ.setOnClickListener(view -> agregarServicio());

        // ----- Guardar Orden -----
        Button btnGlistaServ = findViewById(R.id.btnGlistaServ);
        btnGlistaServ.setOnClickListener(view -> guardarOrden());

        // ----- EdgeToEdge -----
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void llenarSpinnerClientes() {
        List<Cliente> listaClientes = new ArrayList<>(DatosBase.getInstance().getListClient());

        // Evitar duplicar "Seleccione un Cliente"
        if (listaClientes.isEmpty() || !listaClientes.get(0).getNombre().equals("Seleccione un Cliente")) {
            listaClientes.add(0, new Cliente("Seleccione un Cliente", "", "", "", null));
        }

        adapterClientes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaClientes);
        adapterClientes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spidCliente_Orden.setAdapter(adapterClientes);
    }

    private void seleccionarYBloquearSpinnerTipo(TipoCliente tipoCliente) {
        ArrayAdapter<TipoCliente> adapterTipo = (ArrayAdapter<TipoCliente>) spTipo.getAdapter();
        if (adapterTipo == null) return;
        int position = adapterTipo.getPosition(tipoCliente);
        if (position >= 0) {
            spTipo.setSelection(position);
            spTipo.setEnabled(false);
        }
    }

    private void agregarServicio() {
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
        listaDeServicios.add(new DetalleServicio(cant, servicioSeleccionado));
        crearOrdenAdapter.notifyDataSetChanged();
    }

    private void guardarOrden() {
        if (!verificacionDeCampos()) return;

        listaPrincipal = DatosBase.getInstance().getListOrden();
        tipoClienteSelect = (TipoCliente) spTipo.getSelectedItem();
        tipoVehiculoSelect = (TipoVehiculo) sp_TipoV.getSelectedItem();
        clienteSeleccionado = (Cliente) spidCliente_Orden.getSelectedItem();
        placaSeleccionada = idPlaca_Orden.getText().toString().trim();
        tecnicoAletorio = seleccionarTecnicoAleatorio();
        subtotalOrden = calcularTotalOrden(listaDeServicios);

        if (tecnicoAletorio == null) {
            Toast.makeText(this, "No hay técnicos disponibles. Debe agregar un nuevo técnico.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            OrdenServicio nuevaOrden = new OrdenServicio(clienteSeleccionado, tecnicoAletorio, fechaServicioSeleccionada, placaSeleccionada, subtotalOrden, tipoVehiculoSelect, listaDeServicios);
            listaPrincipal.add(nuevaOrden);
            OrdenServicio.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
            Toast.makeText(this, "Orden de Servicios Creada", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("AppOrdenes", "Error al guardar datos: " + e.getMessage());
        }

        Intent intent = new Intent(Crear_orden.this, MainActivity_Orden.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDeleteIcClick(DetalleServicio servicio, int position) {
        if (position >= 0 && position < listaDeServicios.size()) {
            listaDeServicios.remove(position);
            crearOrdenAdapter.notifyItemRemoved(position);
        }
    }

    private Tecnico seleccionarTecnicoAleatorio() {
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
        if (tecnicosDisponibles.isEmpty()) return null;
        Random random = new Random();
        return tecnicosDisponibles.get(random.nextInt(tecnicosDisponibles.size()));
    }

    private double calcularTotalOrden(ArrayList<DetalleServicio> listdetalles) {
        double total = 0.0;
        for (DetalleServicio detalle : listdetalles) total += detalle.getSubtotal();
        return total;
    }

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
    protected void onResume() {
        super.onResume();

        // Actualizar servicios
        ArrayList<Servicio> listaArchivo = Servicio.cargarServicio(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        if (listaArchivo != null) {
            DatosBase.getInstance().setListService(listaArchivo);
        }

        ArrayAdapter<Servicio> adapterS = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DatosBase.getInstance().getListService());
        adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Tipo_Serv.setAdapter(adapterS);

        // Actualizar RecyclerView
        crearOrdenAdapter.notifyDataSetChanged();

        // Actualizar Spinner Clientes sin duplicar
        adapterClientes.clear();
        adapterClientes.add(new Cliente("Seleccione un Cliente", "", "", "", null));
        adapterClientes.addAll(DatosBase.getInstance().getListClient());
        adapterClientes.notifyDataSetChanged();
    }
}

package com.espol.tecnicentro.Andrea;

import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Andrea.Adapters.DetalleFacturaAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.DetalleServicio;
import com.espol.tecnicentro.modelo.OrdenServicio;
import com.espol.tecnicentro.modelo.TipoCliente;

// === NUEVAS CLASES DEL HISTORIAL ===
import com.espol.tecnicentro.Andrea.FacturaResumen;
import com.espol.tecnicentro.Andrea.FacturaStore;
import com.espol.tecnicentro.Andrea.DetalleFacturaItem;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class GenerarFacturaActivity extends AppCompatActivity {

    private Spinner spEmpresa, spMes;
    private EditText etAnio;
    private Button btnGenerar;
    private RecyclerView rvDetalle;
    private TextView tvTotal;

    private DatosBase base;

    private final List<Cliente> empresas = new ArrayList<>();
    private final List<DetalleFacturaItem> items = new ArrayList<>();
    private DetalleFacturaAdapter detalleAdapter;

    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "EC"));

    // Si vienes desde “Más detalle”
    private boolean modoDetalle = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_factura);

        base = DatosBase.getInstance();
        base.inicializarApp();

        // lee Ordenes.ser del mismo lugar donde las guarda Crear_orden
        syncOrdenesDesdeDisco();

        spEmpresa = findViewById(R.id.spEmpresa);
        spMes = findViewById(R.id.spMes);
        etAnio = findViewById(R.id.etAnio);
        btnGenerar = findViewById(R.id.btnGenerar);
        rvDetalle = findViewById(R.id.rvDetalle);
        tvTotal = findViewById(R.id.tvTotal);

        // Empresas solo EMPRESARIALES
        for (Cliente c : base.getListClient()) {
            if (c.getTipoCliente() == TipoCliente.EMPRESARIAL) empresas.add(c);
        }
        spEmpresa.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, toNombres(empresas)));

        // Meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spMes.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, meses));

        // Recycler
        rvDetalle.setLayoutManager(new LinearLayoutManager(this));
        detalleAdapter = new DetalleFacturaAdapter(items);
        rvDetalle.setAdapter(detalleAdapter);

        // Modo normal: genera y GUARDA en el historial
        btnGenerar.setOnClickListener(v -> generar(true));

        // ------- Modo “Más detalle” (desde listado) -------
        String empresaId = getIntent().getStringExtra("empresaId");
        int anioExtra = getIntent().getIntExtra("anio", -1);
        int mesExtra = getIntent().getIntExtra("mes", -1); // 1..12

        if (empresaId != null && anioExtra > 0 && mesExtra > 0) {
            int idxEmpresa = -1;
            for (int i = 0; i < empresas.size(); i++) {
                if (empresaId.equals(empresas.get(i).getIdentificacion())) {
                    idxEmpresa = i;
                    break;
                }
            }
            if (idxEmpresa >= 0) spEmpresa.setSelection(idxEmpresa);

            etAnio.setText(String.valueOf(anioExtra));
            if (mesExtra >= 1 && mesExtra <= 12) spMes.setSelection(mesExtra - 1);

            spEmpresa.setEnabled(false);
            spMes.setEnabled(false);
            etAnio.setEnabled(false);
            btnGenerar.setVisibility(android.view.View.GONE);

            modoDetalle = true;
            generar(false); // ← NO se guarda
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncOrdenesDesdeDisco();
        if (modoDetalle) generar(false);
    }

    /** Lee Ordenes.ser desde el mismo lugar donde las guarda Crear_orden (external files / Downloads). */
    private void syncOrdenesDesdeDisco() {
        ArrayList<OrdenServicio> desdeDisco =
                OrdenServicio.cargarOrdenes(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
        if (desdeDisco != null && !desdeDisco.isEmpty()) {
            base.getListOrden().clear();
            base.getListOrden().addAll(desdeDisco);
        }
    }

    private List<String> toNombres(List<Cliente> cs) {
        List<String> r = new ArrayList<>();
        for (Cliente c : cs) r.add(c.getNombre());
        return r;
    }

    /** Genera el detalle; si guardarEnHistorial=true, registra en FacturaStore. */
    private void generar(boolean guardarEnHistorial) {
        if (empresas.isEmpty()) {
            Toast.makeText(this, "No hay empresas registradas", Toast.LENGTH_SHORT).show();
            return;
        }
        String sAnio = etAnio.getText().toString().trim();
        if (sAnio.isEmpty()) {
            Toast.makeText(this, "Ingrese un año", Toast.LENGTH_SHORT).show();
            return;
        }

        int anio = Integer.parseInt(sAnio);
        int mesIdx = spMes.getSelectedItemPosition() + 1; // 1..12
        Cliente empresa = empresas.get(spEmpresa.getSelectedItemPosition());

        HashMap<String, DetalleFacturaItem> mapa = new HashMap<>();

        for (OrdenServicio o : base.getListOrden()) {
            if (o == null || o.getCliente() == null || o.getFechaServicio() == null) continue;

            if (!empresa.getIdentificacion().equals(o.getCliente().getIdentificacion())) continue;
            if (o.getFechaServicio().getYear() != anio) continue;
            if (o.getFechaServicio().getMonthValue() != mesIdx) continue;

            if (o.getServicios() == null) continue;

            for (DetalleServicio d : o.getServicios()) {
                if (d == null || d.getServicio() == null) continue;
                String nombre = d.getServicio().getNombre();
                double precio = d.getServicio().getPrecio();
                int cant = d.getCantidad();

                DetalleFacturaItem it = mapa.get(nombre);
                if (it == null) {
                    it = new DetalleFacturaItem(nombre, cant, precio);
                    mapa.put(nombre, it);
                } else {
                    it.cantidad += cant;
                }
            }
        }

        // Volcar resultados al adapter
        items.clear();
        items.addAll(mapa.values());

        // Total de servicios
        double totalServicios = 0.0;
        for (DetalleFacturaItem it : items) totalServicios += it.getSubtotal();

        // Caso sin órdenes: NO guardar, mostrar $0 y salir
        if (items.isEmpty()) {
            detalleAdapter.notifyDataSetChanged();
            tvTotal.setText("Total a pagar: " + nf.format(0));
            Toast.makeText(this, "No hay servicios en ese período.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hay servicios: recargo fijo empresarial de $50
        double total = totalServicios + 50.0;

        detalleAdapter.notifyDataSetChanged();
        tvTotal.setText("Total a pagar: " + nf.format(total));

        // Guardar en historial SOLO en modo formulario (no en “Más detalle”)
        if (guardarEnHistorial) {
            FacturaResumen fr = new FacturaResumen(
                    empresa,
                    anio,
                    mesIdx,
                    total,
                    LocalDate.now()
            );
            FacturaStore.upsert(this, fr);
            Toast.makeText(this, "Factura registrada en el historial", Toast.LENGTH_SHORT).show();
        }
    }
}
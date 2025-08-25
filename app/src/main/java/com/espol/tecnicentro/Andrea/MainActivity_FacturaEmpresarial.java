package com.espol.tecnicentro.Andrea;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Andrea.Adapters.FacturaAdapter;
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity_FacturaEmpresarial extends AppCompatActivity {

    private RecyclerView recycler;
    private FacturaAdapter adapter;

    private DatosBase base; // singleton
    private final List<OrdenServicio> facturasUi = new ArrayList<>();

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_factura_empresarial);

        base = DatosBase.getInstance();
        base.inicializarApp();

        recycler = findViewById(R.id.recyclerFacturas);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FacturaAdapter(facturasUi);
        recycler.setAdapter(adapter);

        // Botón para generar factura
        findViewById(R.id.btnGenerarFactura)
                .setOnClickListener(v -> startActivity(new Intent(this, GenerarFacturaActivity.class)));

        // 🔹 Nuevo botón para borrar historial
        findViewById(R.id.btnClearHistorial).setOnClickListener(v -> {
            FacturaStore.clear(this);
            Toast.makeText(this, "Historial borrado", Toast.LENGTH_SHORT).show();
            refreshData();
        });

        refreshData();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    /** Carga el historial (FacturaStore) y lo adapta a la UI existente (usa OrdenServicio “fake”). */
    private void refreshData() {
        // Usa el nombre real del método de tu store (en tu clase es "load")
        List<FacturaResumen> historial = FacturaStore.load(this);

        facturasUi.clear();

        for (FacturaResumen fr : historial) {
            // Tomamos el cliente tal cual se guardó en el resumen
            Cliente cli = fr.getEmpresa();

            // Si quieres reflejar cambios de nombre del cliente, intenta buscarlo por ID
            if (cli != null && cli.getIdentificacion() != null) {
                for (Cliente c : base.getListClient()) {
                    if (cli.getIdentificacion().equals(c.getIdentificacion())) {
                        cli = c; // usa la versión “viva” del cliente
                        break;
                    }
                }
            }

            // Si por alguna razón no hay cliente, crea uno “placeholder” para evitar NPE
            if (cli == null) {
                cli = new Cliente("Empresa desconocida", "", "", "", null);
            }

            // Adaptamos a la UI existente: creamos un OrdenServicio “falso”
            OrdenServicio fake = new OrdenServicio();
            fake.setCliente(cli);

            // El adapter muestra “Periodo”, así que basta con día 1 del mes; si
            // guardaste fecha de creación úsala, si no, construye con anio/mes.
            LocalDate fecha = fr.getFechaCreacion() != null
                    ? fr.getFechaCreacion()
                    : LocalDate.of(fr.getAnio(), fr.getMes(), 1);
            fake.setFechaServicio(fecha);

            fake.setTotalOrden(fr.getTotal());

            facturasUi.add(fake);
        }

        // Ordenar por fecha DESC
        Collections.sort(facturasUi, (a, b) -> {
            if (a.getFechaServicio() == null && b.getFechaServicio() == null) return 0;
            if (a.getFechaServicio() == null) return 1;
            if (b.getFechaServicio() == null) return -1;
            return b.getFechaServicio().compareTo(a.getFechaServicio());
        });

        adapter.notifyDataSetChanged();
    }

}
package com.espol.tecnicentro.Andrea;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.Andrea.Adapters.FacturaAdapter;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.controladores.ControladorFactura;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import com.espol.tecnicentro.modelo.TipoCliente;

public class MainActivity_FacturaEmpresarial extends AppCompatActivity {

    private RecyclerView recycler;
    private Button btnGenerar;
    private FacturaAdapter adapter;

    // Controladores
    private ControladorBase base;
    private ControladorFactura controladorFactura;


    private final List<OrdenServicio> facturasUi = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_factura_empresarial);


        base = new ControladorBase();
        base.inicializarApp();
        controladorFactura = new ControladorFactura(base);

        // UI
        recycler = findViewById(R.id.recyclerFacturas);
        btnGenerar = findViewById(R.id.btnGenerarFactura);
        recycler.setLayoutManager(new LinearLayoutManager(MainActivity_FacturaEmpresarial.this));


        facturasUi.clear();


        Map<String, OrdenServicio> mapa = new HashMap<>();
        for (OrdenServicio o : base.getListOrden()) {

            if (o.getCliente().getTipoCliente() != TipoCliente.EMPRESARIAL) continue;

            String key = o.getCliente().getIdentificacion() + "-" +
                    o.getFechaServicio().getYear() + "-" +
                    o.getFechaServicio().getMonthValue();

            OrdenServicio agg = mapa.get(key);
            if (agg == null) {
                agg = new OrdenServicio();
                agg.setCliente(o.getCliente());
                agg.setFechaServicio(o.getFechaServicio()); // fecha más reciente del grupo
                agg.setTotalOrden(o.getTotalOrden());       // iniciamos acumulado
                mapa.put(key, agg);
            } else {
                // Acumular
                agg.setTotalOrden(agg.getTotalOrden() + o.getTotalOrden());
                //  fecha más reciente como "fecha de creación"
                if (o.getFechaServicio() != null && agg.getFechaServicio() != null
                        && o.getFechaServicio().isAfter(agg.getFechaServicio())) {
                    agg.setFechaServicio(o.getFechaServicio());
                }
            }
        }

        // +$50
        for (OrdenServicio os : mapa.values()) {
            if (os.getCliente() != null && os.getCliente().getTipoCliente() == TipoCliente.EMPRESARIAL) {
                os.setTotalOrden(os.getTotalOrden() + 50.0);
            }
        }


        facturasUi.addAll(mapa.values());


        Collections.sort(facturasUi, new Comparator<OrdenServicio>() {
            @Override
            public int compare(OrdenServicio o1, OrdenServicio o2) {
                if (o1.getFechaServicio() == null && o2.getFechaServicio() == null) return 0;
                if (o1.getFechaServicio() == null) return 1;
                if (o2.getFechaServicio() == null) return -1;
                return o2.getFechaServicio().compareTo(o1.getFechaServicio());
            }
        });

        adapter = new FacturaAdapter(facturasUi);
        recycler.setAdapter(adapter);

        // boton a Generar Factura
        btnGenerar.setOnClickListener(v -> {
            startActivity(new Intent(
                    MainActivity_FacturaEmpresarial.this,
                    GenerarFacturaActivity.class
            ));
        });
    }
}
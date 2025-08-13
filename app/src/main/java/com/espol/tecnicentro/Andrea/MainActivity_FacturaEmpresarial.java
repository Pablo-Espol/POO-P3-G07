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

public class MainActivity_FacturaEmpresarial extends AppCompatActivity {

    private RecyclerView recycler;
    private Button btnGenerar;
    private FacturaAdapter adapter;

    // Controladores
    private ControladorBase base;
    private ControladorFactura controladorFactura;

    // Fuente de datos para el listado (por ahora usamos las órdenes)
    private final List<OrdenServicio> facturasUi = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_factura_empresarial);

        // 1) Inicializar controladores y datos en memoria
        base = new ControladorBase();
        base.inicializarApp();
        controladorFactura = new ControladorFactura(base);


        recycler   = findViewById(R.id.recyclerFacturas);
        btnGenerar = findViewById(R.id.btnGenerarFactura);

        recycler.setLayoutManager(new LinearLayoutManager(MainActivity_FacturaEmpresarial.this));

        // Fuente de datos inicial órdenes existentes
        //    Si luego guardan "facturas" reales, aquí se reemplazan.
        facturasUi.clear();
        facturasUi.addAll(base.getListOrden());

        // Ordenar por fecha DESC (más recientes primero)
        Collections.sort(facturasUi, new Comparator<OrdenServicio>() {
            @Override
            public int compare(OrdenServicio o1, OrdenServicio o2) {
                if (o1.getFechaServicio() == null && o2.getFechaServicio() == null) return 0;
                if (o1.getFechaServicio() == null) return 1;   // nulos al final
                if (o2.getFechaServicio() == null) return -1;
                // invertido para descendente
                return o2.getFechaServicio().compareTo(o1.getFechaServicio());
            }
        });

        // adapter
        adapter = new FacturaAdapter(facturasUi);
        recycler.setAdapter(adapter);

        // se abrirá la pantalla de generación
        //btnGenerar.setOnClickListener(v -> {
            // TODO: crea esta Activity y su layout (la vemos después)
           // Intent i = new Intent(
             //       MainActivity_FacturaEmpresarial.this,
              //      GenerarFacturaActivity.class
        //    );
         //   startActivity(i);
      //  });
    }
}
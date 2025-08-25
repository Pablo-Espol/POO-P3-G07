package com.espol.tecnicentro.Andrea;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.Andrea.Adapters.ReporteServicioAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_ReporteServicio extends AppCompatActivity {

    private EditText etAnio;
    private Spinner spMes;
    private Button btnConsultar;
    private RecyclerView rvServicios;
    private ReporteServicioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reporte_servicio);

        etAnio = findViewById(R.id.etAnioServicio);
        spMes = findViewById(R.id.spMesServicio);
        btnConsultar = findViewById(R.id.btnConsultarServicio);
        rvServicios = findViewById(R.id.rvReporteServicio);


        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        ArrayAdapter<String> adapterMes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, meses);
        spMes.setAdapter(adapterMes);


        rvServicios.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReporteServicioAdapter(new ArrayList<>());
        rvServicios.setAdapter(adapter);


        btnConsultar.setOnClickListener(v -> {
            String anio = etAnio.getText().toString();
            String mes = spMes.getSelectedItem().toString();

            // Datos de prueba
            List<ReporteServicio> datos = new ArrayList<>();
            datos.add(new ReporteServicio("Alineación", 20));
            datos.add(new ReporteServicio("Balanceo", 25));
            datos.add(new ReporteServicio("Cambio de aceite motor", 10));
            datos.add(new ReporteServicio("Cambio filtro aceite", 15));

            adapter.setDatos(datos);
        });
    }
}
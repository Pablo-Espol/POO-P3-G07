package com.espol.tecnicentro.Andrea;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.Andrea.Adapters.ReporteTecnicoAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.controladores.ControladorReporte;

public class MainActivity_ReporteTecnico extends AppCompatActivity {

    private EditText etAnio;
    private Spinner spMes;
    private Button btnConsultar;
    private RecyclerView rvTecnicos;
    private TextView tvTotalGeneral;
    private ReporteTecnicoAdapter adapter;

    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es", "EC"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reporte_tecnico);

        etAnio = findViewById(R.id.etAnioTec);
        spMes = findViewById(R.id.spMesTec);
        btnConsultar = findViewById(R.id.btnConsultarTec);
        rvTecnicos = findViewById(R.id.rvTecnicos);
        tvTotalGeneral = findViewById(R.id.tvTotalGeneralTec);

        // Meses
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        spMes.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, meses));

        // Recycler
        rvTecnicos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReporteTecnicoAdapter();
        rvTecnicos.setAdapter(adapter);

        btnConsultar.setOnClickListener(v -> {

            String anioStr = etAnio.getText().toString().trim();
            if (anioStr.isEmpty()) {
                etAnio.setError("Ingrese un año");
                etAnio.requestFocus();
                return;
            }

            try {

                int anio = Integer.parseInt(anioStr);
                int mes = spMes.getSelectedItemPosition() + 1; // 1..12

                ControladorBase base = new ControladorBase();
                base.inicializarApp(); //
                ControladorReporte ctrl = new ControladorReporte(base);

                Map<String, Double> mapa = ctrl.reporteAtencionesporTecnico(anio, mes);


                List<ReporteTecnico> datos = new ArrayList<>();
                double total = 0;
                for (Map.Entry<String, Double> e : mapa.entrySet()) {
                    datos.add(new ReporteTecnico(e.getKey(), e.getValue()));
                    total += e.getValue() != null ? e.getValue() : 0.0;
                }


                adapter.setDatos(datos);
                tvTotalGeneral.setText("Total general: " + moneda.format(total));

            } catch (NumberFormatException ex) {
                etAnio.setError("Año inválido");
                etAnio.requestFocus();
            }
        });
    }
}
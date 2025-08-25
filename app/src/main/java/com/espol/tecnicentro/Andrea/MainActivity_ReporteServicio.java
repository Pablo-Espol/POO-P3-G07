package com.espol.tecnicentro.Andrea;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Andrea.Adapters.ReporteServicioAdapter;
import com.espol.tecnicentro.Andrea.ReporteServiciosUC; // Asegúrate de que este paquete es el correcto
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity_ReporteServicio extends AppCompatActivity {

    private EditText etAnio;
    private Spinner spMes;
    private Button btnConsultar;
    private RecyclerView rvServicios;
    private TextView tvTotalGeneralServicio;

    private ReporteServicioAdapter adapter;
    private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es","EC"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reporte_servicio);

        etAnio = findViewById(R.id.etAnioServicio);
        spMes = findViewById(R.id.spMesServicio);
        btnConsultar = findViewById(R.id.btnConsultarServicio);
        rvServicios = findViewById(R.id.rvReporteServicio);
        tvTotalGeneralServicio = findViewById(R.id.tvTotalGeneralServicio);

        List<String> meses = Arrays.asList(
                "Enero","Febrero","Marzo","Abril","Mayo","Junio",
                "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"
        );
        spMes.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, meses));

        // Prefija año/mes actuales
        LocalDate hoy = LocalDate.now();
        etAnio.setText(String.valueOf(hoy.getYear()));
        spMes.setSelection(hoy.getMonthValue() - 1);

        rvServicios.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReporteServicioAdapter(new ArrayList<>());
        rvServicios.setAdapter(adapter);

        btnConsultar.setOnClickListener(v -> consultar());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String anioStr = etAnio.getText().toString().trim();
        if (anioStr.length() == 4) consultar();
    }

    private void consultar() {
        String anioStr = etAnio.getText().toString().trim();
        if (anioStr.length() != 4) {
            Toast.makeText(MainActivity_ReporteServicio.this,
                    "Ingresa un año válido (YYYY).", Toast.LENGTH_SHORT).show();
            return;
        }
        int anio = Integer.parseInt(anioStr);
        int mes = spMes.getSelectedItemPosition() + 1; // 1..12

        // 1) Leer desde el mismo directorio que usa Crear_orden (ExternalFiles/Downloads), con fallback
        File dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (dir == null) dir = getFilesDir();

        List<OrdenServicio> desdeDisco = OrdenServicio.cargarOrdenes(dir);
        if (desdeDisco == null) desdeDisco = new ArrayList<>();

        // 2) Unir con lo que está en memoria (por si aún no se guardó)
        List<OrdenServicio> enMemoria = DatosBase.getInstance().getListOrden();
        if (enMemoria == null) enMemoria = new ArrayList<>();

        ArrayList<OrdenServicio> ordenes = new ArrayList<>(desdeDisco);
        for (OrdenServicio o : enMemoria) {
            if (!ordenes.contains(o)) { // si implementaste equals/hashCode en OrdenServicio, evita duplicados
                ordenes.add(o);
            }
        }

        // 3) Generar reporte para ese mes/año
        List<ReporteServicio> datos = ReporteServiciosUC.generar(ordenes, anio, mes);
        adapter.setDatos(datos);

        // 4) Total general
        double total = 0.0;
        for (ReporteServicio r : datos) total += r.getTotal();
        tvTotalGeneralServicio.setText("Total general: " + money.format(total));

        if (datos.isEmpty()) {
            Toast.makeText(MainActivity_ReporteServicio.this,
                    "Sin datos para ese período.", Toast.LENGTH_SHORT).show();
        }
    }
}
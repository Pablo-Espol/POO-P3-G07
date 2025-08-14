package com.espol.tecnicentro.Andrea;

import android.os.Bundle;
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
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.DetalleServicio;
import com.espol.tecnicentro.modelo.OrdenServicio;
import com.espol.tecnicentro.modelo.TipoCliente;

import java.text.NumberFormat;
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

    private ControladorBase base;

    private final List<Cliente> empresas = new ArrayList<>();
    private final List<DetalleFacturaItem> items = new ArrayList<>();
    private DetalleFacturaAdapter detalleAdapter;

    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es","EC"));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_factura);

        base = new ControladorBase();
        base.inicializarApp();

        spEmpresa = findViewById(R.id.spEmpresa);
        spMes     = findViewById(R.id.spMes);
        etAnio    = findViewById(R.id.etAnio);
        btnGenerar= findViewById(R.id.btnGenerar);
        rvDetalle = findViewById(R.id.rvDetalle);
        tvTotal   = findViewById(R.id.tvTotal);


        for (Cliente c : base.getListClient()) {
            if (c.getTipoCliente() == TipoCliente.EMPRESARIAL) empresas.add(c);
        }
        ArrayAdapter<String> adEmp = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item,
                toNombres(empresas)
        );
        spEmpresa.setAdapter(adEmp);

        // meses
        String[] meses = new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio",
                "Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
        ArrayAdapter<String> adMes = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, meses);
        spMes.setAdapter(adMes);

        // Recycler
        rvDetalle.setLayoutManager(new LinearLayoutManager(this));
        detalleAdapter = new DetalleFacturaAdapter(items);
        rvDetalle.setAdapter(detalleAdapter);

        btnGenerar.setOnClickListener(v -> generar());
    }

    private List<String> toNombres(List<Cliente> cs) {
        List<String> r = new ArrayList<>();
        for (Cliente c : cs) r.add(c.getNombre());
        return r;
    }

    private void generar() {
        if (empresas.isEmpty()) {
            Toast.makeText(this,"No hay empresas registradas", Toast.LENGTH_SHORT).show();
            return;
        }
        String sAnio = etAnio.getText().toString().trim();
        if (sAnio.isEmpty()) {
            Toast.makeText(this,"Ingrese un año", Toast.LENGTH_SHORT).show();
            return;
        }
        int anio = Integer.parseInt(sAnio);
        int mesIdx = spMes.getSelectedItemPosition() + 1; // 1..12
        Cliente empresa = empresas.get(spEmpresa.getSelectedItemPosition());


        HashMap<String, DetalleFacturaItem> mapa = new HashMap<>();
        double totalServicios = 0.0;

        for (OrdenServicio o : base.getListOrden()) {
            if (!o.getCliente().getIdentificacion().equals(empresa.getIdentificacion())) continue;
            if (o.getFechaServicio().getYear() != anio) continue;
            if (o.getFechaServicio().getMonthValue() != mesIdx) continue;

            for (DetalleServicio d : o.getServicios()) {
                String nombre = d.getServicio().getNombre();
                double precio = d.getServicio().getPrecio();
                int cant = d.getCantidad();

                DetalleFacturaItem it = mapa.get(nombre);
                if (it == null) {
                    it = new DetalleFacturaItem(nombre, cant, precio);
                    mapa.put(nombre, it);
                } else {
                    it.cantidad += cant;
                    // precioUnitario se mantiene (histórico simple)
                }
            }
        }

        // lista adapter
        items.clear();
        items.addAll(mapa.values());

        // total de servicios
        for (DetalleFacturaItem it : items) {
            totalServicios += it.getSubtotal();
        }


        double total = totalServicios + 50.0;

        detalleAdapter.notifyDataSetChanged();
        tvTotal.setText("Total a pagar: " + nf.format(total));

        if (items.isEmpty()) {
            Toast.makeText(this,"No hay órdenes en ese período", Toast.LENGTH_SHORT).show();
        }
    }
}
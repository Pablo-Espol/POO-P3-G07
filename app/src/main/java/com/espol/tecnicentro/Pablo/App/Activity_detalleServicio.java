package com.espol.tecnicentro.Pablo.App;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Pablo.adapters.EseServicioAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.OrdenServicio;

public class Activity_detalleServicio extends AppCompatActivity  {

    private TextView idClientO,idNombreO,idTClientO,idFechaO,idTVehiculoO,idPlacaO,idtotalO;
    private RecyclerView recyclerViewO;
    private EseServicioAdapter adaptador;
    private OrdenServicio esaOrden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_servicio);


        //vinculamos vistas

        idClientO= findViewById(R.id.idClientO);
        idNombreO= findViewById(R.id.idNombreO);
        idTClientO= findViewById(R.id.idTClientO);
        idFechaO= findViewById(R.id.idFechaO);
        idTVehiculoO= findViewById(R.id.idTVehiculoO);
        idPlacaO= findViewById(R.id.idPlacaO);
        idtotalO= findViewById(R.id.idtotalO);

        esaOrden = (OrdenServicio) getIntent().getSerializableExtra("CadaOrden");


        if (esaOrden!=null){
            idClientO.setText(esaOrden.getCliente().getIdentificacion());
            idNombreO.setText(esaOrden.getCliente().getNombre());
            idTClientO.setText(esaOrden.getCliente().getTipoCliente().toString());
            idFechaO.setText(esaOrden.getFechaServicio().toString());
            idTVehiculoO.setText(esaOrden.getTipoVehiculo().toString());
            idPlacaO.setText(esaOrden.getPlacaVehiculo());
            idtotalO.setText("$" + esaOrden.getTotalOrden());

            llenarLista();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public void cerrar(View view){
        finish();
    }

    private void llenarLista(){

        recyclerViewO= findViewById(R.id.recyclerViewO);
        recyclerViewO.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new EseServicioAdapter(this, esaOrden.getServicios());
        recyclerViewO.setAdapter(adaptador);
    }


    @Override
    protected void onResume() {
        super.onResume();
        llenarLista();
        Log.d("ListaDetallada", "En onResume");//muestra la lista en el log

    }
}
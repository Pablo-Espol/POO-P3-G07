package com.espol.tecnicentro.Pablo.App;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Pablo.adapters.OrdenAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.OrdenServicio;


public class MainActivity_Orden extends AppCompatActivity implements OrdenAdapter.OnOrderDetailsClickListener {
    private RecyclerView recyclerViewOrden;
    private OrdenAdapter ordenAdapter;

    private ControladorBase controladorBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_orden);

        llenarLista();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button btnCrearOrden = findViewById(R.id.btnCrearOrden);

        btnCrearOrden.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Orden.this, Crear_orden.class);
            startActivity(intent);
        });

    }
    @Override
    public void onDetailsClick(OrdenServicio orden, int position) {
        // Envia a la activity de esa orden
        Intent intent = new Intent(this, Activity_detalleServicio.class);
        intent.putExtra("Orden", orden);
        startActivity(intent);

    }

    private void llenarLista(){
        recyclerViewOrden = findViewById(R.id.recyclerViewOrden);
        recyclerViewOrden.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador



        ordenAdapter = new OrdenAdapter(ControladorBase.getInstance().getListOrden(), this,this);
        recyclerViewOrden.setAdapter(ordenAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        llenarLista();
        Log.d("AppOrdenes", "En onResume");//muestra la lista en el log

    }

}
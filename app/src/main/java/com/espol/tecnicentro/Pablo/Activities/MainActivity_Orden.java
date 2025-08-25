package com.espol.tecnicentro.Pablo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.util.ArrayList;


public class MainActivity_Orden extends AppCompatActivity implements OrdenAdapter.OnOrderDetailsClickListener {
    private RecyclerView recyclerViewOrden;
    private OrdenAdapter ordenAdapter;

    private ArrayList<OrdenServicio> listaOrdenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_orden);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Viajamos al activity crear Orden
        Button btnCrearOrden = findViewById(R.id.btnCrearOrden);

        btnCrearOrden.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Orden.this, Crear_orden.class);
            startActivity(intent);
        });

    }
    @Override
    public void onDetailsClick(OrdenServicio orden, int position) {
        // Envia a la activity de esa orden
        Intent intent = new Intent(this, Activity_Detalle_Orden.class);
        intent.putExtra("CadaOrden", orden);
        startActivity(intent);

    }

    //metodo para llenar el recycler view constantemente en el onResume
    private void llenarLista(){
        recyclerViewOrden = findViewById(R.id.recyclerViewOrden);
        recyclerViewOrden.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador

        try {
            listaOrdenes = OrdenServicio.cargarOrdenes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("AppOrdenes","Datos leidos desde el archivo");
        }catch (Exception e){
            listaOrdenes = OrdenServicio.obtenerOrdenes();
            Log.d("AppOrdenes", "Error al cargar datos"+e.getMessage());

        }
        Log.d("AppOrdenes", listaOrdenes.toString());//muestra la lista en el log

        ordenAdapter = new OrdenAdapter(listaOrdenes, this,this);
        recyclerViewOrden.setAdapter(ordenAdapter);
    }



    @Override
    protected void onResume() {
        super.onResume();
        llenarLista();
        Log.d("AppOrdenes", "En onResume");//muestra la lista en el log

    }

}
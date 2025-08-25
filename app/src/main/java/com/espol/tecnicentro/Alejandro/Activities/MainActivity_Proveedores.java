package com.espol.tecnicentro.Alejandro.Activities;

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


import com.espol.tecnicentro.Alejandro.adapters.ProveedorAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.modelo.Proveedor;

import java.util.ArrayList;

public class MainActivity_Proveedores extends AppCompatActivity{
    private RecyclerView recyclerViewProveedor;
    private ProveedorAdapter proveedorAdapter;
    private ArrayList<Proveedor> listaProveedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_proveedores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAgProv = findViewById(R.id.btnAgProv);

        btnAgProv.setOnClickListener(view -> {
            Intent intent = new Intent(this, Agg_Proveedores.class);
            startActivity(intent);
        });
    }

    //Metodo para llenar el recyclerview constantemente utilizado en OnResume
    private void llenarLista(){
        recyclerViewProveedor = findViewById(R.id.recyclerViewProveedor);
        recyclerViewProveedor.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador y deserializacion
        try{
            listaProveedores = Proveedor.cargaProveedores(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("AppProveedor","Datos leidos desde el archivo");
        }catch (Exception e){
            listaProveedores= DatosBase.getInstance().getListSuplier();
            Log.d("AppProveedor", "Error al cargar datos"+e.getMessage());
        }
        proveedorAdapter = new ProveedorAdapter(listaProveedores, this);
        recyclerViewProveedor.setAdapter(proveedorAdapter);
    }

    //Encargado de mostras "algo" constantemente en la pantalla
    @Override
    protected void onResume() {
        super.onResume();
        llenarLista();
        Log.d("AppProveedores", "En onResume");
    }
}
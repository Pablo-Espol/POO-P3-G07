package com.espol.tecnicentro.Alejandro;

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


import com.espol.tecnicentro.Alejandro.adapters.ProveedorAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.Proveedor;

public class MainActivity_Proveedores extends AppCompatActivity implements ProveedorAdapter.OnProveedorEditClickListener {
    private RecyclerView recyclerViewProveedor;
    private ProveedorAdapter proveedorAdapter;

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
    private void llenarLista(){
        recyclerViewProveedor = findViewById(R.id.recyclerViewProveedor);
        recyclerViewProveedor.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador



        proveedorAdapter = new ProveedorAdapter(ControladorBase.getInstance().getListSuplier(), this,this);
        recyclerViewProveedor.setAdapter(proveedorAdapter);
    }

    @Override
    public void onEditClick(Proveedor proveedor , int position){

    }
    @Override
    protected void onResume() {
        super.onResume();
        llenarLista();
        Log.d("AppProveedores", "En onResume");
    }
}
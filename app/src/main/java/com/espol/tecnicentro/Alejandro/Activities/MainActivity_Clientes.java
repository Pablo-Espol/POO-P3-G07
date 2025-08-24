package com.espol.tecnicentro.Alejandro.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Alejandro.adapters.ClienteAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.Servicio;

import java.util.ArrayList;

public class MainActivity_Clientes extends AppCompatActivity {

    private RecyclerView recyclerViewClient;
    private ClienteAdapter clienteAdapter;
    private ArrayList<Cliente> listaClientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_clientes);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //viajamos a la pantalla de Agregar servicios
        Button btnaggClient = findViewById(R.id.btnaggClient);

        btnaggClient.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Clientes.this, Agg_Cliente.class);
            startActivity(intent);
        });

    }
    private void llenarLista(){
        recyclerViewClient = findViewById(R.id.recyclerViewClient);
        recyclerViewClient.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador
        try{
            listaClientes = Cliente.cargarClientes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            Log.d("AppServicios","Datos leidos desde el archivo");
        }catch (Exception e){
            listaClientes= DatosBase.getInstance().getListClient();
            Log.d("AppServicio", "Error al cargar datos"+e.getMessage());
        }

        clienteAdapter = new ClienteAdapter(listaClientes, this);
        recyclerViewClient.setAdapter(clienteAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        llenarLista();
        Log.d("AppClientes", "En onResume");//muestra la lista en el log

    }
}
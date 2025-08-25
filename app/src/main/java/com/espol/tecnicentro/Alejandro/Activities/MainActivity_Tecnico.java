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

import com.espol.tecnicentro.Alejandro.adapters.TecnicoAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Tecnico;

import java.io.File;
import java.util.ArrayList;

public class MainActivity_Tecnico extends AppCompatActivity
        implements ConfirmarEliminarTecnicoDialogFragment.OnTecnicoEliminarListener {

    private RecyclerView recyclerViewTecnico;
    private TecnicoAdapter tecnicoAdapter;
    private ArrayList<Tecnico> listaTecnicos = new ArrayList<>();
    private File dir; // ruta a usar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_tecnicos);

        //extraemos la ruta donde se encuentra la lista
        dir = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

        // Asegura que exista el archivo la primera vez (no sobrescribe si ya existe)
        try { Tecnico.crearDatosIniciales(dir); } catch (Exception e) {
            Log.e("AppTecnico", "Error crearDatosIniciales: " + e.getMessage());
        }

        recyclerViewTecnico = findViewById(R.id.recyclerViewTecnico);
        recyclerViewTecnico.setLayoutManager(new LinearLayoutManager(this));

        llenarLista();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonaggTec = findViewById(R.id.btnaggTec);
        buttonaggTec.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Tecnico.this, Agg_Tecnico.class);
            startActivity(intent);
        });
    }

    private void llenarLista() {
        listaTecnicos = Tecnico.cargarTecnico(dir);
        if (listaTecnicos == null) listaTecnicos = new ArrayList<>();
        Log.d("AppTecnico", "Cargados " + listaTecnicos.size() + " técnicos desde: " + new File(dir, Tecnico.nomArchivoTec).getAbsolutePath());

        //configuramos el adaptador y lo que se mostrará
        tecnicoAdapter = new TecnicoAdapter(listaTecnicos, this);
        recyclerViewTecnico.setAdapter(tecnicoAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recarga datos desde el archivo
        listaTecnicos.clear();
        listaTecnicos.addAll(Tecnico.cargarTecnico(dir));
        tecnicoAdapter.notifyDataSetChanged();
    }


    // Aquí se hace la eliminación del tecnico
    @Override
    public void onTecnicoEliminado(int position) {
        if (position < 0 || position >= listaTecnicos.size()) return;

        Tecnico eliminado = listaTecnicos.remove(position);
        tecnicoAdapter.notifyItemRemoved(position);

        try {
            // guarda SIEMPRE lo que quedó
            Tecnico.guardarLista(dir, new ArrayList<>(listaTecnicos));
            Log.d("AppTecnico", "Eliminado y guardado: " + eliminado.getNombre());
        } catch (Exception e) {
            Log.e("AppTecnico", "Error guardando lista: " + e.getMessage());
        }
    }
}

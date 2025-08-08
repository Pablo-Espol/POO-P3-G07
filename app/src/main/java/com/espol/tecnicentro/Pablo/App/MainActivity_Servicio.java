package com.espol.tecnicentro.Pablo.App;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.Pablo.adapters.ServicioAdapter;
import com.espol.tecnicentro.modelo.Servicio;

import java.util.ArrayList;


public class MainActivity_Servicio extends AppCompatActivity implements ServicioAdapter.OnServicioEditClickListener, EditarServicioDialogFragment.OnServicioEditadoListener{
    private RecyclerView recyclerView;
    private ServicioAdapter adapter;

    private ArrayList<Servicio> listaServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_servicio);

        cargarDatos();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //viajamos a la pantalla de Agregar servicios
        Button btnAggServ = findViewById(R.id.btnAggServ);

        btnAggServ.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Servicio.this, agg_servicio.class);
            startActivity(intent);
        });




    }
    private void llenarLista() {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Configurar el adaptador
            try{
                listaServicios =Servicio.cargarServicio(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
                Log.d("AppServicios","Datos leidos desde el archivo");
            }catch (Exception e){
                listaServicios= Servicio.obtenerServicio();
                Log.d("AppServicio", "Error al cargar datos"+e.getMessage());
            }

            Log.d("AppServicio", listaServicios.toString());//muestra la lista en el log

            adapter = new ServicioAdapter(listaServicios,this,this);
            recyclerView.setAdapter(adapter);
        }

    private void cargarDatos() {
            boolean guardado = false;
            try{
                guardado = Servicio.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

            }catch (Exception e){
                guardado = false;
                Log.d("AppServicios", "Error al crear los datos iniciales"+e.getMessage());
            }
            if (guardado) {
                Log.d("AppServicios", "DATOS INICIALES GUARDADOS");
                //LEER LOS DATOS
            }
        }


    @Override
    public void onResume() {
        super.onResume();
        llenarLista();
        Log.d("AppEmpleados", "En onResume");//muestra la lista en el log

    }

    @Override
    public void onEditClick(Servicio servicio, int position) {
        // Abre el DialogFragment y le pasa el servicio
        EditarServicioDialogFragment dialogFragment = EditarServicioDialogFragment.newInstance(servicio, position);
        dialogFragment.setOnServicioEditadoListener(this); // Asigna el listener a la MainActivity
        dialogFragment.show(getSupportFragmentManager(), "EditarServicioDialogFragment");
    }

    // Implementa el método del listener del DialogFragment
    @Override
    public void onServicioEditado(Servicio servicioActualizado, int position) {
        // Actualiza el servicio en la lista
        listaServicios.set(position, servicioActualizado);

        // Notifica al adaptador que el ítem ha cambiado
        adapter.notifyItemChanged(position);

        // (Opcional) Guarda la lista actualizada en el archivo
        try {
            Servicio.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaServicios);
            Toast.makeText(this, "Servicio actualizado y guardado.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error al guardar los cambios.", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error al guardar lista: " + e.getMessage());
        }
    }

}




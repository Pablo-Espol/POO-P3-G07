package com.espol.tecnicentro.Pablo.App;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.Servicio;

import java.util.ArrayList;

public class Agg_servicio extends AppCompatActivity {
    private ArrayList<Servicio> listaPrincipal= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agg_servicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cerrar(View view){
        finish();
    }

    @SuppressLint("DefaultLocale")
    public void guardar(View view) {


        EditText idServicio_agg = findViewById(R.id.idServicio_agg);
        EditText idPrecio_agg = findViewById(R.id.idPrecio_agg);

        if (idServicio_agg.getText().toString().isEmpty() || idPrecio_agg.getText().toString().isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }


        String nameServ= idServicio_agg.getText().toString();
        double valorSer= Double.parseDouble(idPrecio_agg.getText().toString());

        ArrayList<Servicio> listaServicios = Servicio.cargarServicio(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        if (listaServicios == null) {
            listaServicios = new ArrayList<>();
        }

        String codeLista = "001";  //codigo por defecto si no hay lista
        if (!listaServicios.isEmpty()) {
            Servicio ultimoServicio = listaServicios.get(listaServicios.size() - 1);
            try {
                int codigo = Integer.parseInt(ultimoServicio.getCodigo());
                codeLista = String.format("%03d", codigo + 1);
            } catch (NumberFormatException e) {
                Log.d("AppServicio", "Código inválido, usando '001'");
            }
        }

        Servicio nuevoServicio= new Servicio(codeLista,nameServ,valorSer);


        try {

            Log.d("AppServicios", nuevoServicio.toString());
            listaPrincipal= ControladorBase.getInstance().getListService();
            listaPrincipal.add(nuevoServicio);
            Servicio.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
            Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("AppServicio", "Error al guardar datos: " + e.getMessage());
        }

        finish();
    }



}
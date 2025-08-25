package com.espol.tecnicentro.Alejandro.Activities;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.espol.tecnicentro.modelo.Tecnico;


import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.ListaBase.DatosBase;

import java.util.ArrayList;

public class Agg_Tecnico extends AppCompatActivity {
    private ArrayList<Tecnico> listaPrincipal = new ArrayList<>();
    private EditText editTextIdentificacion, editTextNombre, editTextTelefono, editTextEspecialidad;
    private Button buttonGuardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_tecnicos); // Cambia por el nombre real
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        editTextIdentificacion = findViewById(R.id.editTextPhone3); // en tu xml está con id editTextPhone para identificacion
        editTextNombre = findViewById(R.id.editTextText);
        editTextTelefono = findViewById(R.id.editTextPhone4);
        editTextEspecialidad = findViewById(R.id.editTextText2);
        buttonGuardar = findViewById(R.id.btnaggTec);


        //configuracion del boton de guardar
        buttonGuardar.setOnClickListener(v -> {
            String id = editTextIdentificacion.getText().toString().trim();
            String nombre = editTextNombre.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();
            String especialidad = editTextEspecialidad.getText().toString().trim();

            //verifica que los campos esten llenos
            if(id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()){
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            //carga la lista externa para agregar tecnico, guardar y serializar
            ArrayList<Tecnico> listaTecnicos = Tecnico.cargarTecnico(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

            if (listaTecnicos == null) {
                listaTecnicos = new ArrayList<>();
            }

            Tecnico nuevoTecnico= new Tecnico(id,nombre,telefono,especialidad);
            try {
                listaPrincipal= DatosBase.getInstance().getListTecni();
                listaPrincipal.add(nuevoTecnico);
                Tecnico.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
                Toast.makeText(getApplicationContext(), "Técnico Agregado", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                Log.d("AppTecnico", "Error al guardar datos: " + e.getMessage());

            }


            finish(); // Cierra esta activity y regresa a MainActivity_Tecnico
        });





    }

}


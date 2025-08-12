package com.espol.tecnicentro.Alejandro;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;
import com.espol.tecnicentro.modelo.Tecnico;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.controladores.ControladorTecnico;
import com.espol.tecnicentro.controladores.ControladorTecnico;

public class Agg_Tecnico extends AppCompatActivity {

    private ControladorTecnico controladorTecnico;
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

        // Inicializa el controlador (aquí asumo que tienes una instancia base o la puedes crear)
        controladorTecnico = new ControladorTecnico(new ControladorBase());

        buttonGuardar.setOnClickListener(v -> {
            String id = editTextIdentificacion.getText().toString().trim();
            String nombre = editTextNombre.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();
            String especialidad = editTextEspecialidad.getText().toString().trim();

            if(id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || especialidad.isEmpty()){
                Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("id", id);
            resultIntent.putExtra("nombre", nombre);
            resultIntent.putExtra("telefono", telefono);
            resultIntent.putExtra("especialidad", especialidad);

            setResult(RESULT_OK, resultIntent); // tu método para agregar al controlador

            finish(); // Cierra esta activity y regresa a MainActivity_Tecnico
        });





    }

}


package com.espol.tecnicentro.Alejandro;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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





    }
}


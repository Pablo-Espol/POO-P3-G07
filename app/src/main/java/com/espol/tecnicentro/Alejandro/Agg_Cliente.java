package com.espol.tecnicentro.Alejandro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.controladores.ControladorCliente;

public class Agg_Cliente extends AppCompatActivity {

    private EditText editTextIdentificacion, editTextNombre, editTextTelefono, editTextDireccion;
    private Spinner spinner;
    private Button buttonGuardar;

    private ControladorCliente controladorCliente;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_clientes); // Cambia por el nombre real
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        editTextIdentificacion = findViewById(R.id.editTextPhone); // en tu xml está con id editTextPhone para identificacion
        editTextNombre = findViewById(R.id.editTextText5);
        editTextTelefono = findViewById(R.id.editTextPhone2);
        editTextDireccion = findViewById(R.id.editTextText6);
        spinner = (Spinner) findViewById(R.id.spinner);
        buttonGuardar = findViewById(R.id.button);

        // Inicializa el controlador (aquí asumo que tienes una instancia base o la puedes crear)
        controladorCliente = new ControladorCliente(new ControladorBase());


        String [] opciones= { "PERSONAL", "EMPRESARIAL"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);


    }
}
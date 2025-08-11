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
import com.espol.tecnicentro.controladores.ControladorProveedor;
import com.espol.tecnicentro.controladores.ControladorTecnico;

public class Agg_Proveedores extends AppCompatActivity {

    private EditText editTextIdentificacion,editTextNombre, editTextTelefono, editTextDescripcion;
    private Button buttonGuardar;
    private ControladorProveedor controladorProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_proveedores); // Cambia por el nombre real
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        editTextIdentificacion = findViewById(R.id.editTextPhone5); // en tu xml está con id editTextPhone para identificacion

        editTextNombre = findViewById(R.id.editTextText4);
        editTextTelefono = findViewById(R.id.editTextPhone6);
        editTextDescripcion = findViewById(R.id.editTextTextMultiLine2);

        buttonGuardar = findViewById(R.id.button2);

        // Inicializa el controlador (aquí asumo que tienes una instancia base o la puedes crear)
        controladorProveedor = new ControladorProveedor(new ControladorBase());





    }

}

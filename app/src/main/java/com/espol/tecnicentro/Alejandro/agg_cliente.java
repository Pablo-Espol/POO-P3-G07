package com.espol.tecnicentro.Alejandro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.controladores.ControladorCliente;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.TipoCliente;

import java.util.ArrayList;

public class agg_cliente extends AppCompatActivity {

    private EditText editTextIdentificacion, editTextNombre, editTextTelefono, editTextDireccion;
    private Spinner spinnerTipoCliente;
    private Button buttonGuardar;

    private ControladorCliente controladorCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_clientes); // Cambia por el nombre real

        // Inicializar vistas
        editTextIdentificacion = findViewById(R.id.editTextPhone); // en tu xml está con id editTextPhone para identificacion
        editTextNombre = findViewById(R.id.editTextText5);
        editTextTelefono = findViewById(R.id.editTextPhone2);
        editTextDireccion = findViewById(R.id.editTextText6);
        spinnerTipoCliente = findViewById(R.id.spinner);
        buttonGuardar = findViewById(R.id.button);

        // Inicializa el controlador (aquí asumo que tienes una instancia base o la puedes crear)
        controladorCliente = new ControladorCliente(new ControladorBase());


}

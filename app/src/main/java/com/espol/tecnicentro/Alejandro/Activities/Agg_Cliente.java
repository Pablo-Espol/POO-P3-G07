package com.espol.tecnicentro.Alejandro.Activities;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.TipoCliente;

import java.util.ArrayList;

public class Agg_Cliente extends AppCompatActivity {

    private EditText editTextIdentificacion, editTextNombre, editTextTelefono, editTextDireccion;
    private Spinner spinner;
    private Button button;
    private ArrayList<Cliente> listaPrincipal= new ArrayList<>();


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
        button = findViewById(R.id.button);


        String [] opciones= { "PERSONAL", "EMPRESARIAL"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);

        button.setOnClickListener(view -> {


            if (editTextIdentificacion.getText().toString().isEmpty() ||editTextIdentificacion.getText().toString().isEmpty()||editTextTelefono.getText().toString().isEmpty()||editTextDireccion.getText().toString().isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }


            String nameId= editTextIdentificacion.getText().toString();
            String nameCli= editTextNombre.getText().toString();
            String nameTel= editTextTelefono.getText().toString();
            String nameDir= editTextDireccion.getText().toString();
            TipoCliente nameTipo= spinner.getSelectedItem().toString().equals("PERSONAL")?TipoCliente.PERSONAL:TipoCliente.EMPRESARIAL;

            ArrayList<Cliente> listaClientes = Cliente.cargaClientes(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

            if (listaClientes == null) {
                listaClientes = new ArrayList<>();
            }

            Cliente nuevoCliente = new Cliente(nameId,nameCli,nameTel,nameDir,nameTipo);

            try {
                Log.d("AppCliente", nuevoCliente.toString());
                listaPrincipal= DatosBase.getInstance().getListClient();
                listaPrincipal.add(nuevoCliente);
                Cliente.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("AppCliente", "Error al guardar datos: " + e.getMessage());
            }

            finish();

        });


    }


}
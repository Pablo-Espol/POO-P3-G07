package com.espol.tecnicentro.Alejandro.Activities;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.ListaBase.DatosBase;
import com.espol.tecnicentro.modelo.Proveedor;

import java.util.ArrayList;

public class Agg_Proveedores extends AppCompatActivity {

    private EditText editTextIdentificacion,editTextNombre, editTextTelefono, editTextDescripcion;
    private Button buttonGuardar;
    private ArrayList<Proveedor> listaPrincipal= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agg_proveedores); // Cambia por el nombre real
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Encontrarlos objetos de la interfaz
        editTextIdentificacion = findViewById(R.id.editTextPhone5);
        editTextNombre = findViewById(R.id.editTextText4);
        editTextTelefono = findViewById(R.id.editTextPhone6);
        editTextDescripcion = findViewById(R.id.editTextTextMultiLine2);
        buttonGuardar = findViewById(R.id.button2);


        buttonGuardar.setOnClickListener(view -> {

            //verifica que todos los campos esten llenos
            if (editTextIdentificacion.getText().toString().isEmpty()||editTextTelefono.getText().toString().isEmpty()||editTextNombre.getText().toString().isEmpty() ||editTextDescripcion.getText().toString().isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            //getteamos los datos ingresados
            String idProve= editTextIdentificacion.getText().toString();
            String nameProve= editTextNombre.getText().toString();
            String nameTel= editTextTelefono.getText().toString();
            String Descrip= editTextDescripcion .getText().toString();

            //cargamos la lista externa y agregamos el nuevo proveedor y serializamos
            ArrayList<Proveedor> listaProveedor= Proveedor.cargaProveedores(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
            if (listaProveedor == null) {
                listaProveedor = new ArrayList<>();
            }

            Proveedor nuevoProveedor = new Proveedor(idProve,nameProve,nameTel,Descrip);

            try {
                Log.d("AppProveedor", nuevoProveedor.toString());
                listaPrincipal= DatosBase.getInstance().getListSuplier();
                listaPrincipal.add(nuevoProveedor);
                Proveedor.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
                Toast.makeText(getApplicationContext(), "Proveedor Agregado", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("AppProveedor", "Error al guardar datos: " + e.getMessage());
            }

            finish();

        });


    }







}



package com.espol.tecnicentro.Alejandro;
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
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.controladores.ControladorProveedor;
import com.espol.tecnicentro.controladores.ControladorTecnico;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.Proveedor;
import com.espol.tecnicentro.modelo.TipoCliente;

import java.util.ArrayList;

public class Agg_Proveedores extends AppCompatActivity {

    private EditText editTextIdentificacion,editTextNombre, editTextTelefono, editTextDescripcion;
    private Button buttonGuardar;
    private ControladorProveedor controladorProveedor;
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

        // Inicializar vistas
        editTextIdentificacion = findViewById(R.id.editTextPhone5); // en tu xml está con id editTextPhone para identificacion

        editTextNombre = findViewById(R.id.editTextText4);
        editTextTelefono = findViewById(R.id.editTextPhone6);
        editTextDescripcion = findViewById(R.id.editTextTextMultiLine2);

        buttonGuardar = findViewById(R.id.button2);

        // Inicializa el controlador (aquí asumo que tienes una instancia base o la puedes crear)
        controladorProveedor = new ControladorProveedor(new ControladorBase());
        Button button = findViewById(R.id.button2);

        button.setOnClickListener(view -> {


            if (editTextIdentificacion.getText().toString().isEmpty()||editTextTelefono.getText().toString().isEmpty()||editTextNombre.getText().toString().isEmpty() ||editTextDescripcion.getText().toString().isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }


            String idProve= editTextIdentificacion.getText().toString();
            String nameProve= editTextNombre.getText().toString();
            String nameTel= editTextTelefono.getText().toString();
            String Descrip= editTextDescripcion .getText().toString();
            ArrayList<Proveedor> listaProveedor= Proveedor.cargaProveedores(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));


            if (listaProveedor == null) {
                listaProveedor = new ArrayList<>();
            }

            Proveedor nuevoProveedor = new Proveedor(idProve,nameProve,nameTel,Descrip);





            try {


                Log.d("AppServicios", nuevoProveedor.toString());
                listaPrincipal= ControladorBase.getInstance().getListSuplier();
                listaPrincipal.add(nuevoProveedor);
                Proveedor.guardarLista(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), listaPrincipal);
                Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.d("AppServicio", "Error al guardar datos: " + e.getMessage());
            }

            finish();

        });


    }







}



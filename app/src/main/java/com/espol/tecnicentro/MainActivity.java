package com.espol.tecnicentro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.tecnicentro.Alejandro.Activities.MainActivity_Clientes;
import com.espol.tecnicentro.Alejandro.Activities.MainActivity_Proveedores;
import com.espol.tecnicentro.Alejandro.Activities.MainActivity_Tecnico;
import com.espol.tecnicentro.Andrea.MainActivity_FacturaEmpresarial;
import com.espol.tecnicentro.Andrea.MainActivity_ReporteServicio;
import com.espol.tecnicentro.Andrea.MainActivity_ReporteTecnico;
import com.espol.tecnicentro.Pablo.Activities.MainActivity_Orden;
import com.espol.tecnicentro.Pablo.Activities.MainActivity_Servicio;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.OrdenServicio;
import com.espol.tecnicentro.modelo.Proveedor;
import com.espol.tecnicentro.modelo.Servicio;
import com.espol.tecnicentro.modelo.Tecnico;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //cargamos todos los datos  en el celular(serialziados)
        cargarDatosServicios();
        cargarDatosOrdenes();
        cargarDatosClientes();
        cargarDatosTecnicos();
        cargarDatosProveedores();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        Button btnMainServ = findViewById(R.id.btnMainServ);
        btnMainServ.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_Servicio.class);
            startActivity(intent);
        });

        Button btnMainClient = findViewById(R.id.btnMainClient);
        btnMainClient.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_Clientes.class);
            startActivity(intent);
        });
        Button btnMainProv = findViewById(R.id.btnMainProv);
        btnMainProv.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_Proveedores.class);
            startActivity(intent);
        });
        Button btnMainTec = findViewById(R.id.btnMainTec);
        btnMainTec.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_Tecnico.class);
            startActivity(intent);
        });
        Button btnMainOrden = findViewById(R.id.btnMainOrden);
        btnMainOrden.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_Orden.class);
            startActivity(intent);
        });
        Button btnMainFact = findViewById(R.id.btnMainFact);
        btnMainFact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_FacturaEmpresarial.class);
            startActivity(intent);
        });
        Button btnMainRServ = findViewById(R.id.btnMainRServ);
        btnMainRServ.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_ReporteServicio.class);
            startActivity(intent);
        });
        Button btnMainRTec = findViewById(R.id.btnMainRTec);
        btnMainRTec.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MainActivity_ReporteTecnico.class);
            startActivity(intent);
        });

    }







    private void cargarDatosServicios() {
        boolean guardado = false;
        try{
            guardado = Servicio.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        }catch (Exception e){
            guardado = false;
            Log.d("AppServicios", "Error al crear los datos iniciales"+e.getMessage());
        }
        if (guardado) {
            Log.d("AppServicios", "DATOS INICIALES GUARDADOS");
            //LEER LOS DATOS
        }
    }

    private void cargarDatosOrdenes() {
        boolean guardado = false;
        try{
            guardado = OrdenServicio.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        }catch (Exception e){
            guardado = false;
            Log.d("AppOrdenes", "Error al crear los datos iniciales"+e.getMessage());
        }
        if (guardado) {
            Log.d("AppOrdenes", "DATOS INICIALES GUARDADOS");
            //LEER LOS DATOS
        }
    }

    private void cargarDatosClientes() {
        boolean guardado = false;
        try{
            guardado = Cliente.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        }catch (Exception e){
            guardado = false;
            Log.d("AppClientes", "Error al crear los datos iniciales"+e.getMessage());
        }
        if (guardado) {
            Log.d("AppClientes", "DATOS INICIALES GUARDADOS");
            //LEER LOS DATOS
        }
    }

    private void cargarDatosTecnicos() {
        boolean guardado = false;
        try{
            guardado = Tecnico.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        }catch (Exception e){
            guardado = false;
            Log.d("AppServicios", "Error al crear los datos iniciales"+e.getMessage());
        }
        if (guardado) {
            Log.d("AppServicios", "DATOS INICIALES GUARDADOS");
            //LEER LOS DATOS
        }
    }

    private void cargarDatosProveedores() {
        boolean guardado = false;
        try{
            guardado = Proveedor.crearDatosIniciales(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));

        }catch (Exception e){
            guardado = false;
            Log.d("AppProveedor", "Error al crear los datos iniciales"+e.getMessage());
        }
        if (guardado) {
            Log.d("AppProveedor", "DATOS INICIALES GUARDADOS");
            //LEER LOS DATOS
        }
    }
}
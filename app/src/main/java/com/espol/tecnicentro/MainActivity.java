package com.espol.tecnicentro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.espol.tecnicentro.Alejandro.MainActivity_Clientes;
import com.espol.tecnicentro.Alejandro.MainActivity_Proveedores;
import com.espol.tecnicentro.Alejandro.MainActivity_Tecnico;
import com.espol.tecnicentro.Andrea.MainActivity_FacturaEmpresarial;
import com.espol.tecnicentro.Andrea.MainActivity_ReporteServicio;
import com.espol.tecnicentro.Andrea.MainActivity_ReporteTecnico;
import com.espol.tecnicentro.Pablo.App.MainActivity_Orden;
import com.espol.tecnicentro.Pablo.App.MainActivity_Servicio;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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


}
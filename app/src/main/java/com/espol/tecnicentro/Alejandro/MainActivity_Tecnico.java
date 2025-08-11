package com.espol.tecnicentro.Alejandro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Alejandro.adapters.ClienteAdapter;
import com.espol.tecnicentro.Alejandro.adapters.TecnicoAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.controladores.ControladorBase;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.Tecnico;

public class MainActivity_Tecnicos extends AppCompatActivity {

    private RecyclerView recyclerViewTecnico;
    private TecnicoAdapter tecnicoAdapter;
    private ControladorBase control2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_tecnicos);
        control2= new ControladorBase();
        control2.inicializarApp();
        llenarlista();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnaggClient = findViewById(R.id.button2);

        btnaggClient.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Tecnicos.this, Agg_Cliente.class);
            startActivity(intent);
        });
    }

    private void llenarlista(){
        recyclerViewTecnico= findViewById(R.id.recyclerViewTecnico);
        recyclerViewTecnico.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador



        tecnicoAdapter = new TecnicoAdapter(control2.getListTecni(),this,this);
        recyclerViewTecnico.setAdapter(tecnicoAdapter);
    }
    }
@Override
public void onEditClick(Tecnico tecnico, int position){


}

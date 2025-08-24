package com.espol.tecnicentro.Alejandro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;

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

public class MainActivity_Tecnico extends AppCompatActivity implements TecnicoAdapter.OnTecnicoEditClickListener{
    private RecyclerView recyclerViewTecnico;
    private TecnicoAdapter tecnicoAdapter;
    private ControladorBase control;
    private Tecnico tecnico;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_tecnicos);


        llenarLista();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button buttonaggTec = findViewById(R.id.btnaggTec);

        buttonaggTec.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity_Tecnico.this, Agg_Tecnico.class);
            startActivityForResult(intent, 1);


        });


    }




    private void llenarLista(){
        recyclerViewTecnico = findViewById(R.id.recyclerViewTecnico);
        recyclerViewTecnico.setLayoutManager(new LinearLayoutManager(this));

        //Configuramos el adaptador



        tecnicoAdapter = new TecnicoAdapter(ControladorBase.getInstance().getListTecni(), this,this);
        recyclerViewTecnico.setAdapter(tecnicoAdapter);
    }



    @Override
    public void onEditClick(Tecnico tecnico, int position){


    }
    @Override
    public void onResume(){
        super.onResume();
        llenarLista();

        Log.d("AppTecnicos", "En onResume");//muestra la lista en el log
        tecnicoAdapter.notifyDataSetChanged();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // El nuevo técnico fue agregado, actualizamos la lista
            llenarLista();
            tecnicoAdapter.notifyDataSetChanged();
        }
    }


}

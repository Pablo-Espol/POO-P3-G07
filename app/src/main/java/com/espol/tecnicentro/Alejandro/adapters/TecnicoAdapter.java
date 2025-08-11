package com.espol.tecnicentro.Alejandro.adapters;
import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.Tecnico;

import java.util.List;

public class TecnicoAdapter extends RecyclerView.Adapter<TecnicoAdapter.ViewHolder>{
    private List<Tecnico> tecnicoList;
    private Activity activity;
    private OnTecnicoEditClickListener listener;

    public TecnicoAdapter(List<Tecnico> tecnicoList, Activity activity, TecnicoAdapter.OnTecnicoEditClickListener listener) {
        this.tecnicoList = tecnicoList;
        this.activity = activity;
        this.listener = listener;
    }

    public interface OnTecnicoEditClickListener {
        void onEditClick(Tecnico tecnico, int position); // Asegúrate que los parámetros coincidan con lo que necesitas
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTecnico,nombreTec,phoneTec,especialidad;
        Button btneditTecnico,btneelimTec ;

        public ViewHolder(View itemView) {
            super(itemView);
            idTecnico = itemView.findViewById(R.id.idTecnico);   // tu id de identificación
            nombreTec = itemView.findViewById(R.id.nombreTec);          // tu id de nombre
            phoneTec= itemView.findViewById(R.id.phoneTec);       // tu id de teléfono
            especialidad = itemView.findViewById(R.id.especialidad);// tu id de dirección
            btneditTecnico = itemView.findViewById(R.id.btneditTecnico);
            btneelimTec = itemView.findViewById(R.id.btneelimTec);// botón guardar (o crea otro botón "editar" para fila)
        }
    }
    @Override
    public TecnicoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tecnico, parent, false); // reemplaza con tu layout de fila
        return new ViewHolder(vista);
    }
    @Override
    public void onBindViewHolder(TecnicoAdapter.ViewHolder holder, int position) {
        Tecnico tecnico = tecnicoList.get(position);

        holder.idTecnico.setText("Identificacion: " + tecnico.getIdentificacion());
        holder.nombreTec.setText("Nombre: "+ tecnico.getNombre());
        holder.phoneTec.setText("Telefono: "+tecnico.getTelefono());
        holder.especialidad.setText("Direccion: "+tecnico.getEspecialidad());
        holder.btneditTecnico.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(tecnico, holder.getAdapterPosition());
            }
        });





    }

    @Override
    public int getItemCount() {
        return tecnicoList != null ? tecnicoList.size() : 0; // Buena práctica añadir esto
    }
}

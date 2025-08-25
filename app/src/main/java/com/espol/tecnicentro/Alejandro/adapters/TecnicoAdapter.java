package com.espol.tecnicentro.Alejandro.adapters;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Alejandro.Activities.ConfirmarEliminarTecnicoDialogFragment;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Tecnico;

import java.util.List;

public class TecnicoAdapter extends RecyclerView.Adapter<TecnicoAdapter.ViewHolder> {
    private List<Tecnico> tecnicoList;
    private Activity activity;
    private OnTecnicoEditClickListener listener;

    public TecnicoAdapter(List<Tecnico> tecnicoList, Activity activity) {
        this.tecnicoList = tecnicoList;
        this.activity = activity;
    }

    public interface OnTecnicoEditClickListener {
        void onEditClick(Tecnico tecnico, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTecnico, nombreTec, phoneTec, especialidad;
        Button  btneelimTec;

        public ViewHolder(View itemView) {
            super(itemView);
            idTecnico = itemView.findViewById(R.id.idTecnico);
            nombreTec = itemView.findViewById(R.id.nombreTec);
            phoneTec = itemView.findViewById(R.id.phoneTec);
            especialidad = itemView.findViewById(R.id.especialidad);

            btneelimTec = itemView.findViewById(R.id.btneelimTec);
        }
    }

    @Override
    public TecnicoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tecnico, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(TecnicoAdapter.ViewHolder holder, int position) {
        Tecnico tecnico = tecnicoList.get(position);

        holder.idTecnico.setText("Identificación: " + tecnico.getIdentificacion());
        holder.nombreTec.setText("Nombre: " + tecnico.getNombre());
        holder.phoneTec.setText("Teléfono: " + tecnico.getTelefono());
        holder.especialidad.setText("Especialidad: " + tecnico.getEspecialidad());


        // Botón Eliminar
        holder.btneelimTec.setOnClickListener(v -> {
            ConfirmarEliminarTecnicoDialogFragment dialog =
                    ConfirmarEliminarTecnicoDialogFragment.newInstance(tecnico, holder.getAdapterPosition());

            // Pasa el callback a la Activity para que ELLA elimine y GUARDE el .ser
            if (activity instanceof ConfirmarEliminarTecnicoDialogFragment.OnTecnicoEliminarListener) {
                dialog.setOnTecnicoEliminarListener(
                        (ConfirmarEliminarTecnicoDialogFragment.OnTecnicoEliminarListener) activity
                );
            }

            dialog.show(
                    ((FragmentActivity) activity).getSupportFragmentManager(),
                    "ConfirmarEliminarTecnico"
            );
        });

    }

    @Override
    public int getItemCount() {
        return tecnicoList != null ? tecnicoList.size() : 0;
    }
}

package com.espol.tecnicentro.Pablo.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Servicio;

import java.util.List;

public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {

    private  List<Servicio> listaServicios;
    private Context context;
    private Activity activity;


    private  OnServicioEditClickListener listener;


    public ServicioAdapter(List<Servicio> listaServicios, Activity activity) {
        this.listaServicios = listaServicios;
        this.activity=activity;
    }

    public ServicioAdapter(List<Servicio> listaServicios, Activity activity, OnServicioEditClickListener listener) {
        this.listaServicios = listaServicios;
        this.activity = activity;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCodigo, tvNombre, tvPrecio;
        private Button btnEditar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            btnEditar =itemView.findViewById(R.id.btnEditar);


        }
    }

    @Override
    public ServicioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Servicio servicio = listaServicios.get(position);
        holder.tvCodigo.setText("Código: " + servicio.getCodigo());
        holder.tvNombre.setText("Nombre: " + servicio.getNombre());
        holder.tvPrecio.setText("Precio: $" + servicio.getPrecio());


        // Manejar el clic en el botón de editar
        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(servicio, position); // <-- Modifica el método para pasar la posición
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    public interface OnServicioEditClickListener {
        void onEditClick(Servicio servicio, int position);
    }

}


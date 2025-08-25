package com.espol.tecnicentro.Pablo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.DetalleServicio;

import java.util.ArrayList;

public class EseServicioAdapter extends RecyclerView.Adapter<EseServicioAdapter.ViewHolder>{

    private Activity activity;
    private ArrayList<DetalleServicio> listaDetalles;

    public EseServicioAdapter(Activity activity, ArrayList<DetalleServicio> listaDetalles) {
        this.activity = activity;
        this.listaDetalles = listaDetalles;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServicio, tvCantidad, tvPrecio;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);

        }
    }

    @NonNull
    @Override
    public EseServicioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_servicios,parent,false);
        return new ViewHolder(vista);
    }

    //lo que se mostrara en el item de cada servicio  y su subtotal en el Activity_detalle_orden
    @Override
    public void onBindViewHolder(@NonNull EseServicioAdapter.ViewHolder holder, int position) {

        DetalleServicio cadaServicio = listaDetalles.get(position);
        holder.tvServicio.setText(cadaServicio.getServicio().getNombre());
        holder.tvCantidad.setText(String.valueOf(cadaServicio.getCantidad()));
        holder.tvPrecio.setText(String.valueOf("$"+cadaServicio.getSubtotal()));
    }

    @Override
    public int getItemCount() {
        return listaDetalles.size();
    }
}

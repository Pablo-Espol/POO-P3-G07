package com.espol.tecnicentro.Andrea.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Andrea.ReporteServicio;
import com.espol.tecnicentro.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ReporteServicioAdapter extends RecyclerView.Adapter<ReporteServicioAdapter.ViewHolder> {

    private List<ReporteServicio> listaServicios;
    private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("es","EC"));

    public ReporteServicioAdapter(List<ReporteServicio> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public void setDatos(List<ReporteServicio> nuevosDatos) {
        this.listaServicios = nuevosDatos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reporte_servicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReporteServicio servicio = listaServicios.get(position);
        holder.tvServicio.setText(servicio.getServicio());
        holder.tvTotal.setText(money.format(servicio.getTotal()));
    }

    @Override
    public int getItemCount() {
        return (listaServicios == null) ? 0 : listaServicios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServicio, tvTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
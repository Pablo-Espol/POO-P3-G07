package com.espol.tecnicentro.Andrea.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.Andrea.ReporteTecnico;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReporteTecnicoAdapter extends RecyclerView.Adapter<ReporteTecnicoAdapter.VH> {

    private final List<ReporteTecnico> datos = new ArrayList<>();
    private final NumberFormat moneda = NumberFormat.getCurrencyInstance(new Locale("es","EC"));

    public void setDatos(List<ReporteTecnico> nuevos) {
        datos.clear();
        if (nuevos != null) datos.addAll(nuevos);
        notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reporte_tecnico, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int position) {
        ReporteTecnico r = datos.get(position);
        h.tvTecnico.setText(r.getTecnico());
        h.tvTotal.setText(moneda.format(r.getTotal()));
    }

    @Override public int getItemCount() { return datos.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTecnico, tvTotal;
        VH(@NonNull View itemView) {
            super(itemView);
            tvTecnico = itemView.findViewById(R.id.tvTecnico);
            tvTotal   = itemView.findViewById(R.id.tvTotal);
        }
    }
}
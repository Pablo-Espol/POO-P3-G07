package com.espol.tecnicentro.Andrea.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Andrea.GenerarFacturaActivity;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.ViewHolder> {

    private final List<OrdenServicio> facturas;
    private final NumberFormat nfMoneda = NumberFormat.getCurrencyInstance(new Locale("es","EC"));
    private final Locale es = new Locale("es","ES");

    public FacturaAdapter(List<OrdenServicio> facturas) {
        this.facturas = (facturas == null) ? new ArrayList<>() : facturas;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_generar_facturas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        OrdenServicio orden = facturas.get(position);

        String nombreEmpresa = (orden.getCliente() != null)
                ? orden.getCliente().getNombre() : "(Sin empresa)";
        h.tvEmpresa.setText("Empresa: " + nombreEmpresa);

        if (orden.getFechaServicio() != null) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaCreacion = orden.getFechaServicio().format(fmt);

            String mes = orden.getFechaServicio().getMonth().getDisplayName(TextStyle.FULL, es);
            mes = Character.toUpperCase(mes.charAt(0)) + mes.substring(1);
            String periodo = mes + " " + orden.getFechaServicio().getYear();

            h.tvFecha.setText("Fecha de creación: " + fechaCreacion);
            h.tvPeriodo.setText("Periodo: " + periodo);
        } else {
            h.tvFecha.setText("Fecha de creación: —");
            h.tvPeriodo.setText("Periodo: —");
        }

        h.tvTotal.setText("Total a pagar: " + nfMoneda.format(orden.getTotalOrden()));

        // ===== Abrir GenerarFacturaActivity prellenada =====
        h.btnDetalle.setOnClickListener(v -> {
            if (orden.getCliente() == null || orden.getFechaServicio() == null) return;

            Intent i = new Intent(v.getContext(), GenerarFacturaActivity.class);
            i.putExtra("empresaId", orden.getCliente().getIdentificacion());
            i.putExtra("anio",      orden.getFechaServicio().getYear());
            i.putExtra("mes",       orden.getFechaServicio().getMonthValue()); // 1..12
            v.getContext().startActivity(i);
        });
    }

    @Override public int getItemCount() { return facturas.size(); }

    public void setData(List<OrdenServicio> nuevas) {
        facturas.clear();
        if (nuevas != null) facturas.addAll(nuevas);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmpresa, tvFecha, tvPeriodo, tvTotal;
        Button btnDetalle;
        public ViewHolder(@NonNull View v) {
            super(v);
            tvEmpresa  = v.findViewById(R.id.tvEmpresa);
            tvFecha    = v.findViewById(R.id.tvFecha);
            tvPeriodo  = v.findViewById(R.id.tvPeriodo);
            tvTotal    = v.findViewById(R.id.tvTotal);
            btnDetalle = v.findViewById(R.id.btnMasDetalle);
        }
    }
}
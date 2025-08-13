package com.espol.tecnicentro.Andrea.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;


public class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.ViewHolder> {

    private final List<OrdenServicio> facturas;
    private final SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
    private final SimpleDateFormat sdfPeriodo = new SimpleDateFormat("MMMM yyyy", new Locale("es", "ES"));
    private final NumberFormat nfMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "EC")); // USD con formato local

    public FacturaAdapter(List<OrdenServicio> facturas) {

        this.facturas = (facturas == null) ? new ArrayList<>() : facturas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_generar_facturas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrdenServicio orden = facturas.get(position);

        String nombreEmpresa = (orden.getCliente() != null) ? orden.getCliente().getNombre() : "(Sin empresa)";
        holder.tvEmpresa.setText("Empresa: " + nombreEmpresa);

        if (orden.getFechaServicio() != null) {

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaCreacion = orden.getFechaServicio().format(fmt);

            Locale es = new Locale("es", "ES");
            String mes = orden.getFechaServicio().getMonth().getDisplayName(TextStyle.FULL, es);
            mes = Character.toUpperCase(mes.charAt(0)) + mes.substring(1);
            String periodo = mes + " " + orden.getFechaServicio().getYear();

            holder.tvFecha.setText("Fecha de creación: " + fechaCreacion);
            holder.tvPeriodo.setText("Periodo: " + periodo);
        } else {
            holder.tvFecha.setText("Fecha de creación: —");
            holder.tvPeriodo.setText("Periodo: —");
        }

        holder.tvTotal.setText("Total a pagar: $" + orden.getTotalOrden());
    }

    @Override
    public int getItemCount() {
        return facturas.size();
    }

    public void setData(List<OrdenServicio> nuevas) {
        facturas.clear();
        if (nuevas != null) facturas.addAll(nuevas);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmpresa, tvFecha, tvPeriodo, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmpresa = itemView.findViewById(R.id.tvEmpresa);
            tvFecha   = itemView.findViewById(R.id.tvFecha);
            tvPeriodo = itemView.findViewById(R.id.tvPeriodo);
            tvTotal   = itemView.findViewById(R.id.tvTotal);
        }
    }
}
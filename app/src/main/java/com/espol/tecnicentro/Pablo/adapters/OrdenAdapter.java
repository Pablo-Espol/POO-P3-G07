package com.espol.tecnicentro.Pablo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.OrdenServicio;

import java.util.ArrayList;
public class OrdenAdapter extends RecyclerView.Adapter<OrdenAdapter.ViewHolder>{

    private ArrayList<OrdenServicio> listaOrdenes;

    private Activity activity;
    private OnOrderDetailsClickListener listener;

    public  OrdenAdapter(ArrayList<OrdenServicio> listaOrdenes, Activity activity, OnOrderDetailsClickListener listener){

        this.activity=activity;
        this.listaOrdenes=listaOrdenes;
        this.listener= listener;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente, tvFecha, tvPlaca, tvTpagar;
        private Button btnDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvPlaca = itemView.findViewById(R.id.tvPlaca);
            tvTpagar= itemView.findViewById(R.id.tvTpagar);
            btnDetails =itemView.findViewById(R.id.btnDetails);


        }
    }

    @Override
    public OrdenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_orden,parent,false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder( OrdenAdapter.ViewHolder holder, int position) {
        OrdenServicio ordenS = listaOrdenes.get(position);
        holder.tvCliente.setText("Cliente: " + ordenS.getCliente().getNombre());
        holder.tvFecha.setText("Fecha: "+ ordenS.getFechaServicio());
        holder.tvPlaca.setText("Placa: " + ordenS.getPlacaVehiculo());
        holder.tvTpagar.setText("Total a pagar: $" + ordenS.getTotalOrden());

        // Manejar el clic en el botón de Mas detalles

        holder.btnDetails.setOnClickListener(v ->{
            if (listener !=null){
                listener.onDetailsClick(ordenS,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaOrdenes.size();
    }

    public interface OnOrderDetailsClickListener{
        void onDetailsClick(OrdenServicio orden, int position);
    }
}

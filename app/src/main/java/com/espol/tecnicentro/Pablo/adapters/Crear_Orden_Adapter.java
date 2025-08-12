package com.espol.tecnicentro.Pablo.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.DetalleServicio;

import java.util.ArrayList;

public class Crear_Orden_Adapter extends RecyclerView.Adapter<Crear_Orden_Adapter.ViewHolder> {

    private ArrayList<DetalleServicio> listaDetalleServicio;
    private Activity activity;
    private OnDeleteIcClickListener listener;


    public Crear_Orden_Adapter(ArrayList<DetalleServicio> listaDetalleServicio, Activity activity, OnDeleteIcClickListener listener) {
        this.listaDetalleServicio = listaDetalleServicio;
        this.activity = activity;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServicioSel, tvCantidadServ, tvSubtotalServ;
        private ImageButton btnEliminarSer;

        public ViewHolder(View itemView) {
            super(itemView);
            tvServicioSel = itemView.findViewById(R.id.tvServicioSel);
            tvCantidadServ = itemView.findViewById(R.id.tvCantidadServ);
            tvSubtotalServ = itemView.findViewById(R.id.tvSubtotalServ);
            btnEliminarSer =itemView.findViewById(R.id.btnEliminarSer);


        }
    }

    @Override
    public Crear_Orden_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crear_orden,parent,false);
        return new Crear_Orden_Adapter.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder( Crear_Orden_Adapter.ViewHolder holder, int position) {
        DetalleServicio DServicio = listaDetalleServicio.get(position);
        holder.tvServicioSel.setText(DServicio.getServicio().getNombre());
        holder.tvCantidadServ.setText("Cantidad: "+ DServicio.getCantidad());
        holder.tvSubtotalServ.setText("Subtotal: " + DServicio.getSubtotal());

        // Manejar el clic en el botón de Mas detalles

        holder.btnEliminarSer.setOnClickListener(v ->{
            if (listener !=null){
                listener.onDeleteIcClick(DServicio,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDetalleServicio.size();
    }
    public interface OnDeleteIcClickListener{
        void onDeleteIcClick(DetalleServicio servicio, int position);
    }

}

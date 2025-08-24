package com.espol.tecnicentro.Alejandro.adapters;
import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Alejandro.adapters.*;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Cliente;
import com.espol.tecnicentro.modelo.Proveedor;

import java.util.List;

    public class ProveedorAdapter extends RecyclerView.Adapter<ProveedorAdapter.ViewHolder>{
        private List<Proveedor> proveedorList;
        private Activity activity;
        private OnProveedorEditClickListener listener;

        public ProveedorAdapter(List<Proveedor> proveedorList, Activity activity, OnProveedorEditClickListener listener) {
            this.proveedorList = proveedorList;
            this.activity = activity;
            this.listener = listener;

        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView  nombreProve,phoneProve,descripcion;
            Button btneditProveedor;

            public ViewHolder(View itemView) {
                super(itemView);
                nombreProve = itemView.findViewById(R.id.nombreProve);   // tu id de identificación
                phoneProve = itemView.findViewById(R.id.phoneProve);          // tu id de nombre
                descripcion= itemView.findViewById(R.id.descripcion);       // tu id de teléfono
                btneditProveedor = itemView.findViewById(R.id.btneditProveedor);

            }

        }
        @Override
        public ProveedorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_proveedor, parent, false); // reemplaza con tu layout de fila
            return new ProveedorAdapter.ViewHolder(vista);
        }
        @Override
        public void onBindViewHolder(ProveedorAdapter.ViewHolder holder, int position) {
            Proveedor proveedor = proveedorList.get(position);

            holder.nombreProve.setText("Nombre: " + proveedor.getNombre());
            holder.phoneProve.setText("Telefono: "+ proveedor.getTelefono());
            holder.descripcion.setText("Descripcion: "+proveedor.getDescripcion());


            holder.btneditProveedor.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEditClick(proveedor, position);
                }
            });


        }
        @Override
        public int getItemCount() {
            return proveedorList.size();
        }
        public interface OnProveedorEditClickListener {
            void onEditClick(Proveedor proveedor, int position);
        }

}

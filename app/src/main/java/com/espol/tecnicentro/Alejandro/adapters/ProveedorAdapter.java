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
        @Override
        public ProveedorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View vista = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_proveedor, parent, false); // reemplaza con tu layout de fila
            return new RecyclerView.ViewHolder(vista);
        }
}

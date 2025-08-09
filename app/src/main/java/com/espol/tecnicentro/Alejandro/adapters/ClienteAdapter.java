package com.espol.tecnicentro.Alejandro.adapters;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.espol.tecnicentro.Alejandro.adapters.ClienteAdapter;
import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder>{
    private List<Cliente> clienteList;
    private Context context;
    private Activity activity;
    private OnClienteEditClickListener listener;

    public ClienteAdapter(List<Cliente> clienteList, Activity activity, OnClienteEditClickListener listener) {
        this.clienteList = clienteList;
        this.activity = activity;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdentificacion, tvNombre, tvTelefono, tvDireccion;
        Button btnEditar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvIdentificacion = itemView.findViewById(R.id.editTextPhone);   // tu id de identificación
            tvNombre = itemView.findViewById(R.id.editTextText5);          // tu id de nombre
            tvTelefono = itemView.findViewById(R.id.editTextPhone2);       // tu id de teléfono
            tvDireccion = itemView.findViewById(R.id.editTextText6);       // tu id de dirección
            btnEditar = itemView.findViewById(R.id.button);                // botón guardar (o crea otro botón "editar" para fila)
        }
    }

    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_clientes, parent, false); // reemplaza con tu layout de fila
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cliente cliente = clienteList.get(position);

        holder.tvIdentificacion.setText(cliente.getIdentificacion());
        holder.tvNombre.setText(cliente.getNombre());
        holder.tvTelefono.setText(cliente.getTelefono());
        holder.tvDireccion.setText(cliente.getDireccion());

        holder.btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(cliente, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

    public interface OnClienteEditClickListener {
        void onEditClick(Cliente cliente, int position);
    }
}
package com.espol.tecnicentro.Alejandro.adapters;
import android.app.Activity;

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
    private Activity activity;

    public ClienteAdapter(List<Cliente> clienteList, Activity activity) {
        this.clienteList = clienteList;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idCliente,nombre,direccion,phone,tipocliente;
        Button btneditClient;

        public ViewHolder(View itemView) {
            super(itemView);
            idCliente = itemView.findViewById(R.id.idCliente);   // tu id de identificación
            nombre = itemView.findViewById(R.id.nombre);          // tu id de nombre
            direccion= itemView.findViewById(R.id.direccion);       // tu id de teléfono
            phone = itemView.findViewById(R.id.phone);// tu id de dirección
            tipocliente= itemView.findViewById(R.id.tipocliente);// botón guardar (o crea otro botón "editar" para fila)
        }
    }

    @Override
    public ClienteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false); // reemplaza con tu layout de fila
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cliente cliente = clienteList.get(position);

        holder.idCliente.setText("Identificación: " + cliente.getIdentificacion());
        holder.nombre.setText("Nombre: "+ cliente.getNombre());
        holder.phone.setText("Telefono: "+cliente.getTelefono());
        holder.direccion.setText("Dirección: "+cliente.getDireccion());

        holder.tipocliente.setText("Tipo Cliente: "+cliente.getTipoCliente().toString());

    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

}
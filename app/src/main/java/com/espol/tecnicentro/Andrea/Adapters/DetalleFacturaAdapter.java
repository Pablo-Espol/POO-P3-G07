package com.espol.tecnicentro.Andrea.Adapters;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.espol.tecnicentro.Andrea.DetalleFacturaItem;
import com.espol.tecnicentro.R;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetalleFacturaAdapter extends RecyclerView.Adapter<DetalleFacturaAdapter.VH> {

    private final List<DetalleFacturaItem> data;
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("es", "EC"));

    public DetalleFacturaAdapter(List<DetalleFacturaItem> data) {
        this.data = data;
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_detalle_factura, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        DetalleFacturaItem it = data.get(pos);
        h.tvServicio.setText("Servicio: " + it.nombreServicio);
        h.tvCantidad.setText("Cantidad: " + it.cantidad);
        h.tvCostoUnit.setText("Costo unitario: " + nf.format(it.precioUnitario));
        h.tvSubtotal.setText("Total: " + nf.format(it.getSubtotal()));
    }

    @Override public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvServicio, tvCantidad, tvCostoUnit, tvSubtotal;
        VH(@NonNull View v) {
            super(v);
            tvServicio = v.findViewById(R.id.tvServicio);
            tvCantidad = v.findViewById(R.id.tvCantidad);
            tvCostoUnit = v.findViewById(R.id.tvCostoUnit);
            tvSubtotal = v.findViewById(R.id.tvSubtotal);
        }
    }
}
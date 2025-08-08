package com.espol.tecnicentro.Pablo.App;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Servicio;

public class EditarServicioDialogFragment extends DialogFragment {

    private static final String ARG_SERVICIO = "servicio";
    private static final String ARG_POSITION = "position";

    private Servicio servicio;
    private int position;

    // Interfaz para comunicar el resultado a la actividad principal
    public interface OnServicioEditadoListener {
        void onServicioEditado(Servicio servicioActualizado, int position);
    }
    private OnServicioEditadoListener listener;

    public void setOnServicioEditadoListener(OnServicioEditadoListener listener) {
        this.listener = listener;
    }

    public static EditarServicioDialogFragment newInstance(Servicio servicio, int position) {
        EditarServicioDialogFragment fragment = new EditarServicioDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SERVICIO, servicio);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            servicio = (Servicio) getArguments().getSerializable(ARG_SERVICIO);
            position = getArguments().getInt(ARG_POSITION);
        }
        // Aplica el estilo para el fondo opaco
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editar_servicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvNombreServicio = view.findViewById(R.id.tvNombreServicio);
        EditText etNuevoPrecio = view.findViewById(R.id.etNuevoPrecio);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);

        tvNombreServicio.setText("Servicio: " + servicio.getNombre());
        etNuevoPrecio.setText(String.valueOf(servicio.getPrecio()));

        btnGuardar.setOnClickListener(v -> {
            try {
                double nuevoPrecio = Double.parseDouble(etNuevoPrecio.getText().toString());
                servicio.setPrecio(nuevoPrecio);

                // Notifica a la actividad principal que el servicio ha sido editado
                if (listener != null) {
                    listener.onServicioEditado(servicio, position);
                }

                dismiss(); // Cierra el diálogo
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Por favor, ingrese un precio válido.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Hace que el diálogo ocupe el ancho completo y se coloque en la parte inferior
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                window.setAttributes(params);
            }
        }
    }
}
package com.espol.tecnicentro.Alejandro.Activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.espol.tecnicentro.R;
import com.espol.tecnicentro.modelo.Tecnico;

public class ConfirmarEliminarTecnicoDialogFragment extends DialogFragment {

    private static final String ARG_TECNICO = "tecnico";
    private static final String ARG_POSITION = "position";

    private Tecnico tecnico;
    private int position;

    // Interfaz para devolver el resultado
    public interface OnTecnicoEliminarListener {
        void onTecnicoEliminado(int position);
    }

    private OnTecnicoEliminarListener listener;

    public void setOnTecnicoEliminarListener(OnTecnicoEliminarListener listener) {
        this.listener = listener;
    }

    public static ConfirmarEliminarTecnicoDialogFragment newInstance(Tecnico tecnico, int position) {
        ConfirmarEliminarTecnicoDialogFragment fragment = new ConfirmarEliminarTecnicoDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TECNICO, tecnico);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tecnico = (Tecnico) getArguments().getSerializable(ARG_TECNICO);
            position = getArguments().getInt(ARG_POSITION);
        }
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_eliminar_tecnico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvMensaje = view.findViewById(R.id.tvMensaje);
        Button btnConfirmar = view.findViewById(R.id.btnConfirmar);
        Button btnCancelar = view.findViewById(R.id.btnCancelar);

        tvMensaje.setText("¿Seguro que quieres eliminar al técnico " + tecnico.getNombre() + "?");

        btnConfirmar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTecnicoEliminado(position);
            }
            dismiss();
        });

        btnCancelar.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
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

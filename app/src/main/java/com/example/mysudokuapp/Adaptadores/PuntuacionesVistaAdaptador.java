package com.example.mysudokuapp.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mysudokuapp.Entidades.Puntuaciones;
import com.example.mysudokuapp.R;

import java.util.List;

public class PuntuacionesVistaAdaptador extends RecyclerView.Adapter<PuntuacionesVistaAdaptador.PlatoViewHolder> {
    List<Puntuaciones> listaPuntuaciones;

    public PuntuacionesVistaAdaptador(List<Puntuaciones> listaPuntuaciones){
        this.listaPuntuaciones = listaPuntuaciones;
    }

    @NonNull
    @Override
    public PlatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plantilla_puntuacion, parent, false);
        return new PlatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatoViewHolder holder, int position) {
        Puntuaciones puntuacion = listaPuntuaciones.get(position);

        Number tiempoTardadoNoFormat = puntuacion.getTiempoTardado();
        int tiempoRedondeado = tiempoTardadoNoFormat != null ? tiempoTardadoNoFormat.intValue() : 0;

        int segundos = tiempoRedondeado % 60;
        int minutos = (tiempoRedondeado / 60) % 60;

        String tiempoTardadFormat = String.format("%02d:%02d", minutos, segundos);

        holder.nombreUsuario.setText(puntuacion.getNombreUsuario());
        holder.tiempoTardado.setText(tiempoTardadFormat);
        holder.pistasUsadas.setText(String.valueOf(puntuacion.getPistasUsadas()));
    }

    @Override
    public int getItemCount() {
        return listaPuntuaciones.size();
    }

    public static class PlatoViewHolder extends RecyclerView.ViewHolder {
        TextView nombreUsuario, tiempoTardado, pistasUsadas;

        public PlatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreUsuario = itemView.findViewById(R.id.nombreUsuario);
            tiempoTardado = itemView.findViewById(R.id.tiempoTardado);
            pistasUsadas = itemView.findViewById(R.id.pistasUsadas);
        }
    }

}
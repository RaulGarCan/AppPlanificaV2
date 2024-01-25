package com.cifpceuta.appplanifica;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PracticasAdapter extends RecyclerView.Adapter<PracticasAdapter.ViewHolder> {
    ArrayList<Practica> lista_practicas;
    String grupo;
    int semana;
    public PracticasAdapter(ArrayList<Practica> list_items,String grupo) {
        this.lista_practicas = list_items;
        this.grupo = grupo;
    }
    public PracticasAdapter(ArrayList<Practica> list_items, String grupo, int semana) {
        this.grupo = grupo;
        this.semana = semana;
        this.lista_practicas = this.estaDentroSemana(LocalDate.now(),semana);
    }

    @NonNull
    @Override
    public PracticasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elemento_practicas, parent, false);
        return new PracticasAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PracticasAdapter.ViewHolder holder, int position) {
        Practica practica = new Practica((Map<String, Object>) lista_practicas.get(position));
        int color = practica.tiempoPlazo();
        switch (color){
            case -1:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(), R.color.plazo_terminado));
                break;
            case 0:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(), R.color.plazo_cercano));
                break;
            case 1:
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(), R.color.plazo_correcto));
                break;
        }
        holder.bindData(grupo, practica.getModulo(), practica.getTitulo(), practica.getFechaInicio(), practica.getFechaFin());
    }

    @Override
    public int getItemCount() {
        return lista_practicas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGrupoPractica, tvModuloPractica, tvTituloPractica, tvFechaInicioPractica, tvFechaFinPractica;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGrupoPractica = itemView.findViewById(R.id.tv_grupo_practica_lista);
            tvModuloPractica = itemView.findViewById(R.id.tv_modulo_practica_lista);
            tvTituloPractica = itemView.findViewById(R.id.tv_titulo_practica_lista);
            tvFechaInicioPractica = itemView.findViewById(R.id.tv_fecha_inicio_practica_lista);
            tvFechaFinPractica = itemView.findViewById(R.id.tv_fecha_fin_practica_lista);

            cardView = itemView.findViewById(R.id.cv_elemento_practica);
        }

        void bindData(String grupo, String modulo, String titulo, String fechaInicio, String fechaFin) {
            tvGrupoPractica.setText(grupo);
            tvModuloPractica.setText(modulo);
            tvTituloPractica.setText(titulo);
            tvFechaInicioPractica.setText(fechaInicio);
            tvFechaFinPractica.setText(fechaFin);
        }
    }

    public void setFilterList(ArrayList<Practica> lista) {
        lista_practicas = lista;
        notifyDataSetChanged();
    }
    public ArrayList<Practica> estaDentroSemana(LocalDate fechaActual, int semana) {
        ArrayList<Practica> practicasSemana = new ArrayList<>();
        for(int i = 0; i<lista_practicas.size(); i++){
            LocalDate fecha = lista_practicas.get(i).getFechaFinDate();
            if (fechaActual.getYear() == fecha.getYear() && fechaActual.getYear() == fecha.getYear()){
                int semanaFecha = fecha.get(WeekFields.of(DayOfWeek.MONDAY, 1).weekOfMonth());
                if(semanaFecha == semana){
                    practicasSemana.add(lista_practicas.get(i));
                }
            }
        }
        return practicasSemana;
    }
}

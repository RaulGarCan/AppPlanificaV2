package com.cifpceuta.appplanifica;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActExtrasAdapter extends RecyclerView.Adapter<ActExtrasAdapter.ViewHolder> {
    ArrayList<ActividadExtra> lista_act_extra;
    public ActExtrasAdapter(ArrayList<ActividadExtra> list_items) {
        this.lista_act_extra = list_items;
    }

    @NonNull
    @Override
    public ActExtrasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_elemento_actividad_extra, parent, false);
        return new ActExtrasAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActExtrasAdapter.ViewHolder holder, int position) {
        holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.getContext(),R.color.color_cardview_act_extra));
        holder.tvTituloActExtra.setTextColor(Color.WHITE);
        holder.tvGrupoActExtra.setTextColor(Color.WHITE);
        holder.tvFechaActExtra.setTextColor(Color.WHITE);
        holder.bindData(lista_act_extra.get(position).getTitulo(),lista_act_extra.get(position).getGrupo(),lista_act_extra.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return lista_act_extra.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGrupoActExtra, tvTituloActExtra, tvFechaActExtra;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvGrupoActExtra = itemView.findViewById(R.id.tv_grupo_act_extra);
            tvTituloActExtra = itemView.findViewById(R.id.tv_titulo_act_extra);
            tvFechaActExtra = itemView.findViewById(R.id.tv_fecha_act_extra);

            cardView = itemView.findViewById(R.id.cv_elemento_actividad_extra);

        }

        void bindData(String titulo, String grupo, String fecha) {
            tvTituloActExtra.setText(titulo);
            tvGrupoActExtra.setText(grupo);
            tvFechaActExtra.setText(fecha);
        }
    }

    public void setFilterList(ArrayList<ActividadExtra> lista) {
        lista_act_extra = lista;
        notifyDataSetChanged();
    }
    public void addActividadExtra(ActividadExtra actividadExtra){
        lista_act_extra.add(actividadExtra);
        notifyDataSetChanged();
    }
}

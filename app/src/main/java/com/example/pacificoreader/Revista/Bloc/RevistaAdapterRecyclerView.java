package com.example.pacificoreader.Revista.Bloc;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacificoreader.R;
import com.example.pacificoreader.Revista.Model.Revista;

import java.util.ArrayList;

public class RevistaAdapterRecyclerView extends RecyclerView.Adapter<RevistaAdapterRecyclerView.RevistaViewHolder> {

    Activity activity;
    int resourse;
    ArrayList<Revista> revistas;

    public RevistaAdapterRecyclerView(Activity activity, int resourse, ArrayList<Revista> revistas) {
        this.activity = activity;
        this.resourse = resourse;
        this.revistas = revistas;
    }

    @NonNull
    @Override
    public RevistaAdapterRecyclerView.RevistaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RevistaAdapterRecyclerView.RevistaViewHolder holder, int position) {
        Revista revista = revistas.get(position);
        holder.nombre.setText(revista.getNombre());
        holder.descripcion.setText(revista.getDescripcion());
        holder.publicado.setText(revista.getPublicado());
        holder.articulos.setText(revista.getArticulos());
    }

    @Override
    public int getItemCount() {
        return revistas.size();
    }

    public class RevistaViewHolder extends RecyclerView.ViewHolder {


        ImageView imagen;
        TextView nombre;
        TextView descripcion;
        TextView publicado;
        TextView articulos;


        public RevistaViewHolder(@NonNull View itemView) {
            super(itemView);
            this.publicado = itemView.findViewById(R.id.revista_fecha);
            this.articulos = itemView.findViewById(R.id.revista_articulos);
            this.descripcion = itemView.findViewById(R.id.revista_descripcion);
            this.nombre = itemView.findViewById(R.id.revista_nombre);
            this.imagen = itemView.findViewById(R.id.revista_imagen);

        }
    }
}
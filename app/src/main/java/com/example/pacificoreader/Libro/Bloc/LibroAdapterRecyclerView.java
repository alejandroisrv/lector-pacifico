package com.example.pacificoreader.Libro.Bloc;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pacificoreader.Libro.Model.Libro;
import com.example.pacificoreader.Libro.screen.LibroDetail;
import com.example.pacificoreader.R;

import java.io.File;
import java.util.ArrayList;

public class LibroAdapterRecyclerView extends RecyclerView.Adapter<LibroAdapterRecyclerView.LibroViewHolder> {


    public ArrayList<Libro> libros;
    private int resource;
    private Activity activity;

    public LibroAdapterRecyclerView(ArrayList<Libro> libros, int resource, Activity activity) {
        this.libros = libros;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {

        Libro libro = libros.get(position);


        holder.nombre.setText(libro.getNombre());
        holder.descripcion.setText(libro.getSubtitulo());



        File file = new File(libro.getImagen());
        Uri imageUri = Uri.fromFile(file);
        Glide.with(activity).load(imageUri).centerCrop().placeholder(R.drawable.ic_launcher_background).into(holder.imagen);


        holder.nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LibroDetail.class);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public class LibroViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagen;
        private TextView nombre;
        private TextView descripcion;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imagen = itemView.findViewById(R.id.libro_imagen);
            this.nombre = itemView.findViewById(R.id.libro_name);
            this.descripcion = itemView.findViewById(R.id.libro_descripcion);
        }
    }
}
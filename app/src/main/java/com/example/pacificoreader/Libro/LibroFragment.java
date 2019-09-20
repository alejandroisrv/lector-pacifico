package com.example.pacificoreader.Libro;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pacificoreader.Libro.Bloc.LibroAdapterRecyclerView;
import com.example.pacificoreader.Libro.Model.Libro;
import com.example.pacificoreader.Libro.SqlLite.LibroSqlAdapter;
import com.example.pacificoreader.R;

import java.util.ArrayList;

public class LibroFragment extends Fragment {
    ArrayList<Libro> libros;
    public LibroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_libro, container, false);
        RecyclerView picturesRecycler = view.findViewById(R.id.rv_libro_gratis);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);
        LibroAdapterRecyclerView libroAdapterRecyclerView = new LibroAdapterRecyclerView(buildLibros(), R.layout.cardview_libro, getActivity());
        picturesRecycler.setAdapter(libroAdapterRecyclerView);


        return view;
    }


    public ArrayList<Libro> buildLibros(){
        ArrayList<Libro> libros = new ArrayList<>();
        LibroSqlAdapter dbLibro = new LibroSqlAdapter(getActivity());
        Cursor cursor = dbLibro.getAll();
        while (cursor.moveToNext()){
            libros.add(new Libro(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5)));
        }
        return libros;
    }

}

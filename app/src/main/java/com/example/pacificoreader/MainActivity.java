package com.example.pacificoreader;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pacificoreader.Libro.SqlLite.LibroSqlAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISO_CODE = 100;
    TextView startLector;
    boolean newDatos = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startLector = findViewById(R.id.startLector);

        if(newDatos){
            new DownloadImage(MainActivity.this);
            LibroSqlAdapter DbLibro = new LibroSqlAdapter(MainActivity.this);
            ArrayList<String> Datos = new ArrayList<>();
            Datos.add("Libro1");
            Datos.add("");
            Datos.add("Descripcion");
            Datos.add("libro.jpg");
            Datos.add("Roberto");
            DbLibro.insert(Datos);
        }


        startLector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,  @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == PERMISO_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this,"Camera Permission Granted",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,"Camera Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }

    }

}



package com.example.pacificoreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    TextView startLector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startLector = findViewById(R.id.startLector);

        startLector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainLector = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(mainLector);
            }
        });
    }


}

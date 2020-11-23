package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.agendacontactos.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button aniadir;
    private Button visualizar;
    private Button recordatorios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aniadir = (Button) findViewById(R.id.notasbtn);
        visualizar = (Button) findViewById(R.id.contactosbtn);
        recordatorios = (Button) findViewById(R.id.recordatoriosbtn);
        aniadir.setOnClickListener(this);
        visualizar.setOnClickListener(this);
        recordatorios.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.contactosbtn:
                cargarIntent(VisualizarContactoActivity.class);
                break;
            case R.id.notasbtn:
                cargarIntent(VisualizarNotaActivity.class);
                break;
            case R.id.recordatoriosbtn:
                cargarIntent(VisualizarRecordatorioActivity.class);
                break;
            default:

                break;
        }
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

}

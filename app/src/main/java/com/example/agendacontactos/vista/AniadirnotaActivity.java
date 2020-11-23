package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.api.service.NotaService;
import com.example.agendacontactos.controlador.ConexionRetrofit;
import com.example.agendacontactos.modelo.Notas;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AniadirnotaActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView titulo,notas;
    private Button aniadir;
    private int user;
    private Notas nota;
    private ConexionRetrofit retrofit;
    private NotaService notaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirnota);
        retrofit = new ConexionRetrofit();
        notaService = retrofit.getRetrofit().create(NotaService.class);
        cargarPreferencias();
        titulo = (TextView) findViewById(R.id.tituloidEditText);
        notas = (TextView) findViewById(R.id.notasidEditText);
        aniadir = (Button) findViewById(R.id.aniadirnotabtn);
        aniadir.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(titulo.getText().toString().length()==0)
            Toast.makeText(this, "Debe introducir un título", Toast.LENGTH_LONG).show();
        else {
            if(notas.getText().toString().length()==0)
                Toast.makeText(this,"Debe introducir una nota",Toast.LENGTH_LONG).show();
            else {
                crearNota();
                insertarNota();
            }
        }

    }

    public void insertarNota(){
        Call<Boolean> call = notaService.insertarNota(nota.getUser(),nota.getTitulo(),nota.getNotas(),nota.getFecha());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AniadirnotaActivity.this, "Nota insertada con éxito", Toast.LENGTH_LONG).show();
                    cargarIntent(VisualizarNotaActivity.class);
                }
                else {
                    try {
                        Toast.makeText(AniadirnotaActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(AniadirnotaActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void crearNota(){
        //TODO revisar crear fecha, porque lo pone en GMT 0 no es GMT +1 o CEST
        nota = new Notas();
        nota.setTitulo(titulo.getText().toString());
        nota.setNotas(notas.getText().toString());
        nota.setFecha((new Date()).toString());
        nota.setUser(user);
    }
    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }
    @Override
    public void onBackPressed() {
        cargarIntent(VisualizarNotaActivity.class);
    }
}

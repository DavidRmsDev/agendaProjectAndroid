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
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.modelo.Notas;

public class aniadirnotaActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView titulo,notas;
    private Button aniadir;
    private int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadirnota);
        cargarPreferencias();
        titulo = (TextView) findViewById(R.id.tituloidEditText);
        notas = (TextView) findViewById(R.id.notasidEditText);
        aniadir = (Button) findViewById(R.id.aniadirnotabtn);
        aniadir.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(titulo.getText().toString().length()!=0){
            if(notas.getText().toString().length()!=0){
                if(new ConexionBBDD(this).insertarNota(crearNota())!=-1) {
                    Toast.makeText(this, "Nota insertada con éxito", Toast.LENGTH_LONG).show();
                    cargarIntent(visualizarNotaActivity.class);
                }
            }
            else
                Toast.makeText(this,"Debe introducir una nota",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"Debe introducir un título",Toast.LENGTH_LONG).show();
    }


    public Notas crearNota(){
        Notas c=new Notas();
        c.setTitulo(titulo.getText().toString());
        c.setNotas(notas.getText().toString());
        c.setUser(user);
        return c;
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
        cargarIntent(visualizarNotaActivity.class);
    }
}

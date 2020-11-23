package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.controlador.NotaAdapter;
import com.example.agendacontactos.modelo.Notas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class visualizarNotaActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lista;
    private List<Notas> items;
    private FloatingActionButton fab;
    private int user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_nota);
        inicializar();
    }
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }
    public void inicializar(){
        cargarPreferencias();
        items=new ConexionBBDD(this).devolverNotas(user);
        lista = (ListView)findViewById(R.id.listNota);
        fab = (FloatingActionButton) findViewById(R.id.fabnotaid);
        fab.setOnClickListener(this);
        final NotaAdapter adaptador= new NotaAdapter(this,R.layout.nota_item,items);

        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(items.get(position).getId()!=null){
                    cargarIntent(modificarborrarNotaActivity.class,String.valueOf(items.get(position).getId()));
                }

            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(items.get(position).getTitulo()!=null){
                    cargarIntent(modificarborrarNotaActivity.class,String.valueOf(items.get(position).getId()));
                }

                return false;
            }
        });
    }

    public void cargarIntent(Class ventana,String id){
        Intent intent = new Intent(this,ventana);
        intent.putExtra("id",id);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fabnotaid){
            Intent intent = new Intent(this, aniadirnotaActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        cargarIntent(MainActivity.class,null);
    }

}

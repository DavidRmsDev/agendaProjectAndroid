package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.api.service.RecordatorioService;
import com.example.agendacontactos.controlador.ConexionRetrofit;
import com.example.agendacontactos.controlador.RecordatorioAdapter;
import com.example.agendacontactos.modelo.Recordatorio;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisualizarRecordatorioActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lista;
    private List<Recordatorio> items;
    private FloatingActionButton fab;
    private int user;
    private ConexionRetrofit retrofit;
    private RecordatorioService recordatorioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_recordatorio);
        retrofit = new ConexionRetrofit();
        recordatorioService = retrofit.getRetrofit().create(RecordatorioService.class);
        cargarPreferencias();
        llenarLista();
    }
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }

    public void llenarLista(){
        Call<List<Recordatorio>> call = recordatorioService.listarRecordatorios(user);
        call.enqueue(new Callback<List<Recordatorio>>() {
            @Override
            public void onResponse(Call<List<Recordatorio>> call, Response<List<Recordatorio>> response) {
                if(response.isSuccessful()){
                    items = response.body();
                    cargarRecordatorios();
                }
                else {
                    try {
                        Toast.makeText(VisualizarRecordatorioActivity.this,response.errorBody().string(), Toast.LENGTH_SHORT);
                    } catch (IOException e) {}
                }
            }
            @Override
            public void onFailure(Call<List<Recordatorio>> call, Throwable t) {
                Toast.makeText(VisualizarRecordatorioActivity.this,"Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    public void cargarRecordatorios(){
        lista = (ListView)findViewById(R.id.listRecordatorio);
        fab = (FloatingActionButton) findViewById(R.id.Recfabid);
        fab.setOnClickListener(this);
        final RecordatorioAdapter adaptador= new RecordatorioAdapter(this,R.layout.recordatorio_item,items);

        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(items.get(position).getId()!=null){
                    cargarIntent(ModificarborrarRecordatorioActivity.class,String.valueOf(items.get(position).getId()));
                }

            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(items.get(position).getId()!=null){
                    cargarIntent(ModificarborrarRecordatorioActivity.class,String.valueOf(items.get(position).getId()));
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
        if(v.getId()==R.id.Recfabid){
            Intent intent = new Intent(this, AniadirRecordatorioActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        cargarIntent(MainActivity.class,null);
    }

}


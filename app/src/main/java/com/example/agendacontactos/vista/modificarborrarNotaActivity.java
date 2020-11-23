package com.example.agendacontactos.vista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ModificarborrarNotaActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private EditText titulo;
    private EditText notas;
    private Button borrar;
    private Button modificar;
    private Notas nota;
    private ConexionRetrofit retrofit;
    private NotaService notaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarborrar_nota);
        retrofit = new ConexionRetrofit();
        notaService = retrofit.getRetrofit().create(NotaService.class);
        id= Integer.parseInt(getIntent().getStringExtra("id"));
        devolverNota();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_borr){
            crearDialogo();
        }
        else{
            if(titulo.getText().toString().isEmpty()){
                Toast.makeText(this,"Debe rellenar campo de título",Toast.LENGTH_LONG).show();
            }
            else{
                if(notas.getText().toString().isEmpty()){
                    Toast.makeText(this,"Debe rellenar campo de notas",Toast.LENGTH_LONG).show();
                }
                else{
                    modificarNota();
                    /*if(new ConexionBBDD(this).modificarNota(Integer.toString(id),titulo.getText().toString(),notas.getText().toString()))
                    {Toast.makeText(this, "Nota modificada", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarNotaActivity.class);
                    }
                    else
                        Toast.makeText(this, "No se ha podido modificar la nota", Toast.LENGTH_SHORT).show();*/
                }

            }
        }
    }

    public void devolverNota(){
        Call<Notas> call = notaService.seleccionaUnaNota(id);
        call.enqueue(new Callback<Notas>() {
            @Override
            public void onResponse(Call<Notas> call, Response<Notas> response) {
                if (response.isSuccessful()){
                    nota = new Notas(response.body());

                    titulo = (EditText) findViewById(R.id.modificartituloid);
                    notas = (EditText) findViewById(R.id.modificarnotasid);
                    borrar = (Button) findViewById(R.id.btn_borr);
                    modificar = (Button) findViewById(R.id.btn_mod);
                    cargarNotas(nota);

                    borrar.setOnClickListener(ModificarborrarNotaActivity.this);
                    modificar.setOnClickListener(ModificarborrarNotaActivity.this);
                }
                else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {}
                }
            }

            @Override
            public void onFailure(Call<Notas> call, Throwable t) {
                Toast.makeText(ModificarborrarNotaActivity.this,"Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    public void cargarNotas(Notas c){
        titulo.setText(c.getTitulo());
        notas.setText(c.getNotas());
    }

    public void modificarNota(){
        Call<Boolean> call = notaService.modificarNota(id,titulo.getText().toString(),notas.getText().toString(),new Date().toString());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarNotaActivity.this, "Nota modificado", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarNotaActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarNotaActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarNotaActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }

    public void crearDialogo() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Borrar");
        dialog.setMessage("¿Desea borrar la nota?");
        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrar();
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    public void borrar(){
        Call<Boolean> call = notaService.borrarNota(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarNotaActivity.this, "Nota eliminada", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarNotaActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarNotaActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarNotaActivity.this, "Error de conexion, no se ha podido borrar la nota", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        cargarIntent(VisualizarNotaActivity.class);
    }

}

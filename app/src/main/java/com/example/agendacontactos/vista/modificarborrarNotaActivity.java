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
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.modelo.Notas;

public class modificarborrarNotaActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private EditText titulo;
    private EditText notas;
    private Button borrar;
    private Button modificar;
    Notas c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarborrar_nota);
        id= Integer.parseInt(getIntent().getStringExtra("id"));
        c=new ConexionBBDD(this).devolverNota(id);
        titulo = (EditText) findViewById(R.id.modificartituloid);
        notas = (EditText) findViewById(R.id.modificarnotasid);
        borrar = (Button) findViewById(R.id.btn_borr);
        modificar = (Button) findViewById(R.id.btn_mod);
        borrar.setOnClickListener(this);
        modificar.setOnClickListener(this);
        cargarNotas(c);
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
                    if(new ConexionBBDD(this).modificarNota(Integer.toString(id),titulo.getText().toString(),notas.getText().toString()))
                    {Toast.makeText(this, "Nota modificada", Toast.LENGTH_SHORT).show();
                        cargarIntent(visualizarNotaActivity.class);
                    }
                    else
                        Toast.makeText(this, "No se ha podido modificar la nota", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    public void cargarNotas(Notas c){
        titulo.setText(c.getTitulo());
        notas.setText(c.getNotas());
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
        if(new ConexionBBDD(this).borrarNota(id)){
            Toast.makeText(this, "Nota borrada", Toast.LENGTH_SHORT).show();
            cargarIntent(visualizarNotaActivity.class);
        }
        else
            Toast.makeText(this, "No se ha podido borrar la nota", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        cargarIntent(visualizarNotaActivity.class);
    }

}

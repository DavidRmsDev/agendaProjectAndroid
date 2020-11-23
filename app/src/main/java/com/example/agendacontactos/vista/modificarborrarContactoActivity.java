package com.example.agendacontactos.vista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.modelo.Contacto;

import java.util.regex.Pattern;

public class modificarborrarContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private EditText nombre;
    private EditText apellidos;
    private EditText direccion;
    private EditText email;
    private EditText telefono;
    private Button borrar;
    private Button modificar;
    Contacto c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarborrarcontacto);
        id= Integer.parseInt(getIntent().getStringExtra("id"));
        //Toast.makeText(this, "wea "+getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
        c=new ConexionBBDD(this).devolverContacto(id);
        nombre= (EditText) findViewById(R.id.nombretextview);
        apellidos= (EditText) findViewById(R.id.apellidostextview);
        direccion= (EditText) findViewById(R.id.direcciontextview);
        email= (EditText) findViewById(R.id.emailtextview);
        telefono= (EditText) findViewById(R.id.telefonotextview);
        cargarContacto(c);
        borrar = (Button) findViewById(R.id.borrarbtn);
        modificar = (Button) findViewById(R.id.modificarbtn);
        borrar.setOnClickListener(this);
        modificar.setOnClickListener(this);
    }

    public void cargarContacto(Contacto c){
        nombre.setText(c.getNombre());
        if(c.getApellidos()!=null)
            apellidos.setText(c.getApellidos());
        if(c.getDireccion()!=null)
            direccion.setText(c.getDireccion());
        if(c.getEmail()!=null)
            email.setText(c.getEmail());
        telefono.setText(c.getTelefono());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.borrarbtn){
            crearDialogo();
        }
        else{
            if (nombre.getText().toString().isEmpty()|| telefono.getText().toString().isEmpty())
                Toast.makeText(this,"Debe rellenar campos de nombre y teléfono",Toast.LENGTH_LONG).show();
            else if(telefono.getText().toString().length()!=9){
                Toast.makeText(this,"Formato de teléfono incorrecto",Toast.LENGTH_LONG).show();
            }
            else if(email.getText().length()!=0 && !validarEmail(email.getText().toString())){
                Toast.makeText(this,"Formato de email incorrecto",Toast.LENGTH_LONG).show();
            }
            else{
                if(new ConexionBBDD(this).modificarContacto(Integer.toString(id),nombre.getText().toString(),apellidos.getText().toString(),direccion.getText().toString(),telefono.getText().toString(),email.getText().toString()))
                {Toast.makeText(this, "Contacto modificado", Toast.LENGTH_SHORT).show();
                cargarIntent(visualizarContactoActivity.class);
                }
                else
                    Toast.makeText(this, "No se ha podido modificar el contacto", Toast.LENGTH_SHORT).show();

            }
        }

    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }

    public void crearDialogo() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Borrar");
        dialog.setMessage("¿Desea borrar el contacto?");
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
        if(new ConexionBBDD(this).borrarContacto(id)){
            Toast.makeText(this, "Contacto borrado", Toast.LENGTH_SHORT).show();
            cargarIntent(visualizarContactoActivity.class);
        }
        else
            Toast.makeText(this, "No se ha podido borrar el contacto", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        cargarIntent(visualizarContactoActivity.class);
    }
}

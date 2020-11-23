package com.example.agendacontactos.vista;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.modelo.Contacto;

import java.util.regex.Pattern;

public class aniadirContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nombre;
    private TextView apellidos;
    private TextView direccion;
    private TextView email;
    private TextView telefono;
    private Button btnAniadir;
    private int user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniadircontactos);
        cargarPreferencias();
        btnAniadir = (Button) findViewById(R.id.btnaniadir);
        nombre = (TextView) findViewById(R.id.nombreeditTexto);
        apellidos = (TextView) findViewById(R.id.apellidoseditText);
        direccion = (TextView) findViewById(R.id.direccioneditText);
        email = (TextView) findViewById(R.id.emaileditText);
        telefono = (TextView) findViewById(R.id.telefonoeditText);
        btnAniadir.setOnClickListener(this);
    }
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }
    @Override
    public void onClick(View v) {
        if (nombre.getText().toString().isEmpty()|| telefono.getText().toString().isEmpty())
            Toast.makeText(this,"Debe rellenar campos de nobre y teléfono",Toast.LENGTH_LONG).show();
        else if(telefono.getText().toString().length()!=9){
            Toast.makeText(this,"Formato de teléfono incorrecto",Toast.LENGTH_LONG).show();
        }
        else if(email.getText().length()!=0 && !validarEmail(email.getText().toString())){
            Toast.makeText(this,"Formato de email incorrecto",Toast.LENGTH_LONG).show();
        }
        else
            if(new ConexionBBDD(this).insertarContacto(crearContacto())!=-1) {
                Toast.makeText(this, "Contacto insertado con éxito", Toast.LENGTH_LONG).show();
                cargarIntent(visualizarContactoActivity.class);
            }
            else
                Toast.makeText(this,"No se ha podido agregar el contacto. Teléfono ya registrado",Toast.LENGTH_LONG).show();
    }

    public Contacto crearContacto(){
        Contacto c=new Contacto();
        c.setNombre(nombre.getText().toString());
        if(!apellidos.getText().toString().isEmpty())
            c.setApellidos(apellidos.getText().toString());
        c.setTelefono(telefono.getText().toString());
        if(!direccion.getText().toString().isEmpty())
            c.setDireccion(direccion.getText().toString());
        if(!email.getText().toString().isEmpty())
            c.setEmail(email.getText().toString());
        c.setUser(user);
        return c;
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        cargarIntent(visualizarContactoActivity.class);
    }
}

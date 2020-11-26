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
import com.example.agendacontactos.controlador.ConexionRetrofit;
import com.example.agendacontactos.api.service.ContactoService;
import com.example.agendacontactos.modelo.Contacto;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AniadirContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nombre;
    private TextView apellidos;
    private TextView direccion;
    private TextView email;
    private TextView telefono;
    private Button btnAniadir;
    private int user;
    private Contacto contacto;
    private ConexionRetrofit conexion;
    private ContactoService contactoService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniadircontactos);
        conexion = new ConexionRetrofit();
        contactoService = conexion.getRetrofit().create(ContactoService.class);
        btnAniadir = (Button) findViewById(R.id.btnaniadir);
        nombre = (TextView) findViewById(R.id.nombreeditTexto);
        apellidos = (TextView) findViewById(R.id.apellidoseditText);
        direccion = (TextView) findViewById(R.id.direccioneditText);
        email = (TextView) findViewById(R.id.emaileditText);
        telefono = (TextView) findViewById(R.id.telefonoeditText);
        cargarPreferencias();
        btnAniadir.setOnClickListener(this);
    }
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }
    @Override
    public void onClick(View v) {
        if (telefono.getText().toString().isEmpty())
            Toast.makeText(this,"Debe rellenar el campo teléfono",Toast.LENGTH_LONG).show();
        else if(telefono.getText().toString().length()!=9)
                Toast.makeText(this,"Formato de teléfono incorrecto",Toast.LENGTH_LONG).show();
        else if(email.getText().length() > 0 && !validarEmail(email.getText().toString()))
            Toast.makeText(this,"Formato de email incorrecto",Toast.LENGTH_LONG).show();
        else {
            crearContacto();
            añadir();
        }
    }

    public void crearContacto(){
        contacto=new Contacto("","","","","");

        if(!telefono.getText().toString().isEmpty())
            contacto.setTelefono(telefono.getText().toString());
        contacto.setNombre(nombre.getText().toString());
        contacto.setApellidos(apellidos.getText().toString());
        contacto.setDireccion(direccion.getText().toString());
        contacto.setEmail(email.getText().toString());

        contacto.setUser(user);
        /*contacto.setNombre(nombre.getText().toString());
        if(!apellidos.getText().toString().isEmpty())
            contacto.setApellidos(apellidos.getText().toString());
        contacto.setTelefono(telefono.getText().toString());
        if(!direccion.getText().toString().isEmpty())
            contacto.setDireccion(direccion.getText().toString());
        if(!email.getText().toString().isEmpty())
            contacto.setEmail(email.getText().toString());
        contacto.setUser(user);*/
    }

    public void añadir(){
        Call<Boolean> call = contactoService.insertarContacto(contacto.getUser(),contacto.getNombre(),contacto.getApellidos(),
                Integer.parseInt(contacto.getTelefono()),contacto.getDireccion(),contacto.getEmail());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AniadirContactoActivity.this, "Contacto insertado con éxito", Toast.LENGTH_LONG).show();
                    cargarIntent(VisualizarContactoActivity.class);
                }
                else {
                    try {
                        Toast.makeText(AniadirContactoActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(AniadirContactoActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarEmail(String email) {
        Pattern patron = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patron.matcher(email);
        return matcher.find();
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        cargarIntent(VisualizarContactoActivity.class);
    }
}

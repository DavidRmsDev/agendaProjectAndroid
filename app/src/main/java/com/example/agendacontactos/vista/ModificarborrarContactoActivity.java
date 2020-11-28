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
import com.example.agendacontactos.controlador.ConexionRetrofit;
import com.example.agendacontactos.api.service.ContactoService;
import com.example.agendacontactos.modelo.Contacto;;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarborrarContactoActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private EditText nombre;
    private EditText apellidos;
    private EditText direccion;
    private EditText email;
    private EditText telefono;
    private Button borrar;
    private Button modificar;
    private Contacto c;
    private ConexionRetrofit conexion;
    private ContactoService contactoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarborrarcontacto);
        id= Integer.parseInt(getIntent().getStringExtra("id"));

        conexion = new ConexionRetrofit();
        contactoService = conexion.getRetrofit().create(ContactoService.class);
        devolverContacto();
    }

    public void devolverContacto(){
        Call<Contacto> call = contactoService.seleccionaUnContacto(id);

        call.enqueue(new Callback<Contacto>() {
            @Override
            public void onResponse(Call<Contacto> call, Response<Contacto> response) {
                if (response.isSuccessful()){
                    c = new Contacto(response.body());
                    nombre= (EditText) findViewById(R.id.nombretextview);
                    apellidos= (EditText) findViewById(R.id.apellidostextview);
                    direccion= (EditText) findViewById(R.id.direcciontextview);
                    email= (EditText) findViewById(R.id.emailtextview);
                    telefono= (EditText) findViewById(R.id.telefonotextview);
                    cargarContacto(c);
                    borrar = (Button) findViewById(R.id.borrarbtn);
                    modificar = (Button) findViewById(R.id.modificarbtn);
                    borrar.setOnClickListener(ModificarborrarContactoActivity.this);
                    modificar.setOnClickListener(ModificarborrarContactoActivity.this);
                }
                else{
                    try {
                        Toast.makeText(ModificarborrarContactoActivity.this,response.errorBody().string(), Toast.LENGTH_SHORT);
                    } catch (IOException e) {}
                }
            }

            @Override
            public void onFailure(Call<Contacto> call, Throwable t) {
                Toast.makeText(ModificarborrarContactoActivity.this,"Error de conexión", Toast.LENGTH_SHORT);
            }
        });
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
            else
                modificar();
        }

    }

    private void modificar(){
        Call<Boolean> call = contactoService.modificarContacto(new Integer(id), nombre.getText().toString(),
                apellidos.getText().toString(), Integer.parseInt(telefono.getText().toString()),
                direccion.getText().toString(), email.getText().toString());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarContactoActivity.this, "Contacto modificado", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarContactoActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarContactoActivity.this,response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarContactoActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarEmail(String email) {
        Pattern patron = Pattern.compile("/^(([^<>()[\\]\\.,;:\\s@\\\"]+(\\.[^<>()[\\]\\.,;:\\s@\\\"]+)*)|(\\\".+\\\"))@(([^<>()[\\]\\.,;:\\s@\\\"]+\\.)+[^<>()[\\]\\.,;:\\s@\\\"]{2,})$/i", Pattern.CASE_INSENSITIVE);
        Matcher matcher = patron.matcher(email);
        return matcher.find();
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
        Call<Boolean> call = contactoService.borrarContacto(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarContactoActivity.this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarContactoActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarContactoActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarContactoActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        cargarIntent(VisualizarContactoActivity.class);
    }
}

package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionRetrofit;
import com.example.agendacontactos.api.service.UsuarioService;
import com.example.agendacontactos.modelo.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView iniciarSesion,registrarse;
    private EditText nickid,passid;
    private Button ini,reg;
    private CheckBox checkBox;
    private ConexionRetrofit conexion;
    private UsuarioService usuarioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarSesion = (TextView) findViewById(R.id.iniciarid);
        registrarse = (TextView) findViewById(R.id.registrarid);
        nickid = (EditText) findViewById(R.id.nicktextid);
        passid = (EditText) findViewById(R.id.passtextid);
        checkBox = (CheckBox) findViewById(R.id.checkboxid);
        ini = (Button) findViewById(R.id.inicid);
        reg = (Button) findViewById(R.id.regisid);
        conexion = new ConexionRetrofit();
        usuarioService = conexion.getRetrofit().create(UsuarioService.class);
        checkBox.setOnClickListener(this);
        iniciarSesion.setOnClickListener(this);
        registrarse.setOnClickListener(this);
        ini.setOnClickListener(this);
        reg.setOnClickListener(this);
        checkBox.setText(Html.fromHtml("Aceptar <a href='http://agendaproject.herokuapp.com/Privacidad' >Términos y Condiciones</a> de uso"));
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());

        resetearPreferencias();
        ocultarTodo();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iniciarid:
                ocultarTodo();
                activarInicio();
                break;
            case R.id.registrarid:
                ocultarTodo();
                activarRegistro();
                break;
            case R.id.inicid:
                if(comprobarCampos())
                    login();
                break;
            case R.id.regisid:
                if(comprobarCampos()){
                    if(comprobarCheckbox()) {
                        registroNuevo();
                    }
                }
                break;
        }
    }

    public boolean comprobarCheckbox(){
        if(!checkBox.isChecked()){
            Toast.makeText(this,"Debe aceptar los términos y condiciones",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean comprobarCampos(){
        if(nickid.getText().toString().isEmpty() || passid.getText().toString().isEmpty()){
            Toast.makeText(this,"Debe rellenar campos de nickname, password",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    public void activarInicio(){
        nickid.setVisibility(View.VISIBLE);
        passid.setVisibility(View.VISIBLE);
        ini.setVisibility(View.VISIBLE);
    }

    public void activarRegistro(){
        nickid.setVisibility(View.VISIBLE);
        passid.setVisibility(View.VISIBLE);
        reg.setVisibility(View.VISIBLE);
        checkBox.setVisibility(View.VISIBLE);
    }

    public void ocultarTodo(){
        nickid.setVisibility(View.INVISIBLE);
        passid.setVisibility(View.INVISIBLE);
        reg.setVisibility(View.INVISIBLE);
        ini.setVisibility(View.INVISIBLE);
        checkBox.setVisibility(View.INVISIBLE);
    }

    public void registroNuevo(){
        Call<Boolean> call = usuarioService.registrarUsuario(nickid.getText().toString(), passid.getText().toString());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()){
                    if(response.body()){
                        ocultarTodo();
                        Toast.makeText(LoginActivity.this, "Usuario registrado con éxito, inicie sesión", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "El nickname ya existe", Toast.LENGTH_LONG).show();
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }
    public void login(){
        if(comprobarCampos()){
            devolverUserId();
        }
    }


    public void guardarPreferencias(int usuario){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user",usuario);
        editor.commit();
    }
    public void resetearPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("user",0);
        editor.commit();
    }
    public void devolverUserId(){
        //TODO refactorizar
        Call<Usuario> call = usuarioService.devolverUsuario(nickid.getText().toString(), passid.getText().toString());

        Usuario usuario = new Usuario();

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    usuario.setUser(response.body().getUser());
                    if (!(usuario.getUser()).equals("null")){
                        int user = usuario.getUser().intValue();
                        guardarPreferencias(user);
                        Toast.makeText(LoginActivity.this, "Usuario logeado con éxito", Toast.LENGTH_LONG).show();
                        cargarIntent(MainActivity.class);
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
                return;
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                //System.out.println("Mensaje: " + t.getMessage() + "  Error: " + t.getLocalizedMessage());
                Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }

}

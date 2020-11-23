package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.modelo.Usuario;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView iniciarSesion,registrarse;
    private EditText nickid,passid;
    private Button ini,reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarSesion = (TextView) findViewById(R.id.iniciarid);
        registrarse = (TextView) findViewById(R.id.registrarid);
        nickid = (EditText) findViewById(R.id.nicktextid);
        passid = (EditText) findViewById(R.id.passtextid);
        ini = (Button) findViewById(R.id.inicid);
        reg = (Button) findViewById(R.id.regisid);
        iniciarSesion.setOnClickListener(this);
        registrarse.setOnClickListener(this);
        ini.setOnClickListener(this);
        reg.setOnClickListener(this);
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
                if(comprobarCampos())
                    registroNuevo();
                break;
        }
    }

    public boolean comprobarCampos(){
        if(nickid.getText().toString().isEmpty() || passid.getText().toString().isEmpty()){
            Toast.makeText(this,"Debe rellenar campos de nickname y password",Toast.LENGTH_LONG).show();
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
    }

    public void ocultarTodo(){
        nickid.setVisibility(View.INVISIBLE);
        passid.setVisibility(View.INVISIBLE);
        reg.setVisibility(View.INVISIBLE);
        ini.setVisibility(View.INVISIBLE);
    }

    public void registroNuevo(){
        Usuario u = new Usuario();
        u.setNickname(nickid.getText().toString());
        u.setPasssword(passid.getText().toString());
        if(new ConexionBBDD(this).insertarUsuario(u)!=-1) {
            Toast.makeText(this, "Usuario registrado con éxito, inicie sesión", Toast.LENGTH_LONG).show();
            ocultarTodo();
        }
        else
            Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
    }
    public void login(){
        String login;
        if(comprobarCampos()){
            if (!(login=devolverUserId()).equals("null")){
                Toast.makeText(this, "Usuario logeado con éxito", Toast.LENGTH_LONG).show();
                guardarPreferencias(Integer.parseInt(login));
                cargarIntent(MainActivity.class);
            }
            else
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
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
    public String devolverUserId(){
        String id = new ConexionBBDD(this).devolverUsuario(nickid.getText().toString(),passid.getText().toString());
        return id;
    }
    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }


}

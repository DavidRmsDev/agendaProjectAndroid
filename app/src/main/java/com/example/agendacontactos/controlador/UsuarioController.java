package com.example.agendacontactos.controlador;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.agendacontactos.api.service.UsuarioService;
import com.example.agendacontactos.modelo.Usuario;
import com.example.agendacontactos.vista.LoginActivity;
import com.example.agendacontactos.vista.MainActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsuarioController {

    private ConexionRetrofit retrofit;
    private UsuarioService usuarioService;

    public UsuarioController() {
        retrofit = new ConexionRetrofit();
        usuarioService = retrofit.getRetrofit().create(UsuarioService.class);
    }

    public void comprobarLogin(String nick, String password, LoginActivity loginActivity) {
        Call<Boolean> call = usuarioService.registrarUsuario(nick,password);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful())
                {
                    if(response.body()){
                        Toast.makeText(loginActivity, "Usuario registrado con éxito, inicie sesión", Toast.LENGTH_LONG).show();
                        loginActivity.ocultarTodo();
                    }
                    else
                        Toast.makeText(loginActivity, "Usuario ya registrado", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(loginActivity, "Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    public void registrarNuevo(String nickname, String password, LoginActivity loginActivity){
        Call<Usuario> call = usuarioService.devolverUsuario(nickname, password);

        Usuario usuario = new Usuario();

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful())
                {
                    usuario.setUser(response.body().getUser());

                    if (!(usuario.getUser()).equals("null")){
                        int user = usuario.getUser().intValue();

                        loginActivity.guardarPreferencias(user);
                        Toast.makeText(loginActivity, "Usuario logeado con éxito", Toast.LENGTH_LONG).show();
                        loginActivity.cargarIntent(MainActivity.class);
                    }
                    else
                        Toast.makeText(loginActivity, "Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
                else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(loginActivity, "Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }
}

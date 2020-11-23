package com.example.agendacontactos.api.service;

import com.example.agendacontactos.modelo.Usuario;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioService {

    @GET("login")
    Call<Usuario> devolverUsuario(@Query("nickname") String nickname, @Query("password") String password);

    @POST("registro")
    @FormUrlEncoded
    Call<Boolean> registrarUsuario(@Field("nickname") String nickname, @Field("password") String password);
}

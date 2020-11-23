package com.example.agendacontactos.api.service;

import com.example.agendacontactos.modelo.Contacto;
import com.example.agendacontactos.modelo.Notas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NotaService {

    @GET("seleccionaNota")
    public Call<List<Notas>> listarNotas(@Query("usu") int usu);

    @GET("seleccionaUnaNota")
    public Call<Notas> seleccionaUnaNota(@Query("idr") int idr);

    @POST("insertarNota")
    @FormUrlEncoded
    public Call<Boolean> insertarNota(@Field("usu") int usu, @Field("title") String title, @Field("note") String note,
                                          @Field("fech") String fech);

    @PUT("modificarNota")
    @FormUrlEncoded
    public Call<Boolean> modificarNota(@Field("id") int id, @Field("title") String title, @Field("note") String note,
                                       @Field("fech") String fech);

    @DELETE("borrarNota")
    public Call<Boolean> borrarNota(@Query("idr") int idr);
}

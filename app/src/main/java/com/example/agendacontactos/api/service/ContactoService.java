package com.example.agendacontactos.api.service;

import com.example.agendacontactos.modelo.Contacto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ContactoService {

    @GET("contactos")
    public Call<List<Contacto>> listarContactos(@Query("id") int idUser);

    @GET("seleccionaUnContacto")
    public Call<Contacto> seleccionaUnContacto(@Query("idr") int idr);

    @POST("insertarContacto")
    @FormUrlEncoded
    public Call<Boolean> insertarContacto(@Field("idUser") int id, @Field("name") String name, @Field("ape") String ape,
                                          @Field("tel") int tel, @Field("dire") String dire, @Field("emilio") String emilio);

    @PUT("modificarContacto")
    @FormUrlEncoded
    public Call<Boolean> modificarContacto(@Field("id") int id, @Field("name") String name, @Field("ape") String ape,
                                           @Field("tel") int tel, @Field("dire") String dire, @Field("emilio") String emilio);
    @DELETE("borrarContacto")
    public Call<Boolean> borrarContacto(@Query("idr") int idr);
}

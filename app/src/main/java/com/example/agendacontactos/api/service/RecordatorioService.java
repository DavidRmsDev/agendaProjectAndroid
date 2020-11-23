package com.example.agendacontactos.api.service;

import com.example.agendacontactos.modelo.Contacto;
import com.example.agendacontactos.modelo.Recordatorio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RecordatorioService {

    @GET("seleccionaRecordatorio")
    public Call<List<Recordatorio>> listarRecordatorios(@Query("usu") int usu);

    @GET("seleccionaUnRecordatorio")
    public Call<Recordatorio> seleccionaUnRecordatorio(@Query("idr") int idr);

    @POST("insertarRecordatorio")
    @FormUrlEncoded
    public Call<Boolean> insertarRecordatorio(@Field("usu") int usu, @Field("title") String title, @Field("fech") String fech,
                                          @Field("hora") String hora, @Field("descr") String descr);

    @PUT("modificarRecordatorio")
    @FormUrlEncoded
    public Call<Boolean> modificarRecordatorio(@Field("id") int id, @Field("title") String title, @Field("fech") String fech,
                                               @Field("hora") String hora, @Field("descr") String descr);

    @DELETE("borrarRecordatorio")
    public Call<Boolean> borrarRecordatorio(@Query("idr") int idr);
}

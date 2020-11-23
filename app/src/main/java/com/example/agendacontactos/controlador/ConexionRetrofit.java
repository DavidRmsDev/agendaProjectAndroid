package com.example.agendacontactos.controlador;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionRetrofit {

    private Retrofit retrofit;
    /*http://10.0.2.2:8080/*/
    private String url = "https://agendaprojectapi.herokuapp.com/";

    public ConexionRetrofit(){
        retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

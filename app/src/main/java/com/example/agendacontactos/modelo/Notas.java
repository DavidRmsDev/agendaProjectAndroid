package com.example.agendacontactos.modelo;


import java.util.Date;

public class Notas {
    private Long id;
    private int user;
    private String notas;
    private String fecha;
    private String titulo;

    public Notas(Notas nota){
        this.setId(nota.getId());
        this.setUser(nota.getUser());
        this.setNotas(nota.getNotas());
        this.setFecha(nota.getFecha());
        this.setTitulo(nota.getTitulo());
    }

    public Notas(String notas, String fecha, String titulo) {
        this.notas = notas;
        this.fecha = fecha;
        this.titulo = titulo;
    }

    public Notas() {
        this.notas = null;
        this.fecha= new Date().toString();
        this.titulo = null;
        this.id=null;
        this.user=0;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}

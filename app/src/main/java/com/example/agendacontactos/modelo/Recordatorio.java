package com.example.agendacontactos.modelo;

public class Recordatorio {

    private Long id;
    private int user;
    private String descripcion;
    private String fecha;
    private String hora;
    private String titulo;

    public Recordatorio() {
        this.id = null;
        this.user = 0;
        this.descripcion=null;
        this.fecha=null;
        this.hora=null;
        this.titulo=null;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

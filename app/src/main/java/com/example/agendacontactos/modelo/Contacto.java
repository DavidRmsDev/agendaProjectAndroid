package com.example.agendacontactos.modelo;

public class Contacto {

    private Long id;
    private int user;
    private String nombre;
    private String Telefono;
    private String email;
    private String direccion;
    private String apellidos;

    public Contacto(){
        this.nombre = null;
        Telefono = null;
        this.email = null;
        this.direccion = null;
        this.apellidos = null;
        this.id=null;
        this.user=0;
    }

    public Contacto(String nombre, String telefono, String email, String direccion, String apellidos) {
        this.nombre = nombre;
        Telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.apellidos = apellidos;
        this.id=null;
        this.user=0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}

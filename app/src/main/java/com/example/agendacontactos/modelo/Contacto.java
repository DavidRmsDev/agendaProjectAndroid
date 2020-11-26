package com.example.agendacontactos.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contacto {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("user")
    @Expose
    private int user;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("telefono")
    @Expose
    private String Telefono;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("apellidos")
    @Expose
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

    public Contacto(Contacto contacto) {
        this.setNombre(contacto.getNombre());
        this.setApellidos(contacto.getApellidos());
        this.setTelefono(contacto.getTelefono());
        this.setDireccion(contacto.getDireccion());
        this.setEmail(contacto.getEmail());
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

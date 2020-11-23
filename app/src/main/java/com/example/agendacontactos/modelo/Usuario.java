package com.example.agendacontactos.modelo;

public class Usuario {

    private Long user;
    private String nickname;
    private String passsword;

    public Usuario(){
        this.user = null;
        this.nickname = null;
        this.passsword = null;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }
}

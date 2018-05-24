package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model;

import java.io.Serializable;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class Usuario implements Serializable{
    private String nomeCompleto;
    private  String usuario;
    private  String senha;

    public Usuario() {}

    public Usuario(String nome, String usuario, String senha) {
        this.nomeCompleto = nome;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nomeCompleto+"-"+usuario+"-"+senha;
    }
}

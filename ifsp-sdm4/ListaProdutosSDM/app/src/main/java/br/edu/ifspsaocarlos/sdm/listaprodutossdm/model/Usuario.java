package br.edu.ifspsaocarlos.sdm.listaprodutossdm.model;

/**
 * Created by Pio Tofanelli on 29-Nov-17.
 */

public class Usuario {

    private String nome;

    private String senha;

    public Usuario() {
    }

    public Usuario(String nome, String senha) {
        setNome(nome);
        setSenha(senha);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome + ":" + senha;
    }
}

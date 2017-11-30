package br.edu.ifspsaocarlos.sdm.listaprodutossdm.model;

import java.io.Serializable;

/**
 * Created by Pio Tofanelli on 14-Sep-17.
 */

public class Produto implements Serializable {

    private String nome;
    private String descricao;
    private int imagemId;
    private String imagemUri;

    public Produto(String nome, String descricao, int imagemId) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagemId = imagemId;
    }

    public Produto(String nome, String descricao, String imagemUri) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagemUri = imagemUri;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getImagemId() {
        return imagemId;
    }

    public void setImagemId(int imagemId) {
        this.imagemId = imagemId;
    }

    public String getImagemUri() {
        return imagemUri;
    }

    public void setImagemUri(String imagemUri) {
        this.imagemUri = imagemUri;
    }
}

package br.edu.ifspsaocarlos.sdm.pa2trabalho.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ptofanelli on 03/22/2018.
 */

public class Contato implements Serializable{

    @Expose
    @SerializedName("nome_completo")
    private String nomeCompleto;
    @Expose
    private String apelido;
    @Expose
    private String id;

    private String protocolo;



    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }
}

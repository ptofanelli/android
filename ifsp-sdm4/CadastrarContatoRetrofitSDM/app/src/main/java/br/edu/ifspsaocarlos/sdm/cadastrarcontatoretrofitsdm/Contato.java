package br.edu.ifspsaocarlos.sdm.cadastrarcontatoretrofitsdm;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ptofanelli on 03/16/2018.
 */

public class Contato {

    @SerializedName("nome_completo")
    private String nomeCompleto;
    private String apelido;
    private String id;

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
}

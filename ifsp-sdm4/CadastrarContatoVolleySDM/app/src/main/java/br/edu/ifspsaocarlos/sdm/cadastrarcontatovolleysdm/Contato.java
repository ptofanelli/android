package br.edu.ifspsaocarlos.sdm.cadastrarcontatovolleysdm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ptofanelli on 03/09/2018.
 */

public class Contato {

    @SerializedName("nome_completo")
    private String nomeCompleto;
    @Expose
    private String apelido;
    @Expose
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

package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.endpoint;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.Endpoint;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public abstract class AbstractEndpoint<T> implements Endpoint<T> {

    protected MensageiroApi api;

    public AbstractEndpoint(MensageiroApi api) {
        this.api = api;
    }

}

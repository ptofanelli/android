package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests;

import java.io.IOException;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApi;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public abstract class AbstractRequest<T> implements Request<T> {

    protected MensageiroApi api;

    protected AbstractRequest(MensageiroApi api, Object... args) {
        this.api = api;
    }

    public abstract T execute() throws IOException;

    public abstract void enqueue(Callback<T> callback);
}

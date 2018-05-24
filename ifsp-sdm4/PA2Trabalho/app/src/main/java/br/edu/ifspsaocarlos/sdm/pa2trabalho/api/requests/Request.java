package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests;

import java.io.IOException;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public interface Request<T> {

    public T execute() throws IOException;

    public void enqueue(Callback<T> callback);

}

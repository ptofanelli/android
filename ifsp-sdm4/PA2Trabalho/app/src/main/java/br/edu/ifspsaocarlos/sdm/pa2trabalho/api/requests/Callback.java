package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public interface Callback<T> {
    public void onResponse(Request request, T response);

    public void onFailure(Request request, Throwable throwable);
}

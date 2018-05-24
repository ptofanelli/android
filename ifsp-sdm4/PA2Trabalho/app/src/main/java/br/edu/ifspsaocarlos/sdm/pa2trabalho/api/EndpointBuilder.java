package br.edu.ifspsaocarlos.sdm.pa2trabalho.api;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public interface EndpointBuilder {

    public Endpoint getEndpoint(Class requestType, Object... args);
}

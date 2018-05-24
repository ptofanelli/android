package br.edu.ifspsaocarlos.sdm.pa2trabalho.repository;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.Endpoint;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.EndpointBuilder;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.Protocol;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public abstract class AbstractRepository<T> implements Repository<T> {

    protected Protocol<T> protocol;

    protected Endpoint<T> endpoint;

    protected EndpointBuilder endpointBuilder;

    public AbstractRepository(EndpointBuilder endpointBuilder) {
        this.endpointBuilder = endpointBuilder;
    }
}

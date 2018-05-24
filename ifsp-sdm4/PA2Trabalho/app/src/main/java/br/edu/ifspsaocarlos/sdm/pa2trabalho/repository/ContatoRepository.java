package br.edu.ifspsaocarlos.sdm.pa2trabalho.repository;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.EndpointBuilder;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.endpoint.ContatoEndpoint;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.requests.ContatoProtocol;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public class ContatoRepository extends AbstractRepository<Contato> {

    public ContatoRepository(EndpointBuilder endpointBuilder) {
        super(endpointBuilder);
        protocol = new ContatoProtocol();
        endpoint = endpointBuilder.getEndpoint(ContatoEndpoint.class);
    }

    @Override
    public Contato get(Object id) {
        return null;
    }

    @Override
    public List<Contato> getAll() throws IOException {
        List<Contato> contatos;

        contatos = endpoint.getAll();
        contatos = protocol.decode(contatos);

        return contatos;
    }
}

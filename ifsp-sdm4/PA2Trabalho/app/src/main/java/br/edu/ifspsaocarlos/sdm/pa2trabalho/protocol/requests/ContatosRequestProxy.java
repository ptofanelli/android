package br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.RequestBuilder;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Callback;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.ContatosRequest;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Request;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class ContatosRequestProxy implements Request<List<Contato>> {

    private ContatosRequest request;

    public ContatosRequestProxy(RequestBuilder builder) {
        request = (ContatosRequest) builder.build(ContatosRequest.class);
    }

    @Override
    public List<Contato> execute() throws IOException {
        return processProtocol(request.execute());
    }

    @Override
    public void enqueue(final Callback<List<Contato>> callback) {
        request.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Request request, List<Contato> response) {
                List<Contato> contatos = processProtocol(response);
                callback.onResponse(request, contatos);
            }

            @Override
            public void onFailure(Request request, Throwable throwable) {
                callback.onFailure(request, throwable);
            }
        });
    }

    private List<Contato> processProtocol(List<Contato> contatos) {

        contatos = processAppId(contatos);
        contatos = removeProtocolData(contatos);

        return contatos;
    }

    private List<Contato> processAppId(List<Contato> contatos) {

        List<Contato> contatosResult = new ArrayList<Contato>();

        for (Contato contato : contatos) {
            if(contato.getApelido().startsWith("#PT")) {
                contatosResult.add(contato);
            }
        }

        return contatosResult;
    }

    private List<Contato> removeProtocolData(List<Contato> contatos) {
        for (Contato contato : contatos) {
            contato.setApelido(contato.getApelido().split("!")[1]);
        }

        return contatos;
    }
}

package br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.requests;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.RequestBuilder;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Callback;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.MensagensRequest;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Request;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Mensagem;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class MensagensRequestProxy implements Request<List<Mensagem>> {

    private MensagensRequest request;

    public MensagensRequestProxy(RequestBuilder builder, int idOrigem, int idDestido, int idUltimaMensagem) {
        request = (MensagensRequest) builder.build(MensagensRequest.class, idOrigem, idDestido, idUltimaMensagem);
    }

    @Override
    public List<Mensagem> execute() throws IOException {
        return request.execute();
    }

    @Override
    public void enqueue(Callback<List<Mensagem>> callback) {
        request.enqueue(callback);
    }
}

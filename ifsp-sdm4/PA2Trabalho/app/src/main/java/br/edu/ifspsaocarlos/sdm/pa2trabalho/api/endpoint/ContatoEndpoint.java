package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.endpoint;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.ContatosRequest;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ptofanelli on 23-Apr-18.
 */

public class ContatoEndpoint extends AbstractEndpoint<Contato> {

    public ContatoEndpoint(MensageiroApi api) {
        super(api);
    }

    @Override
    public Contato get(Object... args) {
        return null;
    }

    @Override
    public List<Contato> getAll() throws IOException {
        return api.getContatos().execute().body();
    }
}

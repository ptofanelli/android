package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Mensagem;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class ContatosRequest extends AbstractRequest<List<Contato>> {

    private Call<List<Contato>> call;

    protected ContatosRequest(MensageiroApi api, Object... args) {
        super(api, args);

        call = api.getContatos();
    }

    @Override
    public List<Contato> execute() throws IOException {
        return call.execute().body();
    }

    @Override
    public void enqueue(final Callback<List<Contato>> callback) {
        call.enqueue(new retrofit2.Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {
                callback.onResponse(ContatosRequest.this, response.body());
            }

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                callback.onFailure(ContatosRequest.this, t);
            }
        });
    }
}

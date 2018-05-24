package br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Mensagem;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class MensagensRequest extends AbstractRequest<List<Mensagem>>{

    private String idOrigem;
    private String idDestino;
    private String idUltimaMensagem;

    private Call<List<Mensagem>> call;

    /**
     *
     * @param api
     * @param args int idOrigem, int idDestino, int idUltimaMensagem
     */
    protected MensagensRequest(MensageiroApi api, Object... args) {
        super(api, args);

        idOrigem = Integer.toString((int)args[0]);
        idDestino = Integer.toString((int)args[1]);
        idUltimaMensagem = Integer.toString((int)args[2]);

        call = api.getMensagems(idUltimaMensagem, idOrigem, idDestino);
    }

    @Override
    public List<Mensagem> execute() throws IOException {
        return call.execute().body();
    }

    @Override
    public void enqueue(final Callback<List<Mensagem>> callback) {
        call.enqueue(new retrofit2.Callback<List<Mensagem>>() {
            @Override
            public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                callback.onResponse(MensagensRequest.this, response.body());
            }

            @Override
            public void onFailure(Call<List<Mensagem>> call, Throwable t) {
                callback.onFailure(MensagensRequest.this, t);
            }
        });

    }
}

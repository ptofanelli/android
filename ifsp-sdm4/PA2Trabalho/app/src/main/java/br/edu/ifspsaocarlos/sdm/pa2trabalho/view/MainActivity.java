package br.edu.ifspsaocarlos.sdm.pa2trabalho.view;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.R;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApiHelper;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.RequestBuilder;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Callback;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.MensagensRequest;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Request;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.context.ApplicationContext;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Contato;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.requests.ContatosRequestProxy;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.protocol.requests.MensagensRequestProxy;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.repository.ContatoRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MensageiroApiHelper helper = ((ApplicationContext) getApplication()).getApiHelper();

        final ContatoRepository contatoRepository = new ContatoRepository(helper);

        AsyncTask<Void, Void, List<Contato>> getAllContatos = new AsyncTask<Void, Void, List<Contato>>() {
            @Override
            protected List<Contato> doInBackground(Void... voids) {
                try {
                    return contatoRepository.getAll();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(List<Contato> list) {
                super.onPostExecute(list);

                Log.v("GET ALL CONTATOS", list.toString());

            }
        };

        getAllContatos.execute();

    }
}

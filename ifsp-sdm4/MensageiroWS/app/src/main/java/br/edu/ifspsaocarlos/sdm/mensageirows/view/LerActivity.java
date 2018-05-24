package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LerActivity extends AppCompatActivity {

    public static final String EXTRA_LER_MENSAGENS_ENTRE_CONTATOS = "EXTRA_LER_MENSAGENS_ENTRE_CONTATOS";

    private boolean lerEntreContatos;
    private EditText origemEt;
    private EditText destinoEt;
    private EditText ultimaMensagemEt;
    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;

    private Set<Mensagem> mensagemSet;

    private Call<List<Mensagem>> listaMensagensOrigemDestinoCall;
    private Call<List<Mensagem>> listaMensagensDestinoOrigemCall;
    private GetMensagensCallBack callBack;

    private boolean mostrarMensagens;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ler);
        origemEt = findViewById(R.id.et_origem_ler);
        destinoEt = findViewById(R.id.et_destino_ler);
        ultimaMensagemEt = findViewById(R.id.et_ultima_mensagem_ler);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);

        lerEntreContatos = getIntent().getExtras().getBoolean(EXTRA_LER_MENSAGENS_ENTRE_CONTATOS, false);
        mensagemSet = new TreeSet<Mensagem>(new Comparator<Mensagem>() {
            @Override
            public int compare(Mensagem o1, Mensagem o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        callBack = new GetMensagensCallBack();


    }

    public void onClick(View v) {
        if (v.getId() == R.id.bt_ler){

            AsyncTask<String, Void, List<Mensagem>> ObterMensagensTask = new AsyncTask<String, Void, List<Mensagem>>() {
                @Override
                protected List<Mensagem> doInBackground(String... strings) {
                    listaMensagensOrigemDestinoCall = mensageiroApi.getMensagems(ultimaMensagemEt.getText().toString(), origemEt.getText().toString(), destinoEt.getText().toString());
                    try {
                        List<Mensagem> list = listaMensagensOrigemDestinoCall.execute().body();
                        mensagemSet.addAll(list);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(lerEntreContatos) {
                        listaMensagensDestinoOrigemCall = mensageiroApi.getMensagems(ultimaMensagemEt.getText().toString(), destinoEt.getText().toString(), origemEt.getText().toString());
                        try {
                            List<Mensagem> list = listaMensagensDestinoOrigemCall.execute().body();
                            mensagemSet.addAll(list);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    return new ArrayList<Mensagem>(mensagemSet);
                }

                @Override
                protected void onPostExecute(List<Mensagem> mensagems) {
                    super.onPostExecute(mensagems);
                    MostrarMensagensActivity.setMensagemList(mensagems);
                    Intent mostraMensagensIntent = new Intent(LerActivity.this, MostrarMensagensActivity.class);
                    startActivity(mostraMensagensIntent);
                }
            };

            ObterMensagensTask.execute(ultimaMensagemEt.getText().toString(), origemEt.getText().toString(), destinoEt.getText().toString());


            /*
            mostrarMensagens = !lerEntreContatos;

            listaMensagensOrigemDestinoCall = mensageiroApi.getMensagems(ultimaMensagemEt.getText().toString(), origemEt.getText().toString(), destinoEt.getText().toString());
            listaMensagensOrigemDestinoCall.enqueue(callBack);

            if(lerEntreContatos) {
                listaMensagensDestinoOrigemCall = mensageiroApi.getMensagems(ultimaMensagemEt.getText().toString(), destinoEt.getText().toString(), origemEt.getText().toString());
                listaMensagensDestinoOrigemCall.enqueue(callBack);

                while (!listaMensagensOrigemDestinoCallFinished && !listaMensagensDestinoOrigemCallFinished) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                while (!listaMensagensOrigemDestinoCallFinished) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            */


        }
    }

    private class GetMensagensCallBack implements Callback<List<Mensagem>> {

        @Override
        public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
            List<Mensagem> listaMensagens = response.body();
            mensagemSet.addAll(listaMensagens);

        }

        @Override
        public void onFailure(Call<List<Mensagem>> call, Throwable t) {
            Toast.makeText(LerActivity.this, "Erro na recuperação das mensagens!", Toast.LENGTH_SHORT).show();
        }
    }
}


package br.edu.ifspsaocarlos.sdm.buscarmensagemretrofitsdm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MENSAGENS_STRING_ARRAY = "EXTRA_MENSAGENS_STRING_ARRAY";

    private static final String URL_BASE = "http://www.nobile.pro.br/sdm/mensageiro/";
    private final String CORPO_MENSAGEM_JSON = "corpo";

    private EditText idDestinatarioEditText;
    private EditText idRemetenteEditText;
    private EditText idUltimaMensagemEditText;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    private void bindViews() {
        idDestinatarioEditText = findViewById(R.id.et_destinatario);
        idRemetenteEditText = findViewById(R.id.et_remetente);
        idUltimaMensagemEditText = findViewById(R.id.et_ultima_mensagem);
    }

    public void buscarMensagens(View view) {
        if(view.getId() == R.id.bt_buscar_mensagens) {
            MensageiroApi api = retrofit.create(MensageiroApi.class);
            //api.getMensagemByPathID(idUltimaMensagemEditText.getText().toString()).enqueue(new Callback<ResponseBody>() {

            String ultimaMensagem = idUltimaMensagemEditText.getText().toString();
            String origemId = idRemetenteEditText.getText().toString();
            String destinoId = idDestinatarioEditText.getText().toString();

            api.getRawMensagensByPath(ultimaMensagem, origemId, destinoId).enqueue(new Callback<List<Mensagem>>() {
                @Override
                public void onResponse(Call<List<Mensagem>> call, Response<List<Mensagem>> response) {
                    List<Mensagem> listaMensagens = response.body();
                    ArrayList<String> mensagensArrayList = new ArrayList<>();

                    for(Mensagem msg : listaMensagens) {
                        mensagensArrayList.add(msg.getCorpo());
                    }

                    Intent mostraMensagensIntent = new Intent(getApplicationContext(), MostrarMensagensActivity.class);
                    mostraMensagensIntent.putStringArrayListExtra(EXTRA_MENSAGENS_STRING_ARRAY, mensagensArrayList);
                    startActivity(mostraMensagensIntent);
                }

                @Override
                public void onFailure(Call<List<Mensagem>> call, Throwable t) {

                }
            });

/*
            api.getMensagensByQuery(ultimaMensagem, origemId, destinoId).enqueue(new Callback<ListaMensagens>() {
                @Override
                public void onResponse(Call<ListaMensagens> call, Response<ListaMensagens> response) {
                    ListaMensagens listaMensagens = response.body();
                    ArrayList<String> mensagensArrayList = new ArrayList<>();

                    for(Mensagem msg : listaMensagens.getMensagens()) {
                        mensagensArrayList.add(msg.getCorpo());
                    }

                    Intent mostraMensagensIntent = new Intent(getApplicationContext(), MostrarMensagensActivity.class);
                    mostraMensagensIntent.putStringArrayListExtra(EXTRA_MENSAGENS_STRING_ARRAY, mensagensArrayList);
                    startActivity(mostraMensagensIntent);
                }

                @Override
                public void onFailure(Call<ListaMensagens> call, Throwable t) {

                }
            });
*/

            /*
            api.getMensagemByQueryId(idUltimaMensagemEditText.getText().toString()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        JSONObject mensagemJson = new JSONObject(response.body().string());
                        ArrayList<String> mensagensArrayList = new ArrayList<>();

                        if (mensagemJson != null) {
                            mensagensArrayList.add(mensagemJson.getString(CORPO_MENSAGEM_JSON));
                        }

                        Intent mostraMensagensIntent = new Intent(getApplicationContext(), MostrarMensagensActivity.class);
                        mostraMensagensIntent.putStringArrayListExtra(EXTRA_MENSAGENS_STRING_ARRAY, mensagensArrayList);
                        startActivity(mostraMensagensIntent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG);
                }
            });*/

            limparCampos();
        }
    }

    private void limparCampos() {
        idUltimaMensagemEditText.setText("");
        idRemetenteEditText.setText("");
        idUltimaMensagemEditText.setText("");
    }
}

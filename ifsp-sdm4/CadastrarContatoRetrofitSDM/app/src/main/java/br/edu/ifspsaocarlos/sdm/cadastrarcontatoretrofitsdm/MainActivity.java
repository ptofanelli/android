package br.edu.ifspsaocarlos.sdm.cadastrarcontatoretrofitsdm;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final String URL_BASE = "http://nobile.pro.br/sdm/mensageiro/";

    private EditText nomeCompletoEditText;
    private EditText apelidoEditText;

    private Retrofit retrofit;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeCompletoEditText = findViewById(R.id.et_nome_completo);
        apelidoEditText = findViewById(R.id.et_apelido);

        gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                //.addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public void cadastrarContato(View view) {
        if (view.getId() == R.id.bt_cadastrar) {
            Contato contato = new Contato();
            contato.setNomeCompleto(nomeCompletoEditText.getText().toString());
            contato.setApelido(apelidoEditText.getText().toString());

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(contato));

            MensageiroApi api = retrofit.create(MensageiroApi.class);

            Call<ResponseBody> call = api.postContato(requestBody);

            AsyncTask<Call<ResponseBody>, Void, Contato> sendCallAsyncTask = new AsyncTask<Call<ResponseBody>, Void, Contato>() {
                @Override
                protected Contato doInBackground(Call<ResponseBody>[] calls) {
                    Call<ResponseBody> call = calls[0];
                    Contato contatoResposta = null;

                    try {
                        Response<ResponseBody> response = call.execute();
                        contatoResposta = gson.fromJson(response.body().string(), Contato.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return contatoResposta;
                }

                @Override
                protected void onPostExecute(Contato contato) {
                    Toast.makeText(getApplicationContext(), "Id contato: " + contato.getId(), Toast.LENGTH_SHORT).show();
                }
            };

            sendCallAsyncTask.execute(call);

            /*
            try {
                Response<ResponseBody> response = call.execute();

                Contato contatoResposta = gson.fromJson(response.body().toString(), Contato.class);

                Toast.makeText(this, "Id contato: " + contatoResposta.getId(), Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            */

        }
    }
}

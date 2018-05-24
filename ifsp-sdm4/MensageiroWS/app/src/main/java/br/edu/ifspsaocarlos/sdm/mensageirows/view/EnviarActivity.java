package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EnviarActivity extends AppCompatActivity {
    private EditText origemEt;
    private EditText destinoEt;
    private EditText assuntoEt;
    private EditText corpoEt;
    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);
        origemEt = findViewById(R.id.et_origem_enviar);
        destinoEt =findViewById(R.id.et_destino_enviar);
        assuntoEt = findViewById(R.id.et_assunto_enviar);
        corpoEt = findViewById(R.id.et_corpo_enviar);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.bt_enviar) {
            Mensagem mensagem = new Mensagem();
            mensagem.setCorpo(corpoEt.getText().toString());
            mensagem.setAssunto(assuntoEt.getText().toString());
            mensagem.setOrigemId(origemEt.getText().toString());
            mensagem.setDestinoId(destinoEt.getText().toString());
            RequestBody mensagemRb =
                    RequestBody.create(MediaType.parse("application/json"),
                            gson.toJson(mensagem));
            mensageiroApi.postMensagem(mensagemRb).enqueue(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {
                            Toast.makeText(EnviarActivity.this, "Mensagem enviada!",
                                    Toast.LENGTH_SHORT).show();
                            limparCampos();
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(EnviarActivity.this, "Erro no envio da mensagem!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }
    private void limparCampos(){
        origemEt.setText("");
        destinoEt.setText("");
        assuntoEt.setText("");
        corpoEt.setText("");
    }
}


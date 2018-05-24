package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Contato;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NovoContatoActivity extends AppCompatActivity{

    private EditText nomeCompletoEditText;
    private EditText apelidoEditText;
    private Button cadastrarButton;

    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);


        String mensagemDaNotificacao = getIntent().getStringExtra(getString(R.string.mensagem_da_notificacao_extra));

        if (mensagemDaNotificacao != null) {
            Toast.makeText(this, mensagemDaNotificacao, Toast.LENGTH_SHORT).show();
        }

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancelAll();

        bindViews();
    }

    private void bindViews() {
         nomeCompletoEditText = findViewById(R.id.novo_contato_nome_completo);
        apelidoEditText = findViewById(R.id.novo_contato_apelido);

        cadastrarButton = findViewById(R.id.novo_contato_cadastrar);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contato contato = new Contato();
                contato.setNomeCompleto(nomeCompletoEditText.getText().toString());
                contato.setApelido(apelidoEditText.getText().toString());

                RequestBody contatoRb = RequestBody.create(MediaType.parse("application/json"), gson.toJson(contato));
                mensageiroApi.postNovoContato(contatoRb).enqueue(
                        new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Toast.makeText(NovoContatoActivity.this, "Contato cadastrado!", Toast.LENGTH_SHORT).show();
                                limparCampos();
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(NovoContatoActivity.this, "Erro no cadastro do contato!", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void limparCampos() {
        nomeCompletoEditText.setText("");
        apelidoEditText.setText("");
    }
}

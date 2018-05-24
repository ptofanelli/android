package br.edu.ifspsaocarlos.sdm.buscarmensagensvolleysdm;

import android.content.Intent;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MENSAGENS_STRING_ARRAY = "EXTRA_MENSAGENS_STRING_ARRAY";

    private final String URL_BASE = "http://nobile.pro.br/sdm/mensageiro/";
    private final String END_POINT = "mensagem";

    private final String ARRAY_MENSAGENS_JSON = "mensagens";
    private final String CORPO_MENSAGENS_JSON = "corpo";

    private EditText remetenteEditText;
    private EditText destinatarioEditText;
    private EditText ultimaMensagemEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        remetenteEditText = findViewById(R.id.et_remetente);
        destinatarioEditText = findViewById(R.id.et_destinatario);
        ultimaMensagemEditText = findViewById(R.id.et_ultima_mensagem);
    }

    public void buscarMensagens(View view) {
        if(view.getId() == R.id.bt_buscar_mensagens) {
            StringBuilder builder = new StringBuilder();
            builder.append(URL_BASE+END_POINT);
            builder.append("/" + ultimaMensagemEditText.getText().toString());
            builder.append("/" + remetenteEditText.getText().toString());
            builder.append("/" + destinatarioEditText.getText().toString());

            String url = builder.toString();

            RequestQueue filaRequisicoes = Volley.newRequestQueue(this);
            JsonObjectRequest buscaMensagensJsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new ResponseListener(),
                    new ErrorListener()
            );

            filaRequisicoes.add(buscaMensagensJsonObjectRequest);

            remetenteEditText.setText("");
            destinatarioEditText.setText("");
            ultimaMensagemEditText.setText("");
        }

    }

    private class ResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            try {
                JSONArray mensagens = response.getJSONArray(ARRAY_MENSAGENS_JSON);
                ArrayList<String> mensagemList = new ArrayList<>();

                for(int i = 0; i < mensagens.length() ; i++) {
                    JSONObject mensagemJsonObject = (JSONObject) mensagens.get(i);
                    mensagemList.add(mensagemJsonObject.getString(CORPO_MENSAGENS_JSON));
                }

                Intent mostrarMensagensIntent = new Intent(MainActivity.this, MostrarMensagensActivity.class);
                mostrarMensagensIntent.putStringArrayListExtra(EXTRA_MENSAGENS_STRING_ARRAY, mensagemList);
                startActivity(mostrarMensagensIntent);

            } catch (JSONException e) {
                Toast.makeText(MainActivity.this, "Erro conversaro Json", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    private class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Erro volley: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

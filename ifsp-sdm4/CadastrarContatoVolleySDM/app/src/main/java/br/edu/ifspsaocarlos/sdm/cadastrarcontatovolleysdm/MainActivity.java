package br.edu.ifspsaocarlos.sdm.cadastrarcontatovolleysdm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String URL_BASE = "http://nobile.pro.br/sdm/mensageiro/";
    private final String END_POINT = "contato";
    private final String CAMPO_NOME_JSON = "nome_completo";
    private final String CAMPO_APELIDO_JSON = "apelido";

    private EditText nomeCompletoEditText;
    private EditText apelidoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeCompletoEditText = findViewById(R.id.et_nome_completo);
        apelidoEditText = findViewById(R.id.et_apelido);

        //Reflection test
        Class<Contato> contatoClass = Contato.class;
        Log.v("REFLECTION", "Class name: " + contatoClass.getName());
        for (Field field : contatoClass.getDeclaredFields()) {
            Log.v("REFLECTION", "Attribute: " + field.getName() + ", type: " + field.getType());
        }

        for(Method method : contatoClass.getDeclaredMethods()) {
            Log.v("REFLECTION", "Method: " + method.getName() + ", returns: " + method.getReturnType());
        }

        try {
            Contato contato = contatoClass.newInstance();
            contato.setApelido("Pedro");
            Method method = contato.getClass().getDeclaredMethod("getApelido");
            Log.v("REFLECTION", "getApelido: " + method.invoke(contato, null));
        } catch (Exception e) {

        }
    }

    public void cadastrarContato(View view) {
        if(view.getId() == R.id.bt_cadastrar) {
            JSONObject novoContatoJsonObject = new JSONObject();
            try {
                novoContatoJsonObject.put(CAMPO_NOME_JSON, nomeCompletoEditText.getText().toString());
                novoContatoJsonObject.put(CAMPO_APELIDO_JSON, apelidoEditText.getText().toString());

                RequestQueue filaRequisicoes = Volley.newRequestQueue(this);
                JsonObjectRequest buscaMensagensJsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL_BASE+END_POINT,
                        novoContatoJsonObject,
                        new ResponseListener(),
                        new ErrorListener()
                );

                filaRequisicoes.add(buscaMensagensJsonObjectRequest);

                nomeCompletoEditText.setText("");
                apelidoEditText.setText("");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private class ResponseListener implements Response.Listener<JSONObject> {

        @Override
        public void onResponse(JSONObject response) {
            //String id = response.getString("id");
            Gson gson = new Gson();
            Contato contato = gson.fromJson(response.toString(), Contato.class);

            Toast.makeText(MainActivity.this, "id: " + contato.getId(), Toast.LENGTH_SHORT).show();

            //GSON expose annotation sample
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            Gson gson1 = gsonBuilder.create();
            String contatoSemNomeCompleto = gson1.toJson(contato);

            Toast.makeText(MainActivity.this, "JSON filtrado: " + contatoSemNomeCompleto, Toast.LENGTH_SHORT).show();
        }
    }

    private class ErrorListener implements Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Erro volley: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

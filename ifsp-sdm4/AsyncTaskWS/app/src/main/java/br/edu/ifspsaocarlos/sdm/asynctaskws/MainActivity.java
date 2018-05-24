package br.edu.ifspsaocarlos.sdm.asynctaskws;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String URL_BASE = "http://www.nobile.pro.br/sdm/";
    private Button buscarInfoWSButton;
    private TextView textView;
    private TextView dataTextView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buscarInfoWSButton = findViewById(R.id.bt_buscar_info);
        buscarInfoWSButton.setOnClickListener(this);

        progressBar = findViewById(R.id.progress_bar_carregando);

        dataTextView = findViewById(R.id.tv_data);

        textView = findViewById(R.id.tv_texto);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_buscar_info) {
            buscarTexto(URL_BASE+"texto.php");
            buscarData(URL_BASE+"data.php");
        }
    }

    private void buscarData(String url) {
        AsyncTask<String, Void, JSONObject> buscaDataTask = new AsyncTask<String, Void, JSONObject>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected JSONObject doInBackground(String... strings) {
                String url = strings[0];
                StringBuilder builder = new StringBuilder();
                JSONObject jsonObject = null;

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream));

                        String temp;
                        while((temp = reader.readLine()) != null) {
                            builder.append(temp);
                        }

                        jsonObject = new JSONObject(builder.toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return jsonObject;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                String data = null;
                String hora = null;
                String diaSemana = null;

                try {
                    data = jsonObject.getInt("mday")
                            + "/" + jsonObject.getInt("mon")
                            + "/" + jsonObject.getInt("year");
                    hora = jsonObject.getInt("hours")
                            + ":" + jsonObject.getInt("minutes")
                            + ":" + jsonObject.getInt("seconds");

                    diaSemana = jsonObject.getString("weekday");

                    dataTextView.setText(data + "\n" + hora + "\n" + diaSemana);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.GONE);
            }
        };

        buscaDataTask.execute(url);
    }

    private void buscarTexto(String url) {
        AsyncTask<String, Void, String> buscaTextoAsyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Executa na thread principal
                Toast.makeText(getApplicationContext(), "Buscando String no WS", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... strings) {
                //Executa na thread filha/secundaria
                String url = strings[0];
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream));

                        String temp;
                        while((temp = reader.readLine()) != null) {
                            stringBuilder.append(temp);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return stringBuilder.toString();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Executa principal

                textView.setText(s);
            }
        };

        buscaTextoAsyncTask.execute(url);
    }
}

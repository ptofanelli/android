package br.edu.ifspsaocarlos.sdm.asynctaskwsimagem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private final String URL_BASE = "http://nobile.pro.br/sdm/";
    private final String ARQUIVO_IMAGEM = "logo_ifsp.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void acessarWs(View view) {
        if(view.getId() == R.id.bt_acessar_ws) {
            buscarImagem(URL_BASE+ARQUIVO_IMAGEM);
        }
    }

    private void buscarImagem(String url) {
        AsyncTask<String, Void, Bitmap> buscaImagemAt = new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... strings) {
                String url = strings[0];
                try {
                    HttpURLConnection connection = (HttpURLConnection) (new URL(url)).openConnection();
                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        return BitmapFactory.decodeStream(inputStream);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);

                ImageView imageView = findViewById(R.id.iv_imagem);
                imageView.setImageBitmap(bitmap);
            }
        };

        buscaImagemAt.execute(url);
    }
}

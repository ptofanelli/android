package br.edu.ifspsaocarlos.sdm.mensageirows.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Contato;
import br.edu.ifspsaocarlos.sdm.mensageirows.view.NovoContatoActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ptofanelli on 03/22/2018.
 */

public class BuscaNovoContatoService extends Service implements Runnable {
    private boolean appAberta;
    private boolean primeiraBusca;
    private int ultimoNumeroContatos;
    private int novoNumeroContatos;
    private Gson gson;
    private Retrofit retrofit;
    private MensageiroApi mensageiroApi;
    public BuscaNovoContatoService() {
    }
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate() {
        super.onCreate();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(getString(R.string.url_base));
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);
        appAberta = true;
        primeiraBusca = true;
        ultimoNumeroContatos = 0;
        new Thread(this).start();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    public void run() {
        while (appAberta) {
            try {
                Thread.sleep(getResources().getInteger(
                        R.integer.tempo_inatividade_servico));
                buscaNumeroContatos();
                if (!primeiraBusca && novoNumeroContatos != ultimoNumeroContatos) {
                    NotificationManager nm = (NotificationManager)
                            getSystemService(NOTIFICATION_SERVICE);
                    Intent intent = new Intent(this, NovoContatoActivity.class);
                    intent.putExtra(getString(R.string.mensagem_da_notificacao_extra),
                            getString(R.string.contatos_atualizados));
                    PendingIntent p = PendingIntent.getActivity(this, 0, intent, 0);
                    Notification.Builder builder = new Notification.Builder(this);
                    builder.setSmallIcon(R.drawable.ic_contato);
                    builder.setTicker(getString(R.string.novo_contato_adicionado));
                    builder.setContentTitle(getString(R.string.novo_contato));
                    builder.setContentText(getString(R.string.clique_aqui));
                    builder.setWhen(System.currentTimeMillis());
                    builder.setContentIntent(p);
                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
                            R.mipmap.ic_mensageiro));
                    Notification notification = builder.build();
                    notification.vibrate = new long[] {100, 250};
                    nm.notify(R.mipmap.ic_launcher, notification);
                }
                ultimoNumeroContatos = novoNumeroContatos;
                primeiraBusca = false;
            }
            catch (InterruptedException ie) {
                Log.e("SDM", "Erro na thread de recuperação de contato");
            }
        }
    }
    private void buscaNumeroContatos() {
        mensageiroApi.getContatos().enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>>
                    response) {
                List<Contato> listaContatos = response.body();
                novoNumeroContatos = listaContatos.size();
            }
            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {
                Log.v(getString(R.string.app_name), "Erro na recuperação do número de contatos!");
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        appAberta = false;
        stopSelf();
    }
}

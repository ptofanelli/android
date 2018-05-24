package br.edu.ifspsaocarlos.sdm.pcgui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class AcessoDiretoActivity extends ProgramacaoConcorrenteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setSubtitle(getString(R.string.acesso_direto_subtitle));
    }

    @Override
    protected void carregaImagens() {
        CarregaImagensThread carregaImagensThread = new CarregaImagensThread();
        carregaImagensThread.start();
    }

    private class CarregaImagensThread extends Thread {
        @Override
        public void run() {
            super.run();

            try {
                sleep(1000);

                preencheImagens();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}

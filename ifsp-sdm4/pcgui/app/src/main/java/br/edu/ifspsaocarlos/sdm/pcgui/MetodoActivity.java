package br.edu.ifspsaocarlos.sdm.pcgui;

import android.app.Activity;
import android.os.Bundle;

public class MetodoActivity extends ProgramacaoConcorrenteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setSubtitle(getString(R.string.metodo_activity));

    }

    @Override
    protected void carregaImagens() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    preencheImagens();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}

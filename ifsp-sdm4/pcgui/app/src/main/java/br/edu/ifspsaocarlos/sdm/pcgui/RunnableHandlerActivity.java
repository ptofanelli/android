package br.edu.ifspsaocarlos.sdm.pcgui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class RunnableHandlerActivity extends ProgramacaoConcorrenteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setSubtitle(getString(R.string.runnable_handler));
    }

    @Override
    protected void carregaImagens() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                preencheImagens();
            }
        }, 1000);
    }
}

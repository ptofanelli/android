package br.edu.ifspsaocarlos.sdm.pcgui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class MessageHandlerActivity extends ProgramacaoConcorrenteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setSubtitle(getString(R.string.message_handler_subtitle));
    }

    @Override
    protected void carregaImagens() {
        CarregaImagensHandler handler = new CarregaImagensHandler();
        Message message = new Message();
        message.what = CarregaImagensHandler.CARREGA_IMAGEM;
        handler.sendMessageDelayed(message, 1000);
    }

    private class CarregaImagensHandler extends Handler {
        public static final int CARREGA_IMAGEM = 0;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case CARREGA_IMAGEM:
                    preencheImagens();
                    break;
            }
        }
    }
}

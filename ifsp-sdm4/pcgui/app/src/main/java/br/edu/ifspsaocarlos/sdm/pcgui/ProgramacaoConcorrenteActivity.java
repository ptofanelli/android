package br.edu.ifspsaocarlos.sdm.pcgui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public abstract class ProgramacaoConcorrenteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programacao_concorrente);

        getSupportActionBar().setTitle(R.string.programacao_concorrente_title);

        carregaImagens();
    }

    protected void preencheImagens(){
        ImageView altaImageView = findViewById(R.id.iv_alta);
        altaImageView.setImageResource(R.drawable.android_verde);

        ImageView baixaImageView = findViewById(R.id.iv_baixa);
        baixaImageView.setImageResource(R.drawable.android_preto);
    }

    protected abstract void carregaImagens();
}

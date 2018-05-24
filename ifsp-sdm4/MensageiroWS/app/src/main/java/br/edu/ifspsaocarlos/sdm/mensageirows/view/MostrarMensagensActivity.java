package br.edu.ifspsaocarlos.sdm.mensageirows.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.R;
import br.edu.ifspsaocarlos.sdm.mensageirows.adapter.MensagemArrayAdapter;
import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;

public class MostrarMensagensActivity extends AppCompatActivity {

    public static final String EXTRA_LISTA_MENSAGENS = "EXTRA_LISTA_MENSAGENS";

    private ListView mostrarMensagensLv;

    private static List<Mensagem> mensagemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mensagens);
        mostrarMensagensLv = findViewById(R.id.lv_mostrar_mensagens);

        //ArrayList<String> corpoMensagens = getIntent().getStringArrayListExtra(getString(R.string.mensagens_string_array_extra));

        /*
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                corpoMensagens);
        */

        //ArrayList<Mensagem> mensagemList = (ArrayList<Mensagem>) getIntent().getSerializableExtra(EXTRA_LISTA_MENSAGENS);
         MensagemArrayAdapter adapter = new MensagemArrayAdapter(this, mensagemList);
        mostrarMensagensLv.setAdapter(adapter);
    }

    public static void setMensagemList(List<Mensagem> mensagemList) {
        MostrarMensagensActivity.mensagemList = mensagemList;
    }
}


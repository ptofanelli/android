package br.edu.ifspsaocarlos.sdm.buscarmensagensvolleysdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarMensagensActivity extends AppCompatActivity {

    private ListView mostraMensagensListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_mensagens);

        mostraMensagensListView = findViewById(R.id.lv_mensagens);
        ArrayList<String> mensagens = getIntent().getStringArrayListExtra(MainActivity.EXTRA_MENSAGENS_STRING_ARRAY);

        if(mensagens != null && mensagens.size() > 0) {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mensagens);
            mostraMensagensListView.setAdapter(arrayAdapter);
        } else {
            Toast.makeText(this, "nao ha mensagens", Toast.LENGTH_SHORT);
        }
    }
}

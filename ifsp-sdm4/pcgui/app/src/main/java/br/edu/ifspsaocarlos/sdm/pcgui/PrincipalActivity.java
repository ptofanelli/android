package br.edu.ifspsaocarlos.sdm.pcgui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PrincipalActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView principalListView;
    private String[] opcoesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        opcoesMenu = getResources().getStringArray(R.array.opcoes_menu);
        principalListView = findViewById(R.id.lv_principal);
        principalListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, opcoesMenu));
        principalListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0: //Acesso direto
                Intent acessoDiretoIntent = new Intent(this, AcessoDiretoActivity.class);
                startActivity(acessoDiretoIntent);
                break;
            case 1: //Message e Handler
                Intent messageHandlerIntent = new Intent(this, MessageHandlerActivity.class);
                startActivity(messageHandlerIntent);
                break;
            case 2: //Runnable e Handler
                Intent runnableHandler = new Intent(this, RunnableHandlerActivity.class);
                startActivity(runnableHandler);
                break;
            case 3: //Metodo da Activity
                Intent metodoActivity = new Intent(this, MetodoActivity.class);
                startActivity(metodoActivity);
                break;
        }
    }
}

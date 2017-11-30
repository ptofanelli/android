package br.edu.ifspsaocarlos.sdm.listaprodutossdm.view;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.listaprodutossdm.R;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.adapter.ListaProdutosAdapter;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.model.Produto;

/**
 * Created by Pio Tofanelli on 14-Sep-17.
 */

public class ListaProdutosActivity extends ListActivity {

    private final int CADASTRO_NOVO_PRODUTO_RESULT = 1;

    private List<Produto> listaProdutos;
    private List<String> listaNomeProdutos;

    private ListaProdutosAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializaListaNomeProdutos();
        adapter = new ListaProdutosAdapter(this, listaProdutos);

        setListAdapter(adapter);
    }

    private void inicializaListaNomeProdutos()
    {
        inicializaListaProdutos();

        if(listaNomeProdutos == null)
        {
            listaNomeProdutos = new ArrayList<String>();
            for(Produto produto : listaProdutos)
            {
                listaNomeProdutos.add(produto.getNome());
            }
        }
    }

    private void inicializaListaProdutos() {
        if(listaProdutos == null) {
            listaProdutos = new ArrayList<Produto>();
            listaProdutos.add(new Produto("Amoras", "Amoras Vermelhas", R.drawable.amoras));
            listaProdutos.add(new Produto("Bacon", "É Vida", R.drawable.bacon));
            listaProdutos.add(new Produto("Caneca", "Caneca de porcelana", R.drawable.caneca));
            listaProdutos.add(new Produto("Cenouras", "Cenoura champz", R.drawable.cenouras));
            listaProdutos.add(new Produto("Cerejas", "Cerejas especiais", R.drawable.cerejas));
            listaProdutos.add(new Produto("Cerveja", "Cerveja Gelada", R.drawable.cerveja));
            listaProdutos.add(new Produto("Cogumelos", "Cogumelos Gourmet", R.drawable.cogumelos));
            listaProdutos.add(new Produto("Frutas Vermelhas", "Frutas vermelhas selecionadas", R.drawable.frutas_vermelhas));
            listaProdutos.add(new Produto("Leite", "Leite da fazenda", R.drawable.leite));
            listaProdutos.add(new Produto("Morangos", "Morangos Frescos", R.drawable.morangos));
            listaProdutos.add(new Produto("Mouse", "Mouse optico", R.drawable.mouse));
            listaProdutos.add(new Produto("Muffin", "Muffin Starbucks", R.drawable.muffin));
            listaProdutos.add(new Produto("Nozes", "Nozes de Natal", R.drawable.nozes));
            listaProdutos.add(new Produto("Oculos", "Oculos Top", R.drawable.oculos));
            listaProdutos.add(new Produto("Pêssegos", "Pêssegos orgânicos", R.drawable.pessegos));
            listaProdutos.add(new Produto("Pirulitos", "Tchau brigado", R.drawable.pirulitos));
            listaProdutos.add(new Produto("Torta", "Torta receita da vovó", R.drawable.torta));
            listaProdutos.add(new Produto("Uvas", "Estas mais de mesa. Aqui do lado pederneiras", R.drawable.uvas));
        }

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //Toast.makeText(this, listaNomeProdutos.get(position), Toast.LENGTH_SHORT).show();

        Produto prod = listaProdutos.get(position);

        Intent detalheProdutoIntent = new Intent(this, DetalheProdutoActivity.class);
        detalheProdutoIntent.putExtra("produto", prod);

        startActivity(detalheProdutoIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.item_menu_novo:
                Intent novoProdutoIntent = new Intent(this, NovoProdutoActivity.class);
                startActivityForResult(novoProdutoIntent, CADASTRO_NOVO_PRODUTO_RESULT);
                break;
            case R.id.item_menu_novo_usuario:
                Intent novoUsuarioIntent = new Intent(this, UsuarioActivity.class);
                startActivity(novoUsuarioIntent);
                break;
            case R.id.item_menu_sair:
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CADASTRO_NOVO_PRODUTO_RESULT:
                if(resultCode == RESULT_OK) {
                    Produto produto = (Produto) data.getExtras().get("produto");
                    listaProdutos.add(produto);
                    adapter.notifyDataSetChanged();

                }
                break;
        }
    }
}

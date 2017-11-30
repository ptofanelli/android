package br.edu.ifspsaocarlos.sdm.listaprodutossdm.view;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.listaprodutossdm.R;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.model.Produto;

public class NovoProdutoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECIONAR_ARQUIVO_RESULT_CODE = 0;

    private EditText nomeProdutoEditText;
    private EditText descricaoProdutoEditText;
    private ImageView imagemProdutoImageView;
    private Button selecionarImagemButton;
    private Button cancelarButton;
    private Button salvarButton;
    private Uri imagemUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_produto);

        bindViews();
    }

    private void bindViews(){
        nomeProdutoEditText = (EditText) findViewById(R.id.etNome);
        descricaoProdutoEditText = (EditText) findViewById(R.id.etDescricao);
        imagemProdutoImageView = (ImageView) findViewById(R.id.iv_produto);
        selecionarImagemButton = (Button) findViewById(R.id.btSelecionarImagem);
        selecionarImagemButton.setOnClickListener(this);
        cancelarButton = (Button) findViewById(R.id.bt_cancelar);
        cancelarButton.setOnClickListener(this);
        salvarButton = (Button) findViewById(R.id.bt_salvar);
        salvarButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSelecionarImagem:
                abrirJanelaSelecionarArquivo();
                break;
            case R.id.bt_cancelar:
                finish();
                break;
            case R.id.bt_salvar:
                Intent resultIntent = new Intent();

                String nome = nomeProdutoEditText.getText().toString();
                String descricao = descricaoProdutoEditText.getText().toString();
                Produto produto = new Produto(nome, descricao, imagemUri.toString());

                resultIntent.putExtra("produto", produto);

                setResult(RESULT_OK, resultIntent);

                finish();

                break;
        }
    }

    private void abrirJanelaSelecionarArquivo() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent chooser = Intent.createChooser(intent, "Selecionar arquivo:");
        startActivityForResult(chooser, SELECIONAR_ARQUIVO_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case SELECIONAR_ARQUIVO_RESULT_CODE:
                if(resultCode == RESULT_OK) {
                    // mostrar arquivo de imagem
                    imagemUri = (Uri) data.getData();
                    imagemProdutoImageView.setImageURI(imagemUri);
                } else {
                    Toast.makeText(this, "Arquivo não selecionado", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                Toast.makeText(this, "Falha na seleção de arquivo!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

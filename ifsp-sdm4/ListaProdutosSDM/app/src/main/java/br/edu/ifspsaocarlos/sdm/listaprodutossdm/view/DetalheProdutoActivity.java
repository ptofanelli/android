package br.edu.ifspsaocarlos.sdm.listaprodutossdm.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import br.edu.ifspsaocarlos.sdm.listaprodutossdm.R;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.model.Produto;

public class DetalheProdutoActivity extends AppCompatActivity {

    private Produto produto;

    private TextView txtNome;
    private TextView txtDescricao;
    private ImageView imgProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        bindViews();

        produto = (Produto) getIntent().getExtras().get("produto");
        carregarProduto();
    }

    private void bindViews(){
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtDescricao = (TextView) findViewById(R.id.txtDescricao);
        imgProduto = (ImageView) findViewById(R.id.imgProduto);
    }

    private void carregarProduto()
    {
        txtNome.setText(produto.getNome());
        txtDescricao.setText(produto.getDescricao());

        if(produto.getImagemId() != 0) {
            imgProduto.setImageResource(produto.getImagemId());
        }
        else if(produto.getImagemUri() != null)
        {
            Uri uri = Uri.parse(produto.getImagemUri());
            Bitmap bitmap = getBitmapFromUri(this, uri);
            imgProduto.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapFromUri(Context context, Uri uri){
        Bitmap bitmap = null;
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}

package br.edu.ifspsaocarlos.sdm.listaprodutossdm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.listaprodutossdm.R;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.model.Produto;

/**
 * Created by Pio Tofanelli on 14-Sep-17.
 */

public class ListaProdutosAdapter extends ArrayAdapter<Produto> {
    private LayoutInflater inflater;

    public ListaProdutosAdapter(Context context, List<Produto> produtoList){
        super(context, R.layout.item_lista_produto, produtoList);

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if(v == null)
        {
            v = inflater.inflate(R.layout.item_lista_produto, null);
            holder = new Holder();
            holder.nomeProdutoTextView = (TextView) v.findViewById(R.id.txtNomeProduto);
            holder.imagemProdutoImageView = (ImageView) v.findViewById(R.id.imgImagemProduto);

            v.setTag(holder);
        }
        else
        {
            holder = (Holder) v.getTag();
        }

        Produto prod = getItem(position);

        holder.nomeProdutoTextView.setText(prod.getNome());

        //Trata imagem
        if(prod.getImagemId() != 0) {
            holder.imagemProdutoImageView.setImageResource(prod.getImagemId());
        }
        else if(prod.getImagemUri() != null)
        {
            Uri uri = Uri.parse(prod.getImagemUri());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(v.getContext().getContentResolver(), uri);
                holder.imagemProdutoImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return v;
    }

    private static class Holder {
        public TextView nomeProdutoTextView;
        public ImageView imagemProdutoImageView;
    }
}

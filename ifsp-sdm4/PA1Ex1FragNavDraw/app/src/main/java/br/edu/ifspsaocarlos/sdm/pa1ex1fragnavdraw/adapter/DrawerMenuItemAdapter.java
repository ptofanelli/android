package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.R;
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.DrawerMenuItem;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class DrawerMenuItemAdapter extends ArrayAdapter<DrawerMenuItem> {
    private LayoutInflater inflater;

    public DrawerMenuItemAdapter(Context context, List<DrawerMenuItem> menuItems){
        super(context, R.layout.item_drawer_menu, menuItems);

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if(v == null)
        {
            v = inflater.inflate(R.layout.item_drawer_menu, null);
            holder = new Holder();
            holder.nomeProdutoTextView = (TextView) v.findViewById(R.id.item_textview);
            holder.imagemProdutoImageView = (ImageView) v.findViewById(R.id.item_imageview);

            v.setTag(holder);
        }
        else
        {
            holder = (Holder) v.getTag();
        }

        DrawerMenuItem item = getItem(position);

        holder.nomeProdutoTextView.setText(item.getText());

        //Trata imagem
        if(item.getImagemId() != 0) {
            holder.imagemProdutoImageView.setImageResource(item.getImagemId());
        }
        else if(item.getImagemUri() != null)
        {
            Uri uri = Uri.parse(item.getImagemUri());
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

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
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.Usuario;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class UsuarioAdapter extends ArrayAdapter<Usuario>{
    private LayoutInflater inflater;

    public UsuarioAdapter(Context context, List<Usuario> usuarios){
        super(context, android.R.layout.simple_list_item_1, usuarios);

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        UsuarioAdapter.Holder holder;
        if(v == null)
        {
            v = inflater.inflate(android.R.layout.simple_list_item_1, null);
            holder = new UsuarioAdapter.Holder();
            holder.nomeCompletoTextView = (TextView) v.findViewById(android.R.id.text1);

            v.setTag(holder);
        }
        else
        {
            holder = (UsuarioAdapter.Holder) v.getTag();
        }

        Usuario item = getItem(position);

        holder.nomeCompletoTextView.setText(item.getNomeCompleto());

        return v;
    }

    private static class Holder {
        public TextView nomeCompletoTextView;
    }
}

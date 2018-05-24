package br.edu.ifspsaocarlos.sdm.mensageirows.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.mensageirows.model.Mensagem;

/**
 * Created by ptofanelli on 16-Apr-18.
 */

public class MensagemArrayAdapter extends ArrayAdapter<Mensagem> {

    private LayoutInflater inflater;

    public MensagemArrayAdapter(Context context, List<Mensagem> mensagemList) {
        super(context, android.R.layout.simple_list_item_2, mensagemList);

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        Holder holder;
        if(v == null)
        {
            v = inflater.inflate(android.R.layout.simple_list_item_2, null);
            holder = new Holder();

            holder.text1 = (TextView) v.findViewById(android.R.id.text1);
            holder.text2 = (TextView) v.findViewById(android.R.id.text2);

            v.setTag(holder);
        }
        else
        {
            holder = (Holder) v.getTag();
        }

        Mensagem mensagem = getItem(position);

        SpannableString spanString = new SpannableString(mensagem.getOrigem().getApelido());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        holder.text1.setText(spanString);
        holder.text2.setText(mensagem.getCorpo());

        return v;
    }

    private static class Holder {
        public TextView text1;
        public TextView text2;
    }
}

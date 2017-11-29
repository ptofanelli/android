package com.ptofanelli.sorteador.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ptofanelli.sorteador.model.DiceRollResult;

import java.util.List;

/**
 * Created by Pio Tofanelli on 29-Nov-17.
 */

public class DiceRollArrayAdapter extends ArrayAdapter<DiceRollResult> {

    private LayoutInflater inflater;

    public DiceRollArrayAdapter(@NonNull Context context, List<DiceRollResult> contatos) {
        super(context, android.R.layout.simple_list_item_1, contatos);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        Holder holder;

        if(v == null){
            v = inflater.inflate(android.R.layout.simple_list_item_1, null);
            holder = new Holder();
            holder.diceRollTextView = (TextView) v.findViewById(android.R.id.text1);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }


        DiceRollResult diceRoll = getItem(position);
        holder.diceRollTextView.setText(diceRoll.toString());

        return v;

    }

    private class Holder {
        public TextView diceRollTextView;
    }
}

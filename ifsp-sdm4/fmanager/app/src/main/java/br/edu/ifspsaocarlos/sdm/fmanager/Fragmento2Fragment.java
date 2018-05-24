package br.edu.ifspsaocarlos.sdm.fmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Pio Tofanelli on 09-Feb-18.
 */

public class Fragmento2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, null);
        TextView textView = view.findViewById(R.id.tv_texto);
        textView.setText("Oi, eu sou o fragmento 2");
        return view;

    }
}

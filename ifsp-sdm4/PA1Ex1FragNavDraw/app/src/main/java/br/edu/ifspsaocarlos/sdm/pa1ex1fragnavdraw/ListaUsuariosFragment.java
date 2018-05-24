package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.adapter.UsuarioAdapter;
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.Usuario;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class ListaUsuariosFragment extends Fragment {

    AdapterView.OnItemClickListener listItemClickListener;
    ListView listView;
    List<Usuario> usuarios;
    UsuarioAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_usuarios, null);

        adapter = new UsuarioAdapter(getActivity(), usuarios);
        listView = view.findViewById(R.id.lista_usuarios_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listItemClickListener);

        return view;
    }

    public void setListItemClickListener(AdapterView.OnItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}

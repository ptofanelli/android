package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.helper.UsuarioHelper;
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.Usuario;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class CadastroUsuarioFragment extends Fragment {

    EditText nomeCompletoEditText;
    EditText usuarioEditText;
    EditText senhaEditText;
    Button salvarButton;

    Usuario usuario;
    private UsuarioHelper helper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cadastro_usuario, null);

        nomeCompletoEditText = view.findViewById(R.id.nome_completo_edit_text);
        usuarioEditText = view.findViewById(R.id.usuario_edit_text);
        senhaEditText = view.findViewById(R.id.senha_edit_text);
        salvarButton = view.findViewById(R.id.salvar_cadastro_edit_text);

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usuario != null) {
                    helper.atualizar();
                    Toast.makeText(getContext(), "Usuario salvo", Toast.LENGTH_LONG).show();
                } else {
                    if(!usuarioJaExiste(usuarioEditText.getText().toString())) {
                        usuario = new Usuario();
                        usuario.setNomeCompleto(nomeCompletoEditText.getText().toString());
                        usuario.setUsuario(usuarioEditText.getText().toString());
                        usuario.setSenha(senhaEditText.getText().toString());
                        helper.inserir(usuario);

                        usuarioEditText.setEnabled(false);

                        Toast.makeText(getContext(), "Usuario salvo", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "Usuario já existe", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        Bundle args = getArguments();
        if(args != null) {
            Serializable serializable = args.getSerializable("usuario");
            if (serializable != null && (serializable instanceof Usuario)) {
                usuario = (Usuario) serializable;
                nomeCompletoEditText.setText(usuario.getNomeCompleto());
                usuarioEditText.setText(usuario.getUsuario());
                usuarioEditText.setEnabled(false);
                senhaEditText.setText(usuario.getSenha());
            }
        }

        return view;
    }

    public void setUsuarioHelper(UsuarioHelper helper) {
        this.helper = helper;
    }

    private boolean usuarioJaExiste(String nomeUsuario) {
        for (Usuario u : helper.getUsuarios()) {
            if(u.getUsuario().equalsIgnoreCase(nomeUsuario)) {
                return true;
            }
        }

        return false;
    }
}

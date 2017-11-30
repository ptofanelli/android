package br.edu.ifspsaocarlos.sdm.listaprodutossdm.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import br.edu.ifspsaocarlos.sdm.listaprodutossdm.R;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.model.Usuario;

public class UsuarioActivity extends AppCompatActivity {
    private Map<String, Usuario> usuariosCadastrados;
    private SharedPreferences sharedPreferences;
    private final String SHARED_PREFERENCES_USUARIOS = "lista_usuarios";

    private EditText nomeUsuarioEditText;
    private EditText senhaUsuarioEditText;
    private Button salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        bindViews();

        //sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        sharedPreferences = LoginActivity.getSharedPreferences();
        usuariosCadastrados = carregarUsuarios();
    }

    private void bindViews() {
        nomeUsuarioEditText = (EditText) findViewById(R.id.cadastro_usuario_nome_edittext);
        senhaUsuarioEditText = (EditText) findViewById(R.id.cadastro_usuario_senha_edittext);
        salvarButton = (Button) findViewById(R.id.cadastro_usuario_salvar_button);
        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = nomeUsuarioEditText.getText().toString();
                String senha = senhaUsuarioEditText.getText().toString();

                if(usuariosCadastrados.containsKey(nome)) {
                    Toast.makeText(UsuarioActivity.this, R.string.usuario_ja_configurado, Toast.LENGTH_LONG).show();
                    return;
                }

                if(nome != null && nome.length() > 0 && senha != null && senha.length() > 0) {
                    Usuario usuario = new Usuario(nome, senha);
                    inserirUsuario(usuario);
                    Toast.makeText(UsuarioActivity.this, R.string.usuario_cadastrado_sucesso, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(UsuarioActivity.this, R.string.cadastro_usuario_insira_nome_senha, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Map<String,Usuario> carregarUsuarios(){
        Map<String,Usuario> mapUsuarios = new HashMap<String,Usuario>();
        String[] listaUsuarios = sharedPreferences.getString(SHARED_PREFERENCES_USUARIOS, "").split(";");

        for (int i = 0 ; i < listaUsuarios.length ; i++) {
            String[] usuarioString = listaUsuarios[i].split(":");
            if(usuarioString.length == 2) {
                mapUsuarios.put(usuarioString[0], new Usuario(usuarioString[0], usuarioString[1]));
            }
        }

        return mapUsuarios;
    }

    private void inserirUsuario(Usuario usuario){
        usuariosCadastrados.put(usuario.getNome(), usuario);

        Set<String> keys = usuariosCadastrados.keySet();
        StringBuilder builder = new StringBuilder();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Iterator<String> keysIterator = keys.iterator();

        while(keysIterator.hasNext()) {
            Usuario usuario1 = usuariosCadastrados.get(keysIterator.next());
            builder.append(usuario1.toString()+";");
        }

        editor.putString(SHARED_PREFERENCES_USUARIOS, builder.toString());
        editor.commit();
    }
}

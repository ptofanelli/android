package br.edu.ifspsaocarlos.sdm.listaprodutossdm.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import br.edu.ifspsaocarlos.sdm.listaprodutossdm.R;
import br.edu.ifspsaocarlos.sdm.listaprodutossdm.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private Map<String, Usuario> usuariosCadastrados;

    private final String SHARED_PREFERENCES_USUARIOS = "lista_usuarios";
    private final String NOME_USUARIO = "nome_usuario";
    private final String SENHA = "senha";

    private Button entrarButton;
    private EditText nomeUsuarioEditText;
    private EditText senhaEditText;
    private CheckBox primeiroAcessoCheckBox;

    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindViews();

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        usuariosCadastrados = carregarUsuarios();
    }

    private void bindViews(){
        entrarButton = (Button) findViewById(R.id.entrar_button);
        nomeUsuarioEditText = (EditText) findViewById(R.id.nome_usuario);
        senhaEditText = (EditText) findViewById(R.id.senha);
        primeiroAcessoCheckBox = (CheckBox) findViewById(R.id.primeiro_acesso_checkbox);

        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Novo usuario
                if(primeiroAcessoCheckBox.isChecked()){
                    if(usuariosCadastrados.containsKey(nomeUsuarioEditText.getText().toString())) {
                        Toast.makeText(LoginActivity.this, R.string.usuario_ja_configurado, Toast.LENGTH_LONG).show();
                    } else {
                        // salvar usuario no arquivo de shared preferences
                        Usuario usuario = new Usuario(nomeUsuarioEditText.getText().toString(), senhaEditText.getText().toString());
                        inserirUsuario(usuario);
                        abreTelaPrincipal();
                    }
                    // Usuario existente
                }else{
                    Usuario usuario = usuariosCadastrados.get(nomeUsuarioEditText.getText().toString());

                    if(usuario != null) {
                        if (usuario.getSenha().equals(senhaEditText.getText().toString())) {
                            Toast.makeText(LoginActivity.this, R.string.usuario_autenticado, Toast.LENGTH_SHORT).show();
                            abreTelaPrincipal();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.erro_autenticacao, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.nenhum_ususario_cadastrado, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void abreTelaPrincipal() {
        Intent intent = new Intent(this, ListaProdutosActivity.class);
        startActivity(intent);
        finish();
    }

    private Map<String,Usuario> carregarUsuarios(){
        Map<String,Usuario> mapUsuarios = new HashMap<String,Usuario>();
        String[] listaUsuarios = sharedPreferences.getString(SHARED_PREFERENCES_USUARIOS, "").split(";");

        for (int i = 0 ; i < listaUsuarios.length ; i++) {
            String[] usuarioString = listaUsuarios[i].split(":");
            if(usuarioString.length == 2) {
                if(i == 0) {
                    nomeUsuarioEditText.setText(usuarioString[0]);
                }
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

    public static SharedPreferences getSharedPreferences() {
        return  sharedPreferences;
    }
}

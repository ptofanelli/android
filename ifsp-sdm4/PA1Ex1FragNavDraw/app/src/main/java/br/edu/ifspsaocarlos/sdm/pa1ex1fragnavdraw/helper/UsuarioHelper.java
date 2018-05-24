package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.helper;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.Usuario;

/**
 * Created by ptofanelli on 02/23/2018.
 */

public class UsuarioHelper {
    private static final String SHARED_PREFERENCES_USUARIOS = "usuarios";
    private SharedPreferences preferences;

    private List<Usuario> usuarios;

    public UsuarioHelper(SharedPreferences preferences) {
        this.preferences = preferences;
        carregar();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void inserir(Usuario usuario) {
        usuarios.add(usuario);

        StringBuilder builder = new StringBuilder();
        SharedPreferences.Editor editor = preferences.edit();

        for(Usuario user : usuarios) {
            builder.append(user.toString()+";");
        }

        editor.putString(SHARED_PREFERENCES_USUARIOS, builder.toString());
        editor.commit();
    }

    public void atualizar() {
        StringBuilder builder = new StringBuilder();
        SharedPreferences.Editor editor = preferences.edit();

        for(Usuario site1 : usuarios) {
            builder.append(site1.toString()+";");
        }

        editor.putString(SHARED_PREFERENCES_USUARIOS, builder.toString());
        editor.commit();
    }

    private void carregar() {
        usuarios = new ArrayList<Usuario>();
        String[] usuariosStrings = preferences.getString(SHARED_PREFERENCES_USUARIOS, "").split(";");

        for (int i = 0 ; i < usuariosStrings.length ; i++) {
            String[] siteString = usuariosStrings[i].split("-");
            if(siteString.length == 3) {
                Usuario usuario = new Usuario(siteString[0], siteString[1], siteString[2]);
                usuarios.add(usuario);
            }
        }
    }
}

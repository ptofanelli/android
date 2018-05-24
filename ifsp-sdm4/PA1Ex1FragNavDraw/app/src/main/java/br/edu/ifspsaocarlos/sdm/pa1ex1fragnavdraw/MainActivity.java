package br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.adapter.DrawerMenuItemAdapter;
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.helper.UsuarioHelper;
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.DrawerMenuItem;
import br.edu.ifspsaocarlos.sdm.pa1ex1fragnavdraw.model.Usuario;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String TAG_FRAG_LISTA = "FRAG_LIST_USER";
    private final String TAG_FRAG_CAD = "FRAG_CAD_USER";


    private ListView opcoesListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private FragmentManager fragmentManager;
    private List<DrawerMenuItem> menuItems;
    private List<Usuario> usuarios;
    private SharedPreferences sharedPreferences;
    private UsuarioHelper usuarioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        usuarioHelper = new UsuarioHelper(sharedPreferences);

        ListaUsuariosFragment listaUsuariosFragment = new ListaUsuariosFragment();
        listaUsuariosFragment.setListItemClickListener(getListaUsuariosFragmentListener());
        listaUsuariosFragment.setUsuarios(usuarioHelper.getUsuarios());

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fm_tela_principal, listaUsuariosFragment, TAG_FRAG_LISTA)
                .commit();

        getSupportActionBar().setTitle("Todos Usuários");

        //Drawer menu items
        menuItems = new ArrayList<>();
        menuItems.add(new DrawerMenuItem("Novo Usuário", R.mipmap.ic_account_plus_black_24dp));
        menuItems.add(new DrawerMenuItem("Todos", R.mipmap.ic_account_multiple_black_24dp));

        opcoesListView = (ListView) findViewById(R.id.lv_opcoes_menu);
        //opcoesListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new String[]{"opcao 1", "opcao 2"}));
        opcoesListView.setAdapter(new DrawerMenuItemAdapter(this, menuItems));
        opcoesListView.setOnItemClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_gaveta_navegacao);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.menu_aberto, R.string.menu_fechado) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DrawerMenuItem item = menuItems.get(position);

        if(item.getText().equalsIgnoreCase("todos")) {
            ListaUsuariosFragment listaUsuariosFragment = new ListaUsuariosFragment();
            listaUsuariosFragment.setListItemClickListener(getListaUsuariosFragmentListener());
            listaUsuariosFragment.setUsuarios(usuarioHelper.getUsuarios());

            fragmentManager.beginTransaction()
                    .replace(R.id.fm_tela_principal, listaUsuariosFragment , TAG_FRAG_LISTA)
                    .commit();

            getSupportActionBar().setTitle("Todos Usuários");

            drawerLayout.closeDrawers();
        } else {
            CadastroUsuarioFragment novoUsuarioFragment = new CadastroUsuarioFragment();
            novoUsuarioFragment.setUsuarioHelper(usuarioHelper);

            fragmentManager.beginTransaction()
                    .replace(R.id.fm_tela_principal, novoUsuarioFragment , TAG_FRAG_CAD)
                    .commit();

            getSupportActionBar().setTitle("Novo Usuário");

            drawerLayout.closeDrawers();
        }

    }

    private List<Usuario> carregarUsuarios() {
        usuarios = new ArrayList<>();

        Usuario user1 = new Usuario();
        user1.setNomeCompleto("Pio Tofanelli");
        usuarios.add(user1);

        Usuario user2 = new Usuario();
        user2.setNomeCompleto("Nayra Camile");
        usuarios.add(user2);

        return usuarios;
    }

    private AdapterView.OnItemClickListener getListaUsuariosFragmentListener() {
        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Usuario usuario = usuarioHelper.getUsuarios().get(position);
                if(usuario != null) {
                    CadastroUsuarioFragment editarUsuarioFragment = new CadastroUsuarioFragment();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", usuario);
                    editarUsuarioFragment.setArguments(bundle);

                    editarUsuarioFragment.setUsuarioHelper(usuarioHelper);

                    fragmentManager.beginTransaction()
                            .replace(R.id.fm_tela_principal, editarUsuarioFragment , TAG_FRAG_CAD)
                            .commit();

                    getSupportActionBar().setTitle("Editar Usuário");
                }
            }
        };

        return listener;
    }
}

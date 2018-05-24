package br.edu.ifspsaocarlos.sdm.fmanager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Pio Tofanelli on 09-Feb-18.
 */

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private final String TAG_F1 = "FRAGMENTO_1";
    private final String TAG_F2 = "FRAGMENTO_2";
    private Fragment fragment1, fragment2;
    private Button mudaFragmentButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        fragment1 = new Fragmento1Fragment();
        fragment2 = new Fragmento2Fragment();

        setContentView(R.layout.activity_principal);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_principal, fragment1, TAG_F1)
                .commit();

        mudaFragmentButton = (Button) findViewById(R.id.bt_muda_fragmento);
        mudaFragmentButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bt_muda_fragmento:

                Fragment fragment = fragmentManager.findFragmentByTag(TAG_F1);

                if(fragment == null)
                {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_principal, fragment1, TAG_F1)
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fl_principal, fragment2, TAG_F2)
                            .commit();
                }
                break;
        }


    }
}

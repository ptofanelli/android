package br.edu.ifspsaocarlos.sdm.pa2trabalho.context;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.R;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.MensageiroApiHelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ptofanelli on 17-Apr-18.
 */

public class ApplicationContext extends Application {

    private static final String TAG = "CONTEXT";

    private MensageiroApiHelper apiHelper;

    public ApplicationContext() {


    }

    public MensageiroApiHelper getApiHelper() {

        if(apiHelper == null) {
            apiHelper = new MensageiroApiHelper(getString(R.string.url_base));
        }

        return apiHelper;
    }
}

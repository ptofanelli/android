package br.edu.ifspsaocarlos.sdm.pa2trabalho.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import br.edu.ifspsaocarlos.sdm.pa2trabalho.api.requests.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class RequestBuilder {

    private MensageiroApi api;

    protected RequestBuilder(MensageiroApi api) {
        this.api = api;
    }


    public Request build(Class requestType, Object... args) {
        try {
            Constructor cons = requestType.getConstructor(MensageiroApi.class, Object[].class);
            return (Request) cons.newInstance(api, args);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

}

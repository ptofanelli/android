package br.edu.ifspsaocarlos.sdm.pa2trabalho.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ptofanelli on 18-Apr-18.
 */

public class MensageiroApiHelper implements EndpointBuilder{

    private RequestBuilder requestBuilder;

    private MensageiroApi api;

    public MensageiroApiHelper(String baseURL) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseURL);
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        Retrofit retrofit = builder.build();
        api = retrofit.create(MensageiroApi.class);

        requestBuilder = new RequestBuilder(api);
    }

    public Endpoint getEndpoint(Class requestType, Object... args) {
        try {
            Constructor cons = requestType.getConstructor(MensageiroApi.class);
            return (Endpoint) cons.newInstance(api);

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

    public RequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    public MensageiroApi getApi() {
        return api;
    }
}

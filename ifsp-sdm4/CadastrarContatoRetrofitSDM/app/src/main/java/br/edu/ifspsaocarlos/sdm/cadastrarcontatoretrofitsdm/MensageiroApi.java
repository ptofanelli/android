package br.edu.ifspsaocarlos.sdm.cadastrarcontatoretrofitsdm;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ptofanelli on 03/15/2018.
 */

public interface MensageiroApi {

    @POST("contato")
    Call<ResponseBody> postContato(@Body RequestBody novoContato);

}

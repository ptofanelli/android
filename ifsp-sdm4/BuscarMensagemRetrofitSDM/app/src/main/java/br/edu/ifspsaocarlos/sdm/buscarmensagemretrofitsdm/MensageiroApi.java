package br.edu.ifspsaocarlos.sdm.buscarmensagemretrofitsdm;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ptofanelli on 03/15/2018.
 */

public interface MensageiroApi {
    @GET("mensagem/{mensagemId}")
    Call<ResponseBody> getMensagemByPathID(@Path("mensagemId") String mensagemId);

    @GET("mensagem")
    Call<ResponseBody> getMensagemByQueryId(@Query("id") String id);

    @GET("mensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<ListaMensagens> getMensagensByPath(
            @Path("ultimaMensagemId") String id,
            @Path("origemId") String origemId,
            @Path("destinoId") String destinoId);

    @GET("mensagens")
    Call<ListaMensagens> getMensagensByQuery(
            @Query("id") String id,
            @Query("origem") String origemId,
            @Query("destino") String destinoId);

    @GET("rawmensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<List<Mensagem>> getRawMensagensByPath(
            @Path("ultimaMensagemId") String id,
            @Path("origemId") String origemId,
            @Path("destinoId") String destinoId);
}

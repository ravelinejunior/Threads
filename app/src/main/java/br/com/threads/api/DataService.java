package br.com.threads.api;

import java.util.List;

import br.com.threads.models.Fotos;
import br.com.threads.models.Postagens;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataService {

   @GET("/photos")
   Call<List<Fotos>> recuperarFotos();

   @GET("/posts")
   Call<List<Postagens>> recuperarPostagens();

   @POST("/posts")
   Call<Postagens> salvarPostagens(@Body Postagens postagens);

   //simular dados atraves de formulario
    @FormUrlEncoded
    @POST("/posts")
    Call<Postagens> salvarPostagens(
            @Field("userId") Integer userId,
            @Field("title") String title,
            @Field("body") String body
    );

    //passar id 1
    //esse put atualiza tudo, cria uma nova postagem no caso mantendo a ID mas cria novos
    @PUT("/posts/{id}")
    Call<Postagens> atualizarPostagens(@Path("id") int id,
                                       //dados que se quer atualizar
                                       @Body Postagens postagens
                                       ); // passar uma id para poder atualizar

    //atualiza apenas os campos que eu definir
    @PATCH("/posts/{id}")
    Call<Postagens> atualizarPostagensPatch(@Path("id") int Integer,
                                    //dados de um objeto
                                    @Body Postagens postagens
    );

    //deletar dados
    @DELETE("/posts/{id}")
    Call<Void> deletarPostagens(@Path("id") Integer id);
}

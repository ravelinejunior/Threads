package br.com.threads.api;


import br.com.threads.models.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService{

    String BASE_URL = "https://viacep.com.br/ws/";
   //chamando o resto da URL base
   @GET("{cep}/json/")
   Call<CEP> recuperarCEP(@Path("cep")String cep); // configurando o call dinamicamente. Para passar esse valor a notação PATH
}

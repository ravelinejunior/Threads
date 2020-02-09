package br.com.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.threads.api.CEPService;
import br.com.threads.api.DataService;
import br.com.threads.models.CEP;
import br.com.threads.models.Fotos;
import br.com.threads.models.Postagens;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitActivity extends AppCompatActivity {
    private TextView textoResultadoRetrofit;
    private TextView textoResultadoAtualizarPutRetrofit;
    private Retrofit retrofit;
    private List<Fotos> fotosList = new ArrayList<>();
    private List<Postagens> postagensList = new ArrayList<>();
    private TextView textoResultadoFotosRetrofit;
    private TextView textoResultadoDeleteRetrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        textoResultadoRetrofit = findViewById(R.id.resultado_texto_id_retrofit);
        textoResultadoFotosRetrofit = findViewById(R.id.resultado_text_fotosRetrofit_id);
        textoResultadoAtualizarPutRetrofit = findViewById(R.id.resultado_texto_PutRetrofit_id);
        textoResultadoDeleteRetrofit = findViewById(R.id.texto_resultado_DeleteRetrofit_id);
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



    }

    public void botaoAcionarRetrofit(View v){
        try {
            //recuperarCepRetrofit();
            salvarPostagens();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void botaoResultadoAtualizar(View v){

        //Configurar a postagem
        Postagens postagens = new Postagens(20,null,"Atualizado");
        Postagens postagensPatch = new Postagens(20,"Atualizado com PATCH",null);
        DataService dataService = retrofit.create(DataService.class);
        Call<Postagens> call = dataService.atualizarPostagensPatch(
                2,
                    postagensPatch
                );

        call.enqueue(new Callback<Postagens>() {
            @Override
            public void onResponse(Call<Postagens> call, Response<Postagens> response) {
                if (response.isSuccessful()){
                    //retornando uma resposta
                    Postagens postagensResposta = response.body();
                    textoResultadoAtualizarPutRetrofit.setText(
                            "Requisição: "+response.code()+"\n"+
                                    "Codigo " +postagensResposta.getId()+"\n"+
                                    "Id usuario " +postagensResposta.getUserId()+"\n"+
                                    "Titulo "+postagensResposta.getTitle()+"\n"+
                                    "Corpo "+postagensResposta.getBody()

                    );
                }
            }

            @Override
            public void onFailure(Call<Postagens> call, Throwable t) {

            }
        });
    }

    public void botaoRemoverDadosRetrofit(View v){

        DataService dataService = retrofit.create(DataService.class);
        Call<Void> voidCall = dataService.deletarPostagens(2);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    textoResultadoDeleteRetrofit.setText("Status da requisição: "+response.code()
                    +"\n"+"Mensagem: "+response.toString()
                    );
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
    public void recuperarCepRetrofit(){
        try {


            //configuração basica
            retrofit = new Retrofit.Builder()
                    .baseUrl(CEPService.BASE_URL) //url base
                    .addConverterFactory(GsonConverterFactory.create()) // ja configura para as strings
                    .build();

        } catch (Exception e){
            e.printStackTrace();
        }
        //configurar retrofit
        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> cepCall = cepService.recuperarCEP("30590265");

        //criando uma tarefa assincrona com enqueue, chama a requisição
        cepCall.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful()){
                    CEP cep = response.body();
                    textoResultadoRetrofit.setText(cep.getLogradouro()+"\n"
                            +cep.getLocalidade()+"\n"
                    +cep.getUf()+"\n"
               +cep.getCep()

                            );
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                Toast.makeText(RetrofitActivity.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //utilizando a requisição POST
    private void salvarPostagens(){

        //Configura objeto postagem
        Postagens postagens = new Postagens(1234,"Titulo qualquer","Corpo da postagem");

        //recupera o serviço postagens e salva postagem
        DataService dataService = retrofit.create(DataService.class);
        Call<Postagens> postagensCall = dataService.salvarPostagens(100,"Meu deus que coisa louca","Maluquice");

        postagensCall.enqueue(new Callback<Postagens>() {
            @Override
            public void onResponse(Call<Postagens> call, Response<Postagens> response) {
                if (response.isSuccessful()){
                    //retornando uma resposta
                    Postagens postagensResposta = response.body();
                    textoResultadoRetrofit.setText(
                            "Requisição: "+response.code()+"\n"+
                            "Codigo " +postagensResposta.getId()+"\n"+
                            "Id usuario " +postagensResposta.getUserId()+"\n"+
                            "Titulo "+postagensResposta.getTitle()+"\n"+
                            "Corpo "+postagensResposta.getBody()

                    );
                }
            }

            @Override
            public void onFailure(Call<Postagens> call, Throwable t) {

            }
        });


    }

    public void recuperarListaRetrofit(View v){

        //recuperando dados de requisição do retrofit
        DataService dataService = retrofit.create(DataService.class);
        Call<List<Fotos>> fotosCall = dataService.recuperarFotos();

        fotosCall.enqueue(new Callback<List<Fotos>>() {
            @Override
            public void onResponse(Call<List<Fotos>> call, Response<List<Fotos>> response) {
                if (response.isSuccessful()){
                    //recuperando lista de fotos
                    fotosList = response.body();
                    for (int i = 0; i<fotosList.size();i++) {
                        Fotos fotos = fotosList.get(i);
                        Log.d("resultado","resultado "+
                                fotos.getId()+
                                fotos.getThumbailUrl()+
                                fotos.getTitle()+
                                fotos.getUrl()
                        );
                        textoResultadoFotosRetrofit.setText(
                                fotos.getId()+
                                fotos.getThumbailUrl()+
                                fotos.getTitle()+
                                fotos.getUrl());
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Fotos>> call, Throwable t) {

            }
        });

    }
}

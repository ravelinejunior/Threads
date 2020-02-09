package br.com.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button botaoIniciarThread;
    private int numero;
    private Handler handler = new Handler(); // envia mensagens para as threads
    private boolean finalizarTarefa = false;
    private TextView textoResultadoRequisicao;
    private EditText cepDigitado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botaoIniciarThread = findViewById(R.id.botao_iniciarThread_id);
        textoResultadoRequisicao = findViewById(R.id.resultadoDaRequisicao_id);
        botaoIniciarThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarThread();
            }
        });
        cepDigitado = findViewById(R.id.cep_digitado_id);


    }

    public void recuperarRequisicoes(View v){
        //é necessario usar uma AsyncTask para recuperar dados de API´s
        MyTaskAsyncApi myTaskAsyncApi = new MyTaskAsyncApi();
        String urlAPI = "https://blockchain.info/ticker";
        myTaskAsyncApi.execute(urlAPI);

    }

    public void recuperarRequisicoesAPICep(View v){
        //é necessario usar uma AsyncTask para recuperar dados de API´s
        MyTaskAsyncApi myTaskAsyncApi = new MyTaskAsyncApi();
        String cepDigitadoString =  cepDigitado.getText().toString();
        String urlAPICep = "https://viacep.com.br/ws/"+cepDigitadoString+"/json";
        myTaskAsyncApi.execute(urlAPICep);
    }

    class MyTaskAsyncApi extends AsyncTask<String,Void,String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringURL = strings[0]; // recuperar a string passada na primeira posição
            InputStream inputStreamConnection = null;
            InputStreamReader inputStreamReader = null; // le os dados em bytes e decodifica em caracteres
            StringBuffer buffer = null;

            //criar conexão http
            try {
                URL urAPI = new URL(stringURL);
               HttpURLConnection connection = (HttpURLConnection) urAPI.openConnection(); // retorna http url connection
               //recuperar dados
                inputStreamConnection = connection.getInputStream(); // dado vem encapsulado, nao da pra exibit por texto
                inputStreamReader = new InputStreamReader(inputStreamConnection);

                BufferedReader reader = new BufferedReader(inputStreamReader); // le os input stream reader linha por linha

                //lendo caracteres linha a linha
                String linha = "";
                buffer = new StringBuffer(); // inicializar o metodo buffer
                while((linha = reader.readLine()) != null){
                  buffer.append(linha+"\n\n");
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return buffer.toString() ;

        }

        @Override
        protected void onPostExecute(String resultadoInput) {
            super.onPostExecute(resultadoInput);

            //recuperando os dados
            String logradouro = null;
            String rua = null;
            String cep = null;
            String uf = null;
            String localidade = null;
            String bairro = null;

            //recuperando dados da API bitcoin
            String valorObj = "";
            String valorMoeda = "";
            String valorSimbolo = "";


            //criar um JSON object
            try {
                //JSONObject jsonObject = new JSONObject(resultadoInput);
               /* logradouro = jsonObject.getString("logradouro");
                rua = jsonObject.getString("rua");
                cep = jsonObject.getString("cep");
                bairro = jsonObject.getString("bairro");
                uf = jsonObject.getString("uf");
                localidade = jsonObject.getString("localidade");*/

                //recuperando valor de real de bitcoin API
                JSONObject jsonObjectAPI = new JSONObject(resultadoInput);
                valorObj = jsonObjectAPI.getString("BRL");

                JSONObject jsonObjectReal = new JSONObject(valorObj);
                valorMoeda = jsonObjectReal.getString("last"); // recupera o ultimo valor real
                valorSimbolo = jsonObjectReal.getString("symbol");


            } catch (JSONException e) {
                e.printStackTrace();
            }
          // textoResultadoRequisicao.setText("Real"+" | "+valorMoeda+" | "+" | "+valorSimbolo);
            textoResultadoRequisicao.setText(resultadoInput);

        }
    }

    public void asynctaskActivity(View v){
        Intent i = new Intent(getApplicationContext(),TarefaAsyncTaskActivity.class);
        startActivity(i);
    }

    void iniciarThread(){
            MyRunnable myRunnable = new MyRunnable();
            new Thread(myRunnable).start();
            finalizarTarefa = false;
    }

    public void finalizarThread(View v){
        finalizarTarefa = true;
    }

    class MyRunnable implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i<10;i++){
                Log.d("Thread ","count: "+i);
                numero = i;

                //finalizando a thread
                if (finalizarTarefa){
                    return; // para a execução do codigo
                }


                runOnUiThread(new Runnable() { // para realizar alterações de interface, deve-se utilizar a UIThread
                    @Override
                    public void run() {
                        botaoIniciarThread.setText("Contador: "+numero);
                        if (numero == 9){
                            numero = 0;
                            botaoIniciarThread.setText("Iniciar Thread");
                        }
                    }
                });



              /*  handler.post(new Runnable() { // usa a thread acessada no sistema, porem espera na fila para ser utilizado
                    @Override
                    public void run() {
                        botaoIniciarThread.setText("Contador: "+numero);
                        if (numero == 9){
                            numero = 0;
                            botaoIniciarThread.setText("Iniciar Thread");
                        }
                    }
                });
                */

                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }


    public void retrofitIntent(View v){
        Intent i = new Intent(getApplicationContext(),RetrofitActivity.class);
        startActivity(i);
    }


}



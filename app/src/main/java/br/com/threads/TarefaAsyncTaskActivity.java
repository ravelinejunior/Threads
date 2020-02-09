package br.com.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class TarefaAsyncTaskActivity extends AppCompatActivity {
    private ProgressBar progressBarAsyncTask;
    private TextView porcentagemAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa_async_task);
        progressBarAsyncTask = findViewById(R.id.progressBar_asincTask_id);
        porcentagemAsyncTask = findViewById(R.id.porcentagem_AsyncTask_id);
    }

    public void iniciarProgressAsync(View v){
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute(100); //pega o primeiro valor do asyncTask
    }

    /*
        para criar uma asyncTask
        Parametros:
        1 - parametro a ser passado para a classe
        2 - Tipo de valor que sera utilizado para o progresso da tarefa
        3 - Retorno apos tarefa finalizada


     */

    class MyAsyncTask extends AsyncTask<Integer,Integer,String>{

        //ordem de execução dos asyncTask

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBarAsyncTask.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) { // os tres pontos sao para passar varios parametros
            // metodo chamado enquanto a tarefa é executada

            int numero = integers[0]; // pega o primeiro valor que sera passado, por isso o indice 0
            for (int i = 0; i < numero; i+=10){
                publishProgress(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            return "Finalizado";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBarAsyncTask.setProgress(values[0]);
            porcentagemAsyncTask.setText("Taxa de progresso: "+values[0]+"%");
        }

        @Override
        protected void onPostExecute(String s) { // retorna o valor do return setado
            super.onPostExecute(s);
            Toast.makeText(TarefaAsyncTaskActivity.this, s, Toast.LENGTH_SHORT).show();
            progressBarAsyncTask.setProgress(0);
            progressBarAsyncTask.setVisibility(View.INVISIBLE);
            porcentagemAsyncTask.setText("Taxa de progresso: 0%");
        }




    }
    }


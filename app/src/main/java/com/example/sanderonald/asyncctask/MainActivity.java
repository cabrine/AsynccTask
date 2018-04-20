package com.example.sanderonald.asyncctask;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.app.ProgressDialog;

public class MainActivity extends AppCompatActivity {
    private Button startTask;
    ProgressDialog progressBar;
    int progressIncr=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startTask=(Button)findViewById(R.id.startTaskButton);

        startTask.setOnClickListener(startTaskListener);

    }
    private onClickListener startTaskListener=new onClickListener(){
        public void onClick(View v){
            //get application context
            Context context=getApplicationContext();
            //create process  dialog object
            progressBar=new ProgressDialog(v.getContext());
            //create asyn task object
            BackgroundTask  testBackgroundTask=new BackgroundTask();
            /* execute the background task */
            testBackgroundTask.execute(context);
            //Toast message
            CharSequence text=getString(R.string.mainThreadRunning);
            int duration= Toast.LENGTH_LONG;
            Toast toast=Toast.makeText(context,text,duration);
            toast.show();
        }
    };
    //define the asyn task class
    private class BackgroundTask extends AsyncTask<Context,Integer,String>{
        //pre execution method

        protected void onPreExecute(){
            //set progress bar characteristics and values
            CharSequence message=getString(R.string.progressMessage);
            progressBar.setCancelable(true);
            progressBar.setMessage(message);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            progressBar.show();

        }
        //background method
        protected String doInBackground(Context...params){
            //background task
            for (int i=0;i<=100;i+=progressIncr){
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                //publish the progress
                publishProgress(progressIncr);
                //check the cancellation of the background task
                if (isCancelled()) break;
            }
            //return completion text
            return getString(R.string.backgroundTaskCompleted);
        }
        //progress updates method
        protected void onProgressUpdate(Integer...values){
            //update progress bar
            progressBar.incrementProgressBy(progressIncr);
        }
        //post the execution method
        protected void onPostExecute(String result){
            //dismiss the progress bar
            progressBar.dismiss();
            progressBar.incrementProgressBy(progressIncr);
        }

        //post the execution method
        protected void onPostExecute() {
            onPostExecute();
        }

        //post the execution method
        protected void onPostExecute(String result) {
            //dismiss the progress bar
            progressBar.dismiss();

        }

}

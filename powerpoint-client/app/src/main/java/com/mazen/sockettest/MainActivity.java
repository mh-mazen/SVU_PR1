package com.mazen.sockettest;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMessage(new SendMessage.AsyncResponse() {

                    @Override
                    public void processFinish(String output) {
                        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
                    }
                }).execute("one");
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SendMessage(new SendMessage.AsyncResponse() {

                    @Override
                    public void processFinish(String output) {
                        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
                    }
                }).execute("two");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class SendMessage extends AsyncTask<String, Void, String> {

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public SendMessage(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        Client client = new Client();
        try {
            client.startConnection(" 192.168.1.22", 6666);
            String response = client.sendMessage(strings[0]);
            System.out.println(response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Cool";
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
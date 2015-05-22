package com.chamud.cheziandsima.intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class MainActivity extends ActionBarActivity {


    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.fileEditText);

        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(FileService.DONE);

        registerReceiver(downlandReceiver, intentFilter);

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

    public void DownloadFromServer(View view) {

        Intent i = new Intent(this, FileService.class);

        i.putExtra("url", "https://www.newthinktank.com/wordpress/lotr.txt");
        startService(i);
    }

    private BroadcastReceiver downlandReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StringBuilder sb;

            try {
                FileInputStream fileInputStream = openFileInput("myFile");

                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                sb = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line).append("\n");
                }

                et.setText(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    };
}

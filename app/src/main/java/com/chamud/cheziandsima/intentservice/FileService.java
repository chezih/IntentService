package com.chamud.cheziandsima.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CheziAndSima on 22/05/2015.
 */
public class FileService extends IntentService {

    public static final String DONE = "DONE";

    public FileService(String name) {
        super(name);
    }

    public FileService() {
        super(FileService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String url = intent.getStringExtra("url");

        String fileName = "myFile";

        try {
            FileOutputStream outStream = openFileOutput(fileName, Context.MODE_PRIVATE);

            URL fileURL = new URL(url);

            HttpURLConnection urlConnection = (HttpURLConnection) fileURL.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setDoOutput(true);

            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            byte[] buffer =new byte[1024];
            int bufferLength =0;

            while ((bufferLength = inputStream.read(buffer))>0)
            {
                outStream.write(buffer,0,bufferLength);
            }

            outStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent i = new Intent(DONE);
        this.sendBroadcast(i);
    }
}

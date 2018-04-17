package com.namu.sunrin.namu;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFileActivity extends AppCompatActivity {

    TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);

        tx = findViewById(R.id.fileResult);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyApp/myfile.txt");

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            buffer.append(file.getAbsolutePath().toString()+"\n");
            while ((line = reader.readLine())!=null){
                buffer.append(line);

            }

            tx.setText(buffer.toString());
            reader.close();



        } catch (FileNotFoundException e) {
            Log.e("Read","오류남");
        } catch (IOException e) {
            Log.e("Io","오류남");
        }


    }
}

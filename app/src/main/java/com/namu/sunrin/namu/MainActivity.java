package com.namu.sunrin.namu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText contentView;
    Button btn;
    boolean fileReadPermission, fileWritePermission;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fileReadPermission = true;

            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                fileWritePermission = true;

            }

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentView = findViewById(R.id.content);
        btn = findViewById(R.id.btn);

        //퍼미션 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            fileReadPermission = true;

        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            fileWritePermission = true;

        }
//없으면 퍼미션 요청
        if (!fileWritePermission || !fileWritePermission) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fileReadPermission && fileWritePermission) {
                    //외부 저장 공간 경로 획득
                    String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp";
                    File dir = new File(dirPath);
                    //풀더가 없으면 새로 만들어 준다.
                    if (!dir.exists()) {

                        dir.mkdir();

                    }

                    File file = new File(dir + "/myfile.txt");
                    if (!file.exists()) {

                        try {
                            file.createNewFile();
                            FileWriter writer = new FileWriter(file,true);
                            writer.write(contentView.getText().toString());
                            writer.flush();
                            writer.close();

                            Intent intent = new Intent(MainActivity.this,ReadFileActivity.class);
                            startActivity(intent);


                        } catch (IOException e) {
                            Log.e("mainactivity_ex","파일이 생성되지 않음");
                        }

                    }

                } else {

                    Toast.makeText(MainActivity.this, "권한을 주세요.", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }
}

package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity {
    Button start_intent;
    Button start_intent1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_intent= findViewById(R.id.btnLogin);
        start_intent1= findViewById(R.id.btnRegister);
        Intent intent=new Intent(MainActivity.this, com.example.application.LoginActivity.class);
        Intent intent1=new Intent(MainActivity.this, com.example.application.RegisterActivity.class);

        start_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        start_intent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent1);



            }
        });


    }
}
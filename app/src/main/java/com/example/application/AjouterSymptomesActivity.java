package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class AjouterSymptomesActivity extends AppCompatActivity {
    private  EditText inputs1,inputs2,inputs3,inputs4,inputs5;
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference reference=db.getReference().child("Ajouter un symptomes");
   Button Suivant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_symptomes);

        inputs1=findViewById(R.id.s1);
        inputs2=findViewById(R.id.s2);
        inputs3=findViewById(R.id.s3);
        inputs4=findViewById(R.id.s4);
        inputs5=findViewById(R.id.s5);
        Suivant=findViewById(R.id.Suivant);
        Suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCrededentials();
            }
        });

}

    private void checkCrededentials() {
        String Symptome1=inputs1.getText().toString();
        String Symptome2=inputs1.getText().toString();
        String Symptome3=inputs1.getText().toString();
        String Symptome4=inputs1.getText().toString();
        String Symptome5=inputs1.getText().toString();


        //HashMap
        HashMap<String,String> usermap=new HashMap<>();
        usermap.put("S1",Symptome1);
        usermap.put("S2",Symptome2);
        usermap.put("S3",Symptome3);
        usermap.put("S4",Symptome1);
        usermap.put("S5",Symptome1);



        reference.push().setValue(usermap);

        Toast.makeText(AjouterSymptomesActivity.this, "Données Enregistrer", Toast.LENGTH_SHORT).show();


            }

}
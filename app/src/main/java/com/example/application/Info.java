package com.example.application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.application.bean.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Info extends AppCompatActivity {

    private EditText inputpatient, inputdate, Sexe;
    private Button Entrer, Symptomes;
    private RadioButton radioOui, radioNon;
    private List<String> listSymptome = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        inputpatient = findViewById(R.id.inputpatient);
        inputdate = findViewById(R.id.inputdate);
        Sexe = findViewById(R.id.sexe);
        radioOui = findViewById(R.id.oui);
        radioNon = findViewById(R.id.non);
        Entrer = findViewById(R.id.Entrer);
        Symptomes = findViewById(R.id.symptomes_btn);

        Symptomes.setOnClickListener(view -> {
            Intent intent = new Intent(Info.this, SymptomesActivity.class);
            startActivityForResult(intent, 1);
        });
        Entrer.setOnClickListener(view -> {

            Patient patient = new Patient();
            patient.setSexe(Sexe.getText().toString());
            patient.setDateNaissance(inputdate.getText().toString());
            patient.setNom(inputpatient.getText().toString());
            patient.setSymptomeList(listSymptome);
            if(radioOui.isChecked()){
                patient.setAutisteFamille(true);
            }else{
                patient.setAutisteFamille(false);
            }

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patient");
            String id = ref.push().getKey();
            patient.setId(id);
            ref.child(id).setValue(patient);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
            ArrayList<String> mySymptome = data.getStringArrayListExtra("mySymptome");
            listSymptome.clear();
            listSymptome.addAll(mySymptome);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}








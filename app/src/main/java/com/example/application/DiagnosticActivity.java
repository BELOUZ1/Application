package com.example.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.application.bean.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DiagnosticActivity extends AppCompatActivity {

    private TextView nomTV;
    private TextView dateNaissanceTV;
    private TextView autisteFamilleTV;
    private TextView diagnosticTV;
    private ListView listSymptomesLV;
    private EditText diagnosticET;
    private Button ajouterBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostic);

        nomTV = findViewById(R.id.nom_dia_tv);
        dateNaissanceTV = findViewById(R.id.datenaissance_dia_tv);
        autisteFamilleTV = findViewById(R.id.autiste_dia_tv);
        diagnosticTV = findViewById(R.id.diagnostic_tv);
        diagnosticET = findViewById(R.id.diagnostic_et);
        ajouterBTN = findViewById(R.id.ajouter_dia_btn);
        listSymptomesLV = findViewById(R.id.symptomes_lv);

        Patient patient = (Patient) getIntent().getSerializableExtra("PATIENT");

        nomTV.setText(patient.getNom());
        dateNaissanceTV.setText(patient.getDateNaissance());
        if(patient.isAutisteFamille()){
            autisteFamilleTV.setText("Oui");
        }else{
            autisteFamilleTV.setText("Non");
        }

        if(patient.getDiagnostic() != null){
            diagnosticTV.setText(patient.getDiagnostic());
        }else{
            diagnosticTV.setText("Pas de diagnostic");
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patient.getSymptomeList());

        if(patient.getSymptomeList() != null){
            listSymptomesLV.setAdapter(itemsAdapter);
        }


        ajouterBTN.setOnClickListener(view -> {

            String diagnostic = diagnosticET.getText().toString().trim();

            if(!diagnostic.isEmpty()){
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Patient");
                patient.setDiagnostic(diagnostic);
                ref.child(patient.getId()).setValue(patient);
                diagnosticTV.setText(diagnostic);
            }

        });

    }
}
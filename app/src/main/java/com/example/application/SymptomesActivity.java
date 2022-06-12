package com.example.application;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.common.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SymptomesActivity extends AppCompatActivity {
    private Button btn_add;
    private Button valider;
    int i = 0;
    private SymptomeAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> mySymptome = new ArrayList<>();
    private List<String> arraysSymptome = new ArrayList<>();
    private List<String> listSymptome = Arrays
            .asList("Altéraction des interaction socials",
                    "Des et interets des activités restreints",
                    "Stéréotypés et répétitifs",
                    "Les Troubles des communications",
                    "Il ne regarde pas dans les yeux, ne rend pas le sourire, ne pointe pas du doigt pour montrer quelque chose",
                    "L’enfant ne parle pas ou bien le langage n’est pas utilisé pour communiquer avec autrui",
                    "Incapacité d'exécuter des mouvements volontaires adaptés");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptomes);

        mySymptome.clear();
        arraysSymptome.clear();
        arraysSymptome.addAll(listSymptome);
        adapter = new SymptomeAdapter(arraysSymptome, this);
        recyclerView = findViewById(R.id.symtomes_rv);
        valider = findViewById(R.id.valider_btn);
        btn_add = findViewById(R.id.btn_add);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        btn_add.setOnClickListener(view -> {
            showDialog();
        });

        valider.setOnClickListener(view -> {
            Intent intent = getIntent();
            intent.putStringArrayListExtra("mySymptome", mySymptome);
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    public void updateList(boolean checked, String value){

        if(checked){
            mySymptome.add(value);
            Toast.makeText(getApplicationContext(), "Symptome ajouté", Toast.LENGTH_LONG).show();
        }else{
            mySymptome.remove(value);
            Toast.makeText(getApplicationContext(), "Symptome supprimé", Toast.LENGTH_LONG).show();
        }

    }

    private void showDialog(){
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.symptomedialog, null);

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(
                SymptomesActivity.this);

        alertDialogBuilder.setView(promptsView);

        final EditText newSymptome = (EditText) promptsView.findViewById(R.id.symptome_et);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    if(!newSymptome.getText().toString().isEmpty()){
                        arraysSymptome.add(newSymptome.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

}






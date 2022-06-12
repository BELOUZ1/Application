package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.application.bean.Patient;
import com.example.application.bean.Utilisateur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Doctors extends AppCompatActivity {

    private List<Utilisateur> utilisateurList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DocteurAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);
        recyclerView = findViewById(R.id.docteurs_rv);
        Intent intent = getIntent();
        boolean doctors = intent.getBooleanExtra("CHAT",false);
        getDoctors(this, doctors);

    }

    public void goToChat(Utilisateur utilisateur){
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra("USER_ID", utilisateur.getId());
        intent.putExtra("USER_NAME", utilisateur.getName());
        startActivity(intent);
    }

    private void getDoctors(Doctors activity, boolean isDoctor){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Utilisateur");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                utilisateurList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Utilisateur utilisateur = postSnapshot.getValue(Utilisateur.class);
                    if(utilisateur.getType() != null && utilisateur.getType().equals(Utilisateur.DOCTEUR)){
                        utilisateurList.add(utilisateur);
                    }

                }

                adapter = new DocteurAdapter(utilisateurList, activity, isDoctor);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
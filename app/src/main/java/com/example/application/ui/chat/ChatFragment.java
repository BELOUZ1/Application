package com.example.application.ui.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.application.HomeDoctorActivity;
import com.example.application.R;
import com.example.application.UserAdapter;
import com.example.application.bean.Utilisateur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChatFragment extends Fragment {
    private List<Utilisateur> utilisateurList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.user_rv);
        getUsers((HomeDoctorActivity) getActivity());

        return view;
    }


    private void getUsers(HomeDoctorActivity activity){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Utilisateur");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                utilisateurList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Utilisateur utilisateur = postSnapshot.getValue(Utilisateur.class);
                    if(utilisateur.getType() != null && utilisateur.getType().equals(Utilisateur.UTILISATEUR)){
                        utilisateurList.add(utilisateur);
                    }

                }

                adapter = new UserAdapter(utilisateurList, activity);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
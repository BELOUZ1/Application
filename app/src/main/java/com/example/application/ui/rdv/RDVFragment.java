package com.example.application.ui.rdv;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.application.bean.Patient;
import com.example.application.DiagnosticActivity;
import com.example.application.UserFactory;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;
import com.example.application.RDVAdapter;
import com.example.application.bean.RendezVous;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class RDVFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseRDV;
    private List<RendezVous> rendezVousList = new ArrayList<>();
    private RDVAdapter adapter;
    private DatabaseReference ref;
    private Intent patientIntent;
    private List<Patient> patientList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_r_d_v, container, false);

        patientIntent = new Intent(getActivity(), DiagnosticActivity.class);

        recyclerView = view.findViewById(R.id.doc_rdv_rv);
        ref = FirebaseDatabase.getInstance().getReference("Patient");
        databaseRDV = FirebaseDatabase.getInstance().getReference("RendezVous");
        adapter = new RDVAdapter(rendezVousList, null, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getListPatient();
        getRendezVous();

        return view;
    }

    public void deleteRDV(RendezVous rendezVous) {
        String idUser = UserFactory.getUtilisateur().getId();
        rendezVousList.remove(rendezVous);
        databaseRDV.child(idUser).setValue(rendezVousList);
        Toast.makeText(getContext(), "Rendez vous supprimé", Toast.LENGTH_LONG).show();
    }


    private void getListPatient() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Patient patient = postSnapshot.getValue(Patient.class);
                    patientList.add(patient);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void goToMyDiagnostic(RendezVous rendezVous) {
        for(Patient p : patientList){
            if(p.getId().equals(rendezVous.getIdPatient())){
                patientIntent.putExtra("PATIENT", p);
                startActivity(patientIntent);
            }
        }
    }

    private void getRendezVous() {

        databaseRDV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rendezVousList.clear();



                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
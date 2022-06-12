package com.example.application;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.bean.RendezVous;
import com.example.application.bean.Utilisateur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;


public class CalendrierFragment extends Fragment {

    private Button dateBTN;
    private Button heureBTN;
    private Button validerRDV;
    private TextView dateTV;
    private RecyclerView recyclerView;
    private RDVAdapter adapter;
    private TextView heureTV;
    private DatabaseReference databaseRDV;
    private List<RendezVous> rendezVousList = new ArrayList<>();
    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;
    private int lastSelectedHour = -1;
    private int lastSelectedMinute = -1;
    private String heure, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);

        databaseRDV = FirebaseDatabase.getInstance().getReference("RendezVous");

        getRendezVous();

        dateBTN = view.findViewById(R.id.date_btn);
        heureBTN = view.findViewById(R.id.heure_btn);
        dateTV = view.findViewById(R.id.date_tv);
        heureTV = view.findViewById(R.id.heure_tv);
        validerRDV = view.findViewById(R.id.valider_rdv_btn);
        recyclerView = view.findViewById(R.id.rdv_rv);

        adapter = new RDVAdapter(rendezVousList, this, null);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Calendar c = Calendar.getInstance();
        lastSelectedYear = c.get(Calendar.YEAR);
        lastSelectedMonth = c.get(Calendar.MONTH);
        lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        lastSelectedHour = c.get(Calendar.HOUR_OF_DAY);
        lastSelectedMinute = c.get(Calendar.MINUTE);

        date = lastSelectedDayOfMonth + "-" + (lastSelectedMonth + 1) + "-" + lastSelectedYear;
        dateTV.setText(date);

        heure = lastSelectedHour + ":" + lastSelectedMinute;
        heureTV.setText(heure);

        dateBTN.setOnClickListener(view1 -> {
            selectDate();
        });

        heureBTN.setOnClickListener(view12 -> {
           selectTime();
        });

        validerRDV.setOnClickListener(view13 -> {

            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(date);
            rendezVous.setHeure(heure);
            Utilisateur utilisateur = UserFactory.getUtilisateur();
            rendezVousList.add(rendezVous);
            databaseRDV.child(utilisateur.getId()).setValue(rendezVousList);
            Toast.makeText(getContext(), "Rendez vous ajouté", Toast.LENGTH_LONG).show();
        });

        return view;
    }

    public void deleteRDV(RendezVous rendezVous){
        String idUser = UserFactory.getUtilisateur().getId();
        rendezVousList.remove(rendezVous);
        databaseRDV.child(idUser).setValue(rendezVousList);
        Toast.makeText(getContext(), "Rendez vous supprimé", Toast.LENGTH_LONG).show();
    }

    private void selectTime()  {
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            heure = hourOfDay + ":" + minute;
            heureTV.setText(heure);
            lastSelectedHour = hourOfDay;
            lastSelectedMinute = minute;
        };
        TimePickerDialog timePickerDialog =  new TimePickerDialog(getContext(),
                timeSetListener, lastSelectedHour, lastSelectedMinute, true);
        timePickerDialog.show();
    }

    private void selectDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
            dateTV.setText(date);

            lastSelectedYear = year;
            lastSelectedMonth = monthOfYear;
            lastSelectedDayOfMonth = dayOfMonth;
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
        datePickerDialog.show();
    }


    private void getRendezVous() {


        String id = UserFactory.getUtilisateur().getId();
        //databaseRDV.child(id).setValue(rendezVousList);
        databaseRDV.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rendezVousList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RendezVous rendezVous = postSnapshot.getValue(RendezVous.class);
                    rendezVousList.add(rendezVous);
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
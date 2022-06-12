package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.application.bean.RendezVous;
import com.example.application.bean.Utilisateur;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private DatabaseReference databaseRDV;
    private List<RendezVous> rendezVousList = new ArrayList<>();
    private boolean fromHere = false;
    private Intent mediaIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //this line hide satusbar
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        databaseRDV = FirebaseDatabase.getInstance().getReference("RendezVous");
        fromHere = true;
        mediaIntent = new Intent(this, MediaActivity.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel("CH1", "CH1", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(ch);
        }

        getRendezVous();



        navigationView=findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new HomeFragment()).commit();
        navigationView.setSelectedItemId(R.id.nav_home);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              Fragment fragment=null;
              switch (item.getItemId()){
                  case R.id.nav_home:
                      fragment= new HomeFragment();
                      getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                      break;

                  case R.id.nav_calendrier:
                      fragment= new CalendrierFragment();
                      getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                      break;

                  case R.id.nav_activité:
                      startActivity(mediaIntent);
                      break;

                  case R.id.nav_message:
                      fragment= new MessageFragment();
                      getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment).commit();
                      break;

              }


                return true;
            }
        });
    }


    private void mNotify(long diff){

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),"CH1")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Rappel RendezVous")
                .setContentText("Votre rendez vous est dans " + diff + " jours")
                .setAutoCancel(true)
                .setSound(defaultSoundUri);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        notificationManager.notify(0, notificationBuilder.build());
    }


    private void getRendezVous() {

        rendezVousList.clear();
        String id = UserFactory.getUtilisateur().getId();
        databaseRDV.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RendezVous rendezVous = postSnapshot.getValue(RendezVous.class);
                    rendezVousList.add(rendezVous);
                }

                if(fromHere){
                    runNotification();
                    fromHere = false;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void runNotification(){

        for(RendezVous rdv : rendezVousList){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

            Date firstDate = null;
            Date secondDate = null;
            try {
                firstDate = sdf.parse(sdf.format(new Date()));
                secondDate = sdf.parse(rdv.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }


            long diff = secondDate.getTime() - firstDate.getTime();

            TimeUnit time = TimeUnit.DAYS;
            long diffrence = time.convert(diff, TimeUnit.MILLISECONDS);

            if(diffrence <= 2 && diffrence > 0){
                mNotify(diffrence);
                break;
            }
        }

    }
}
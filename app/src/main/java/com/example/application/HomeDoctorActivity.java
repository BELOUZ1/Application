package com.example.application;

import android.content.Intent;
import android.os.Bundle;

import com.example.application.bean.Patient;
import com.example.application.bean.Utilisateur;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.application.databinding.ActivityHomeDoctorBinding;

public class HomeDoctorActivity extends AppCompatActivity {

    private ActivityHomeDoctorBinding binding;
    private Intent chatIntent;
    private Intent diagIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatIntent = new Intent(this, MessageActivity.class);
        diagIntent = new Intent(this, DiagnosticActivity.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_chat, R.id.navigation_patient)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home_doctor);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }


    public void goToChat(Utilisateur utilisateur){
        chatIntent.putExtra("USER_NAME", utilisateur.getName());
        chatIntent.putExtra("USER_ID", utilisateur.getId());
        startActivity(chatIntent);
    }

    public void goToDiagnostic(Patient patient){
        diagIntent.putExtra("PATIENT", patient);
        startActivity(diagIntent);
    }

}
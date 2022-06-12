package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.bean.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Setting extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
   private TextView parametre,modifier, supprimer;
   private SwitchCompat switchCompat;
   private DatabaseReference databaseUser;
   private ProgressDialog mLoadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ThemeUtils.onActivityCreateSetTheme(this);

        setContentView(R.layout.activity_setting);
        parametre=findViewById(R.id.parametre);
        modifier=findViewById(R.id.modifier);
        switchCompat = findViewById(R.id.mode_sombre);
        supprimer = findViewById(R.id.delete_account_tv);
        databaseUser = FirebaseDatabase.getInstance().getReference("Utilisateur");
        mLoadingBar=new ProgressDialog(this);
        mLoadingBar.setTitle("Modifier mot de passe");
        mLoadingBar.setMessage("Veuillez patienter...");
        mLoadingBar.setCanceledOnTouchOutside(false);


        switchCompat.setOnCheckedChangeListener(this);

        parametre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Setting.this,HomeFragment.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        modifier.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_LONG).show();
            showDialog();
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteAccount();
            }
        });


    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

        int id = compoundButton.getId();

        if(id == R.id.mode_sombre){

            if(checked){
                ThemeUtils.changeToTheme(this, 1);
            }else{
                ThemeUtils.changeToTheme(this, 0);
            }

        }


    }



    private void showDialogDeleteAccount(){

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(
                Setting.this);

        alertDialogBuilder.setTitle("Supprimer votre compte");
        alertDialogBuilder.setMessage("Vous etes sur le point de supprimer votre compte. Continuer ?");


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    String email = UserFactory.getUtilisateur().getEmail();
                    String motDePasse = UserFactory.getUtilisateur().getMotDePasse();
                    deleteuser(email, motDePasse);
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private void showDialog(){
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.alertdialog, null);

        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(
                Setting.this);

        alertDialogBuilder.setIcon(R.drawable.ic_baseline_vpn_key_24);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.new_password_et);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, id) -> {
                    Toast.makeText(getApplicationContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
                    changePassword(userInput.getText().toString());
                })
                .setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    private void changePassword(String newPass){

        mLoadingBar.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String email = UserFactory.getUtilisateur().getEmail();
        String motDePasse = UserFactory.getUtilisateur().getMotDePasse();

        AuthCredential credential = EmailAuthProvider
                .getCredential(email, motDePasse);

        user.reauthenticate(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        user.updatePassword(newPass).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                mLoadingBar.dismiss();
                                Utilisateur utilisateur = UserFactory.getUtilisateur();
                                utilisateur.setMotDePasse(newPass);
                                databaseUser.child(utilisateur.getId()).setValue(utilisateur);
                                Toast.makeText(getApplicationContext(), "Mot de passe modifié", Toast.LENGTH_LONG).show();

                            } else {
                                mLoadingBar.dismiss();
                                Toast.makeText(getApplicationContext(), "Error password not updated", Toast.LENGTH_LONG).show();
                            }

                        });
                    } else {
                        mLoadingBar.dismiss();
                        Toast.makeText(getApplicationContext(), "Error auth failed", Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void deleteuser(String email, String password) {
        mLoadingBar.setTitle("Supprimer votre compte");
        mLoadingBar.show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

        if (user != null) {
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> user.delete()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    DatabaseReference databaseReference = databaseUser.child(uid);
                                    databaseReference.removeValue();
                                    mLoadingBar.show();
                                    finishAffinity();
                                    startActivity(new Intent(Setting.this, LoginActivity.class));
                                    Toast.makeText(getApplicationContext(), "Deleted User Successfully,", Toast.LENGTH_LONG).show();
                                }else{
                                    mLoadingBar.show();
                                }
                            }));
        }
    }
}
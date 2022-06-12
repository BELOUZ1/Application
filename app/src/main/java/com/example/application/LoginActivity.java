package com.example.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavController;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.application.bean.Utilisateur;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    TextView createnewAccount;
    private EditText inputPassword,inputEmail;
boolean passwordvisible;
    Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    ImageView btnGoogle;

    FirebaseDatabase db=FirebaseDatabase.getInstance();
    //DatabaseReference reference=db.getReference().child("Users connexio");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        mAuth=FirebaseAuth.getInstance();
        mLoadingBar=new ProgressDialog(LoginActivity.this);
        btnLogin=findViewById(R.id.btnLogin);
        btnGoogle=findViewById(R.id.btnGoogle);


        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=inputPassword.getRight()-inputPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=inputPassword.getSelectionEnd();
                        if (passwordvisible){
                            //set drawable image her
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visibility,0);
                            //for hide password
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible=false;
                        }else{
                            //set drawable image her
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_visible_on,0);
                            //for show password
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible=true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCrededentials();
            }
        });
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LoginActivity.this,GoogleSignInActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               startActivity(intent);
            }
        });





    }
    private void checkCrededentials() {

        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if (email.isEmpty() || !email.contains("@"))
        {
            showError(inputEmail,"Email n'est pas valide!");
        }
        else if (password.isEmpty() || password.length()<7)
        {
            showError(inputPassword, "Le mot de pass doit comporter 7 caractères");
        }
        else {
            mLoadingBar.setTitle("Connexion");
            mLoadingBar.setMessage("Veuillez patienter pendant que vous informationd d'identification");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                       goToHome();
                    }else{
                        Toast.makeText(getApplicationContext(), "Email ou mot de passe incorrecte", Toast.LENGTH_LONG).show();
                        mLoadingBar.dismiss();
                    }

                }
            });
        }
    }

    private void showError(EditText input, String s) {

        input.setError(s);
        input.requestFocus();

    }


    private void goToHome(){
        String idUser = mAuth.getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Utilisateur").child(idUser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("id").getValue() != null){
                    String id = dataSnapshot.child("id").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String dateNaissance = dataSnapshot.child("dateNaissance").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String motDePasse = dataSnapshot.child("motDePasse").getValue().toString();
                    String sexe = dataSnapshot.child("sexe").getValue().toString();
                    String type = dataSnapshot.child("type").getValue().toString();
                    String specialite = dataSnapshot.child("specialite").getValue().toString();
                    String telephone = dataSnapshot.child("telephone").getValue().toString();

                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(id);
                    utilisateur.setName(name);
                    utilisateur.setDateNaissance(dateNaissance);
                    utilisateur.setEmail(email);
                    utilisateur.setMotDePasse(motDePasse);
                    utilisateur.setSexe(sexe);
                    utilisateur.setType(type);
                    utilisateur.setTelephone(telephone);
                    utilisateur.setSpecialite(specialite);

                    UserFactory.setUtilisateur(utilisateur);

                    mLoadingBar.dismiss();

                    Intent intent;

                    if(type.equals(Utilisateur.DOCTEUR)){
                        intent =new Intent(LoginActivity.this,HomeDoctorActivity.class);
                    }else{
                        intent =new Intent(LoginActivity.this,HomeActivity.class);
                    }

                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mLoadingBar.dismiss();
            }
        });

    }

}
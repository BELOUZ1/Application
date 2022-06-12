package com.example.application;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.application.bean.Utilisateur;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://application-d3a89-default-rtdb.firebaseio.com/");
    private EditText inputUsername, inputDateNaissance, inputSexe, inputPassword, inputEmail, inputConformPassword;
    private Button btnRegister;
    private RadioGroup typeRG;
    private RadioButton patientRB;
    private LinearLayout medecinLL;
    private LinearLayout patientLL;
    private EditText telephoneET;
    private EditText specialiteET;
    private EditText adresseET;
    private boolean passwordvisible;
    private String sType = Utilisateur.UTILISATEUR;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = db.getReference().child("Utilisateur");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        inputUsername = findViewById(R.id.inputUserName);
        inputDateNaissance = findViewById(R.id.inputDateNaissance);
        inputSexe = findViewById(R.id.inputSexe);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConformPassword = findViewById(R.id.inputConformPassword);
        inputSexe = findViewById(R.id.inputSexe);
        typeRG = findViewById(R.id.type_rg);
        medecinLL = findViewById(R.id.docteur_lt);
        patientLL = findViewById(R.id.patient_lt);
        telephoneET = findViewById(R.id.inputtelephone);
        specialiteET = findViewById(R.id.inputspecialite);
        patientRB = findViewById(R.id.patient);
        adresseET = findViewById(R.id.inputadresse);

        patientRB.setChecked(true);

        medecinLL.setVisibility(View.GONE);
        patientLL.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        mLoadingBar = new ProgressDialog(RegisterActivity.this);

        btnRegister = findViewById(R.id.btnRegister);

        inputPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= inputPassword.getRight() - inputPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = inputPassword.getSelectionEnd();
                        if (passwordvisible) {
                            //set drawable image her
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                            //for hide password
                            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            //set drawable image her
                            inputPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visible_on, 0);
                            //for show password
                            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        inputConformPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= inputConformPassword.getRight() - inputConformPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = inputPassword.getSelectionEnd();
                        if (passwordvisible) {
                            //set drawable image her
                            inputConformPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                            //for hide password
                            inputConformPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            //set drawable image her
                            inputConformPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_visible_on, 0);
                            //for show password
                            inputConformPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        inputPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        typeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();

                if (id == R.id.patient) {
                    patientLL.setVisibility(View.VISIBLE);
                    medecinLL.setVisibility(View.GONE);
                    sType = Utilisateur.UTILISATEUR;
                } else {
                    patientLL.setVisibility(View.GONE);
                    medecinLL.setVisibility(View.VISIBLE);
                    sType = Utilisateur.DOCTEUR;
                }

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> checkCrededentials());


    }


    private void checkCrededentials() {

        String username = inputUsername.getText().toString().trim();
        String dateNaissance = inputDateNaissance.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String conformPassword = inputConformPassword.getText().toString().trim();
        String sexe = inputSexe.getText().toString().trim();
        String telephone = telephoneET.getText().toString().trim();
        String specialite = specialiteET.getText().toString().trim();
        String adresse = adresseET.getText().toString().trim();

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setName(username);
        utilisateur.setDateNaissance(dateNaissance);
        utilisateur.setEmail(email);
        utilisateur.setSexe(sexe);
        utilisateur.setMotDePasse(password);
        utilisateur.setSpecialite(specialite);
        utilisateur.setTelephone(telephone);
        utilisateur.setType(sType);

        if (username.isEmpty() || username.length() < 7) {
            showError(inputUsername, "Entrer votre nom!");
            return;

        }

        if(sType.equals(Utilisateur.UTILISATEUR)){
            if (sexe.isEmpty() || sexe.length() < 5) {
                showError(inputSexe, "Entrer votr sexe");
                return;
            }
            if (dateNaissance.isEmpty() || dateNaissance.length() < 10) {
                showError(inputDateNaissance, "Entrer date de naissance!");
                return;
            }
        }else{
            if(telephone.isEmpty()){
                showError(telephoneET, "Entrer un numéro de téléphone !");
                return;
            }

            if(specialite.isEmpty()){
                showError(specialiteET, "Entrer une spécialité!");
                return;
            }

            if(adresse.isEmpty()){
                showError(adresseET, "Entrer une adresse!");
                return;
            }
        }

        if (email.isEmpty() || !email.contains("@")) {
            showError(inputEmail, "Email n'est pas valide!");
            return;
        }
        if (password.isEmpty() || password.length() < 7) {
            showError(inputPassword, "password must be 7 character");
            return;
        }
        if (conformPassword.isEmpty() || !conformPassword.equals(password)) {
            showError(inputConformPassword, "Password not match!");
            return;
        }
        mLoadingBar.setTitle("Registration");
        mLoadingBar.setMessage("Please wait ,while check your credentials ");
        mLoadingBar.setCanceledOnTouchOutside(false);
        mLoadingBar.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String idUser = mAuth.getUid();
                utilisateur.setId(idUser);
                reference.child(idUser).setValue(utilisateur);
                mLoadingBar.dismiss();
                finish();
            } else {
                mLoadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
            }

        });

    }


    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }

}


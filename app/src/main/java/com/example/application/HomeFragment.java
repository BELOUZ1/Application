package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.application.bean.Utilisateur;


public class HomeFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;
    Button information, docrors, chat, setting, exit;
    FragmentActivity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView bonjour = root.findViewById(R.id.bonjour_tv);
        bonjour.setText("Bonjour " + UserFactory.getUtilisateur().getName() + "!");
        return root;
    }

    public void onStart() {
        super.onStart();

        docrors = (Button) context.findViewById(R.id.inputDoctors);
        information = (Button) context.findViewById(R.id.inputinfo);
        setting = (Button) context.findViewById(R.id.inputSetting);
        chat = (Button) context.findViewById(R.id.inputChat);
        exit = context.findViewById(R.id.inputExit);

        docrors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Doctors.class);
                startActivity(intent);
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Info.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, Setting.class);
                startActivity(intent);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Doctors.class);
                intent.putExtra("CHAT", true);
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

    }


}
package com.example.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.bean.Utilisateur;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    private List<Utilisateur> utilisateurList;
    private HomeDoctorActivity activity;

    public UserAdapter(List<Utilisateur> utilisateurList, HomeDoctorActivity activity) {
        this.utilisateurList = utilisateurList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.useritem, parent, false);

        return new UserAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Utilisateur utilisateur = utilisateurList.get(position);
        holder.nom.setText(utilisateur.getName());
    }

    @Override
    public int getItemCount() {
        return utilisateurList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView nom;
        public ImageView message;

        public MyViewHolder(View view) {
            super(view);

            nom = view.findViewById(R.id.user_item_nom);
            message = view.findViewById(R.id.user_send_item);

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilisateur utilisateur = utilisateurList.get(getAdapterPosition());
                    activity.goToChat(utilisateur);
                }
            });
        }

    }
}

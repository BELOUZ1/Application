package com.example.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.bean.Utilisateur;

import java.util.List;

public class DocteurAdapter extends RecyclerView.Adapter<DocteurAdapter.MyViewHolder>{

    private List<Utilisateur> utilisateurList;
    private Doctors activity;
    private boolean isDoctor;

    public DocteurAdapter(List<Utilisateur> utilisateurList, Doctors activity, boolean isDoctor) {
        this.utilisateurList = utilisateurList;
        this.activity = activity;
        this.isDoctor = isDoctor;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemdocteur, parent, false);

        return new DocteurAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Utilisateur utilisateur = utilisateurList.get(position);
        holder.adresse.setText(utilisateur.getAdresse());
        holder.telephone.setText(utilisateur.getTelephone());
        holder.specialite.setText(utilisateur.getSpecialite());
        holder.nom.setText(utilisateur.getName());

        if(isDoctor){
            holder.message.setVisibility(View.VISIBLE);
        }else{
            holder.message.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return utilisateurList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public TextView nom;
        public TextView specialite;
        public TextView telephone;
        public TextView adresse;
        public ImageView message;

        public MyViewHolder(View view) {
            super(view);

            nom = view.findViewById(R.id.nom_item_docteur);
            specialite = view.findViewById(R.id.specialite_item_docteur);
            telephone = view.findViewById(R.id.telephone_item_docteur);
            adresse = view.findViewById(R.id.adresse_item_docteur);
            message = view.findViewById(R.id.message_item_docteur);

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

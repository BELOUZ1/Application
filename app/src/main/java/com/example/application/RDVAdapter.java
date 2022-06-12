package com.example.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.bean.RendezVous;
import com.example.application.ui.rdv.RDVFragment;

import java.util.List;

public class RDVAdapter extends RecyclerView.Adapter<RDVAdapter.MyViewHolder>{

    private List<RendezVous> rendezVousList;
    private CalendrierFragment calendrierFragment;
    private RDVFragment rdvFragment;

    public RDVAdapter(List<RendezVous> rendezVousList, CalendrierFragment calendrierFragment, RDVFragment rdvFragment) {
        this.rendezVousList = rendezVousList;
        this.calendrierFragment = calendrierFragment;
        this.rdvFragment = rdvFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemrdv, parent, false);

        return new RDVAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RendezVous rdv = rendezVousList.get(position);
        holder.heure.setText(rdv.getHeure());
        holder.date.setText(rdv.getDate());
        if(calendrierFragment != null){
            holder.patient.setVisibility(View.GONE);
        }else{
            holder.patient.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return rendezVousList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView heure;
        private ImageView delete;
        private ImageView patient;

        public MyViewHolder(View view) {
            super(view);

            date = view.findViewById(R.id.date_rdv_tv);
            heure = view.findViewById(R.id.heure_rdv_tv);
            delete = view.findViewById(R.id.delete_rdv_img);
            patient = view.findViewById(R.id.view_patient_img);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(calendrierFragment != null){
                        calendrierFragment.deleteRDV(rendezVousList.get(getAdapterPosition()));
                    }else {
                        rdvFragment.deleteRDV(rendezVousList.get(getAdapterPosition()));
                    }


                }
            });

            patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rdvFragment.goToMyDiagnostic(rendezVousList.get(getAdapterPosition()));
                }
            });

        }
    }
}

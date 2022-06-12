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

import com.example.application.bean.Patient;

import org.w3c.dom.Text;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.MyViewHolder>{

    private List<Patient> patientList;
    private HomeDoctorActivity activity;

    public PatientAdapter(List<Patient> patientList, HomeDoctorActivity activity) {
        this.patientList = patientList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itempatient, parent, false);

        return new PatientAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Patient patient = patientList.get(position);
        holder.nom.setText(patient.getNom());
        holder.dateNaissance.setText(patient.getDateNaissance());

        if(patient.isAutisteFamille()){
            holder.autisteFamille.setText("Oui");
        }else{
            holder.autisteFamille.setText("Non");
        }
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nom;
        private TextView dateNaissance;
        private TextView autisteFamille;
        private ImageView infoPatient;

        public MyViewHolder(View view) {
            super(view);

            nom = view.findViewById(R.id.nom_patient_item);
            dateNaissance = view.findViewById(R.id.datenaissance_patient_item);
            autisteFamille = view.findViewById(R.id.af_patient_item);
            infoPatient = view.findViewById(R.id.info_patient_img);

            infoPatient.setOnClickListener(view1 -> {
                activity.goToDiagnostic(patientList.get(getAdapterPosition()));
            });
        }
    }
}

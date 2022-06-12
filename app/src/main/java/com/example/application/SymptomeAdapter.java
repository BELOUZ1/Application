package com.example.application;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SymptomeAdapter extends RecyclerView.Adapter<SymptomeAdapter.MyViewHolder>{

    private List<String> symptomes;
    private SymptomesActivity activity;

    public SymptomeAdapter(List<String> symptomes, SymptomesActivity activity) {
        this.symptomes = symptomes;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.symptomeitem, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String symptome = symptomes.get(position);
        holder.isSymtome.setText(symptome);
    }

    @Override
    public int getItemCount() {
        return symptomes.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {

        public CheckBox isSymtome;

        public MyViewHolder(View view) {
            super(view);
            isSymtome = view.findViewById(R.id.symtome_cb);
            isSymtome.setOnCheckedChangeListener(this);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            activity.updateList(b, compoundButton.getText().toString());
        }
    }
}

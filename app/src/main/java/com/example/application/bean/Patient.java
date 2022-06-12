package com.example.application.bean;

import java.io.Serializable;
import java.util.List;

public class Patient implements Serializable {
    private String id;
    private String nom;
    private String dateNaissance;
    private String sexe;
    private boolean autisteFamille;
    private List<String> symptomeList;
    private String diagnostic;

    public Patient() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public boolean isAutisteFamille() {
        return autisteFamille;
    }

    public void setAutisteFamille(boolean autisteFamille) {
        this.autisteFamille = autisteFamille;
    }

    public List<String> getSymptomeList() {
        return symptomeList;
    }

    public void setSymptomeList(List<String> symptomeList) {
        this.symptomeList = symptomeList;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }
}

package com.example.application;

import com.example.application.bean.Utilisateur;

public class UserFactory {

    private static Utilisateur utilisateur;

    public static Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public static void setUtilisateur(Utilisateur utilisateur) {
        UserFactory.utilisateur = utilisateur;
    }
}

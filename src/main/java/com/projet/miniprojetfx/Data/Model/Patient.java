package com.projet.miniprojetfx.Data.Model;

import javafx.application.Application;
import javafx.stage.Stage;

public class Patient {
    private String cin;
    private String nom;
    private String prenom;
    private String tel;
    private String sexe;
    //Constructeur de 5 parametres :
    public Patient(String cin,String nom, String prenom, String tel,String sexe)
    {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.sexe = sexe;
    }
    //Constructeur de 3 parametres :
    public Patient( String nom, String prenom, String tel) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }


    }

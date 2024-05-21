package com.projet.miniprojetfx.Data.Model;
public class Medicament {
    private Integer ref;
    private String libelle;
    private Double prix;
    //Constructeur de 3 parametres :
    public Medicament(Integer ref,String libelle, Double prix)
    {
        this.ref = ref;
        this.libelle = libelle;
        this.prix = prix;
    }
    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }
}

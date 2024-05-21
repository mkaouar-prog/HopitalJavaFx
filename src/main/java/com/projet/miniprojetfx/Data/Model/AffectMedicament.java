package com.projet.miniprojetfx.Data.Model;

import java.util.Date;

public class AffectMedicament {
    private Integer refMed;
    private String cinPat;
    private String date;
    //Constructeur de 3 parametres :
    public AffectMedicament(Integer refMed,String cinPat, String date)
    {
        this.refMed=refMed;
        this.cinPat=cinPat;
        this.date=date;
    }

    public int getRefMed() {
        return refMed;
    }

    public void setRefMed(int refMed) {
        this.refMed = refMed;
    }

    public String getCinPat() {
        return cinPat;
    }

    public void setCinPat( String cinPat) {
        this.cinPat = cinPat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}

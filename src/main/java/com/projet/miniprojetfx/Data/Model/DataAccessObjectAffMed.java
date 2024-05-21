package com.projet.miniprojetfx.Data.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataAccessObjectAffMed {
    //Methode pour rendre tous les patients
    public ResultSet fetchAffMed() {
        String query = "SELECT * FROM patientmed";
        Connection connection = ConnectionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        }catch (SQLException e)
        {
            System.err.println("Exception :"+e.getMessage());
        }
        return null;
    }
    public List<String> getDataPatToCombo() {
        List<String> pats =new ArrayList<>();
        String query = "SELECT cin, nom, prenom FROM patient";
        Connection connection = ConnectionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String pat = rs.getString("cin")+" - "+rs.getString("nom")+" "+rs.getString("prenom");
                pats.add(pat);
            }

        }catch (SQLException e)
        {
            System.err.println("Exception :"+e.getMessage());
        }
        return pats;
    }
    public List<String> getDataMedToCombo() {
        List<String> meds =new ArrayList<>();
        String query = "SELECT ref, libelle FROM medicament";
        Connection connection = ConnectionManager.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String med = String.valueOf(rs.getInt("ref"))+" - "+rs.getString("libelle");
                meds.add(med);
            }
        }catch (SQLException e)
        {
            System.err.println("Exception :"+e.getMessage());
        }
        return meds;
    }
    private static final String insertPatMed= "INSERT INTO patientmed (refMed, cinPat) VALUES (?, ?)";
    //Insertion a la table patientMed
    public boolean insertIntoPatMed(int refMed , String cinPat)
    {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPatMed)) {

            preparedStatement.setInt(1, refMed);
            preparedStatement.setString(2, cinPat);
            int nbRow = preparedStatement.executeUpdate();
            return nbRow > 0; //True si nbRow > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si il ya une erreur l'ors de l'insertion
        }
    }
    //Methode pour effectuer une suppression pour un medicament a l'aide de PK Ref
    public  boolean deleteAffMed(int ref ,String cin , String date)  {
        String query = "DELETE FROM patientmed WHERE refMed = ? and cinPat = ? and date = ?";
        Connection connection = ConnectionManager.getConnection();
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ref);
            statement.setString(2, cin);
            statement.setString(3,date);
            statement.executeUpdate();
            return true;
        }catch (SQLException e)
        {
            System.err.println("Exception :"+e.getMessage());
        }
        return false;

    }




}

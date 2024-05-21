package com.projet.miniprojetfx.Data.Model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessObjectMed {
    private DataAccessObjectMed dataAccessObjectMed;

    //Req insertion un medicament:
    private static final String insertMed = "INSERT INTO medicament (ref, libelle, prix) VALUES (?, ?, ?)";
    public  boolean addMed(int ref, String libelle, Double prix) {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertMed)) {

            preparedStatement.setInt(1, ref);
            preparedStatement.setString(2, libelle);
            preparedStatement.setDouble(3, prix);

            int nbRow = preparedStatement.executeUpdate();
            return nbRow > 0; //True si nbRow > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si il ya une erreur l'ors de l'insertion
        }
    }
    //Methode pour rendre tous les patients
    public  ResultSet fetchMeds() {
        String query = "SELECT * FROM medicament";
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
    //Methode pour effectuer une suppression pour un medicament a l'aide de PK Ref
    public  boolean deleteMed(int ref)  {
        String query = "DELETE FROM medicament WHERE ref = ?";
        Connection connection = ConnectionManager.getConnection();
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ref);
            statement.executeUpdate();
            return true;
        }catch (SQLException e)
        {
            System.err.println("Exception :"+e.getMessage());
        }
        return false;

    }
    private void loadMed() throws SQLException {
        ResultSet resultSet = dataAccessObjectMed.fetchMeds();
    }
    public boolean isAlredyExist(int ref)
    {
        String query = "SELECT COUNT(*) AS count FROM medicament WHERE ref = ?";
        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ref);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // True si count > 0 : cin Exist
            }
        } catch (SQLException e) {
            System.err.println("Exception: " + e.getMessage());
        }
        return false;
    }
    //Req update un Med:
    private static final String updateMed = "UPDATE  medicament SET ref=? ,libelle=?,prix=? where ref =?";
    public  boolean updateMed(int ref, String libelle, Double prix ) {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateMed)) {

            preparedStatement.setInt(1, ref);
            preparedStatement.setString(2, libelle);
            preparedStatement.setDouble(3, prix);
            preparedStatement.setInt(4, ref);
            int nbRow = preparedStatement.executeUpdate();
            return nbRow > 0; //True si nbRow > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si il ya une erreur l'ors de l'insertion
        }
    }



}

package com.projet.miniprojetfx.Data.Model;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataAccessObject {



    //Req insertion un personnel:
    private static final String insertPersonnel = "INSERT INTO personnel (cin, nom, prenom, login, password, fonction) VALUES (?, ?, ?, ?, ?, ?)";
    //Req select un personnel :
    private static final String checkPersonnel ="SELECT * FROM personnel WHERE login=? and password=?";

    public  boolean addPersonnel(String cin, String nom, String prenom, String login, String password, String fonction) {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPersonnel)) {

            preparedStatement.setString(1, cin);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, login);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, fonction);

            int nbRow = preparedStatement.executeUpdate();
            return nbRow > 0; //True si nbRow > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si il ya une erreur l'ors de l'insertion
        }
    }
    public  boolean handelCheckPersonnel(String login,String pass)
    {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(checkPersonnel)) {
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,pass);
            rs =preparedStatement.executeQuery();

            if(rs.next())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bienvenue Personnel");
                alert.setHeaderText(null);
                alert.setContentText("Content de te voir Personnel "+rs.getString("nom")+" "+ rs.getString("prenom"));
                alert.showAndWait();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (SQLException e) {
        e.printStackTrace();
        return false;// Si il ya une erreur l'ors de l'insertion
        }
    }
    //Methode pour rendre tous les patients
    public static  ResultSet fetchPatients() {
        String query = "SELECT * FROM patient";
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
    //Methode pour effectuer une suppression pour un patient a l'aide de PK CIN
    public  boolean deletePatient(String cin)  {
        String query = "DELETE FROM patient WHERE cin = ?";
        Connection connection = ConnectionManager.getConnection();
        try
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cin);
            statement.executeUpdate();
            return true;
        }catch (SQLException e)
        {
            System.err.println("Exception :"+e.getMessage());
        }
        return false;

    }
    private void loadPatients() throws SQLException {
        ResultSet resultSet = fetchPatients();
    }
    public  boolean isAlredyExist(String cin)
    {
        String query = "SELECT COUNT(*) AS count FROM patient WHERE cin = ?";
        Connection connection = ConnectionManager.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cin);
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
    //Req insertion un Patient:
    private static final String insertPatient = "INSERT INTO patient (cin, nom, prenom, sexe, tel) VALUES (?, ?, ?, ?, ?)";
    public  boolean addPatient(String cin, String nom, String prenom, String sexe ,String tel) {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPatient)) {

            preparedStatement.setString(1, cin);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, sexe);
            preparedStatement.setString(5, tel);

            int nbRow = preparedStatement.executeUpdate();
            return nbRow > 0; //True si nbRow > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si il ya une erreur l'ors de l'insertion
        }
    }
    //Req update un Patient:
    private static final String updatePatient = "UPDATE  patient SET nom=? ,prenom=?,sexe=?,tel=? where cin =?";
    public  boolean updatePatient(String cin, String nom, String prenom, String sexe ,String tel) {
        ResultSet rs;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updatePatient)) {

            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, sexe);
            preparedStatement.setString(4, tel);
            preparedStatement.setString(5, cin);

            int nbRow = preparedStatement.executeUpdate();
            return nbRow > 0; //True si nbRow > 0
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Si il ya une erreur l'ors de l'insertion
        }
    }

}

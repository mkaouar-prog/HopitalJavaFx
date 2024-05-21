package com.projet.miniprojetfx.Data.Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionManager {
    private static final String URL = "jdbc:mysql://localhost:3306/projetfx";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // enregistrement de mysql driver
            Class.forName("com.mysql.jdbc.Driver");

            // creer une connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("connection reussi avec sucess !");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur ! verifier le driver !");
            //e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connection a la base !");
            //e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnectionToDB()
    {
        Connection connection =ConnectionManager.getConnection();
        try {
            if (connection != null) {
               connection.close();
                System.out.println("Connection fermer");
            }
        } catch (SQLException e) {
            System.err.println("Erreur de fermeture de la connection!");
            //e.printStackTrace();
        }

    }

    // Test de connection
//    public static void main(String[] args) {
//        Connection connection = getConnection();
//        try {
//            if (connection != null) {
//                connection.close();
//                System.out.println("Connection fermer");
//            }
//        } catch (SQLException e) {
//            System.err.println("Erreur de fermeture de la connection!");
//            //e.printStackTrace();
//        }
//    }

}

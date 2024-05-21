package com.projet.miniprojetfx.Controller;
import com.projet.miniprojetfx.Data.Model.DataAccessObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage; // Reference pour ce stage
    private DataAccessObject dataAccessObject;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    private TextField TxtCin;

    @FXML
    private TextField TxtNom;

    @FXML
    private TextField TxtPrenom;

    @FXML
    private TextField TxtLogin;

    @FXML
    private PasswordField TxtPassword;

    @FXML
    private TextField TxtFunc;

    @FXML
    private Button BtnSignUp;
    @FXML
    private TextField TxtUsername;
    @FXML
    private TextField TxtMdp;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataAccessObject =new DataAccessObject();

    }

    @FXML
    public void handleBtnSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/signup-view.fxml"));
            Parent root = loader.load();

            Stage signupStage = new Stage();
            signupStage.setTitle("Sign Up Gestion de l'hopital");
            signupStage.setScene(new Scene(root));
            signupStage.show();

            // Fermer ce stage
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle load exception
        }
    }

    @FXML
    private void handelBtnBackLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/login-view.fxml"));
            Parent root = loader.load();

            Stage loginStage = new Stage();
            loginStage.setTitle("Login Gestion de l'hopital");
            loginStage.setScene(new Scene(root));
            loginStage.show();

            // Ferme sign-up stage
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ClearFieldsSignUp()
    {
        TxtCin.setText("");
        TxtNom.setText("");
        TxtPrenom.setText("");
        TxtLogin.setText("");
        TxtPassword.setText("");
        TxtFunc.setText("");
    }
    @FXML
    private void handleBtnSignUpInsert() {
        String cin = TxtCin.getText();
        String nom = TxtNom.getText();
        String prenom = TxtPrenom.getText();
        String login = TxtLogin.getText();
        String password = TxtPassword.getText(); // Hash and salt this password for security
        String fonction = TxtFunc.getText();

        //Perpare alert a utiliser a chaque besoin
        Alert alert =new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur :");
        alert.setHeaderText(null);
        try{
            Integer.parseInt(cin);
        }catch (NumberFormatException e){
            alert.setContentText("Exception :"+e.getMessage());
            alert.show();
            System.err.println("NumberFormatException est levee !");
            return;
        }
        if(cin.length()!=8)
        {
            alert.setContentText("Erreur ! CIN doit etre 8 chiffres ");
            alert.show();
            return;
        }
        if(nom.equals(""))
        {
            alert.setContentText("Erreur ! veuillez verifiez le champs de Nom ");
            alert.show();
            return;
        }
        if(prenom.equals(""))
        {
            alert.setContentText("Erreur ! veuillez verifiez le champs de Prenom ");
            alert.show();
            return;
        }
        if(login.equals(""))
        {
            alert.setContentText("Erreur ! veuillez verifiez le champs de Login ");
            alert.show();
            return;
        }

        if(password.length()<8)
        {
            alert.setContentText("Erreur ! Password doit etre contient 8 caracteres ");
            alert.show();
            return;
        }

        if(fonction.equals(""))
        {
            alert.setContentText("Erreur ! veuillez verifiez le champs de Fonction ");
            alert.show();
            return;
        }
        Alert dialog =new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Confirmation :");
        dialog.setHeaderText("Verifier que votre donne sont correct :");
        dialog.setContentText("Cin : {"+cin+"} Nom : {"+nom+"} prenom : {"+prenom+"} login : {"+login+"} Password : {"+password+"} Function : {"+fonction+"}");
        dialog.showAndWait();
        if(dialog.getResult() == ButtonType.OK)
        {
        boolean success = dataAccessObject.addPersonnel(cin, nom, prenom, login, password, fonction);
        if (success) {
            System.out.println("Personnel ajouter avec succes !");
            Alert succes =new Alert(Alert.AlertType.CONFIRMATION);
            succes.setTitle("Insertion valider :");
            succes.setHeaderText("Insertion de nouveau personnel effectuer avec success !");
            succes.show();
            ClearFieldsSignUp();


        } else {
            System.err.println("Failed to sign up user!");
            // Alert lors de insertion impossible
            alert.setContentText("Erreur ! insertion d'un nouveau Personnel a ete refuse ");
            alert.show();
        }
        }
        if(dialog.getResult() == ButtonType.CANCEL) {
            Alert alert1 =new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Verification :");
            alert1.setHeaderText("Verifier vos donnee S.V.P !");
            alert1.showAndWait();
        }
    }
    //Rendre les zones de saisi vide
    public void ResetFieldsSignIn()
    {
        TxtUsername.setText("");
        TxtMdp.setText("");
    }
    public void OpenGestionPatienView(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionPatient-view.fxml"));
            Parent root = loader.load();

            Stage gestionPatientStage = new Stage();
            gestionPatientStage.setTitle("Gestion de Patient");
            gestionPatientStage.setScene(new Scene(root));
            gestionPatientStage.show();

            // Ferme sign-up stage
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handelBtnLogin(ActionEvent event){
        String username =TxtUsername.getText();
        String mdp =TxtMdp.getText();
        if(username.equals(""))
        {
            JOptionPane.showMessageDialog(null,"Attention ! nom de l'utilasateur est vide");
            return;
        }
        if (mdp.equals("")) {

            JOptionPane.showMessageDialog(null,"Attention ! MDP est vide");
            return;
        }
        boolean result =dataAccessObject.handelCheckPersonnel(username,mdp);
        if(result)
        {
            ResetFieldsSignIn();
            OpenGestionPatienView(event);

        }
        else
        {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur ! veuillez verifiez vos coordonnee ");
            alert.show();
            ResetFieldsSignIn();
        }
    }


}

package com.projet.miniprojetfx.Controller;
import com.projet.miniprojetfx.Data.Model.DataAccessObjectAffMed;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javax.swing.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMedController implements Initializable {
    private Stage stage;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private ComboBox<String> patientComboBox;
    @FXML
    private ComboBox<String> medicamentComboBox;
    private DataAccessObjectAffMed dataAccessObjectAffMed;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //JOptionPane.showMessageDialog(null,"test");
        dataAccessObjectAffMed =new DataAccessObjectAffMed();
        patientComboBox.getItems().add("---Selectionner un patient---");
        medicamentComboBox.getItems().add("---Selectionner un medicament---");
        patientComboBox.getItems().addAll(dataAccessObjectAffMed.getDataPatToCombo());
        medicamentComboBox.getItems().addAll(dataAccessObjectAffMed.getDataMedToCombo());
    }
    @FXML
    public void handleAffectMed(ActionEvent event) {
        String patientSelected = patientComboBox.getValue();
        String medSelected = medicamentComboBox.getValue();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Choix invalide");

        if (patientSelected == null || patientSelected.equals("---Sélectionner un patient---")) {
            alert.setHeaderText("Erreur ! Merci de choisir un patient depuis la liste");
            alert.showAndWait();
            return;
        }
        if (medSelected == null || medSelected.equals("---Selectionner un medicament---")) {
            alert.setHeaderText("Erreur ! Merci de choisir un medicament depuis la liste");
            alert.showAndWait();
            return;
        }

        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Selection");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText("Patient: " + patientSelected + "\nMedicament: " + medSelected);
        infoAlert.showAndWait();
        String cinPat=patientSelected.split(" - ")[0];
        String refMed=medSelected.split(" - ")[0];
        if (dataAccessObjectAffMed.insertIntoPatMed((Integer.valueOf(refMed)),cinPat))
        {
            Alert alert1 =new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Affectation reussite");
            alert1.setHeaderText("Affectation de med :"+medSelected.split(" - ")[1]+"\nAu Patient :"+patientSelected.split(" - ")[1]+"reussi !");
            alert1.showAndWait();
        }
    }
    @FXML
    private void handleBack(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionAffMed-view.fxml"));
            Parent root = loader.load();

            Stage signupStage = new Stage();
            signupStage.setTitle("Gestion de patient");
            signupStage.setScene(new Scene(root));
            signupStage.show();

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

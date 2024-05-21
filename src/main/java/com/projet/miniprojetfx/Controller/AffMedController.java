package com.projet.miniprojetfx.Controller;

import com.projet.miniprojetfx.Data.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AffMedController implements Initializable {
    private Stage stage;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnGestM;
    @FXML
    private Button btnGestP;
    @FXML
    private TableView<AffectMedicament> medicaTable;
    @FXML
    private TableColumn<AffectMedicament, Integer> refMedColumn;
    @FXML
    private TableColumn<AffectMedicament, String> cinPatColumn;
    @FXML
    private TableColumn<AffectMedicament, String> dateColumn;
    @FXML
    private TextField txtRefMed;
    @FXML
    private TextField txtCinPat;
    @FXML
    private TextField txtDate;
    @FXML
    private Button btnAdd;

    private ObservableList<AffectMedicament> affMedList;
    private DataAccessObjectAffMed dataAccessObjectAffMed;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleViewSignIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/login-view.fxml"));
            Parent root = loader.load();

            Stage signupStage = new Stage();
            signupStage.setTitle("Sign Up Gestion de l'hopital");
            signupStage.setScene(new Scene(root));
            signupStage.show();

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handelClose(ActionEvent event) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Logout");
        dialog.setHeaderText("Est ce que vous etes sur de deconnecter ?");
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.OK) {
            try {
                ConnectionManager.closeConnectionToDB();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deconnection en cours");
                alert.setHeaderText("Deconnection en cours a bientot");
                alert.showAndWait();
                handleViewSignIn(event);

            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }

    @FXML
    public void handleViewPatient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionPatient-view.fxml"));
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

    @FXML
    public void handleViewMed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionMed-view.fxml"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dataAccessObjectAffMed = new DataAccessObjectAffMed();
        refMedColumn.setCellValueFactory(new PropertyValueFactory<>("refMed"));
        cinPatColumn.setCellValueFactory(new PropertyValueFactory<>("cinPat"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        affMedList = FXCollections.observableArrayList();
        loadMed();
        ecouteurs();
    }

    private void loadMed() {
        ResultSet resultSet = dataAccessObjectAffMed.fetchAffMed();
        try {
            while (resultSet.next()) {
                int refMed = resultSet.getInt("refMed");
                String cinPat = resultSet.getString("cinPat");
                String date = resultSet.getString("date");
                AffectMedicament affectMedicament = new AffectMedicament(refMed, cinPat, date);
                affMedList.add(affectMedicament);
            }
            medicaTable.setItems(affMedList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void ecouteurs() {
        medicaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtRefMed.setText(String.valueOf(newSelection.getRefMed()));
                txtCinPat.setText(newSelection.getCinPat());
                txtDate.setText(String.valueOf(newSelection.getDate()));
            }
        });
    }

    @FXML
    private void handelCDAddView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/addAffectMed-view.fxml"));
            Parent root = loader.load();
            Stage signupStage = new Stage();
            signupStage.setTitle("Affecter un nouveau medicament");
            signupStage.setScene(new Scene(root));
            signupStage.show();

            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clearTableView()
    {
        affMedList.clear();
    }
    @FXML
    private void handelDeleteMed(ActionEvent event)
    {
        if(txtCinPat.getText().equals(""))
        {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression");
            alert.setHeaderText("Attention ! il faut choisir une affectation de med depuis la table");
            alert.showAndWait();
        }
        else
        {
            boolean result =dataAccessObjectAffMed.deleteAffMed(Integer.parseInt(txtRefMed.getText()),txtCinPat.getText(),txtDate.getText());
            if(result)
            {
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression reussi");
                alert.setHeaderText("Affectation de medicament a ete supprimer avec success");
                alert.showAndWait();
                clearTableView();
                loadMed();
            }
        }
    }
}

package com.projet.miniprojetfx.Controller;
import com.projet.miniprojetfx.Data.Model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.AccessibleRole;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import javafx.scene.Node;


public class MedicaController  implements Initializable {
    @FXML
    private TableView<Medicament> medicaTable;
    @FXML
    private Button closeButton;
    @FXML
    private TableColumn<Medicament, Integer> refColumn;
    @FXML
    private TableColumn<Medicament, String> libColumn;
    @FXML
    private TableColumn<Medicament, Double> prixColumn;
    private ObservableList<Medicament> medList;
    @FXML
    private TextField txtRef;
    @FXML
    private TextField txtLib;
    @FXML
    private TextField txtPrix;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnClose;
    @FXML
    private Menu mnGestP;
    @FXML
    private Menu mnGestM;
    @FXML
    private Menu mnAfM;
    //Attribut pour consomer  les method
    private DataAccessObjectMed dataAccessObjectMed;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dataAccessObjectMed = new DataAccessObjectMed();
        refColumn.setCellValueFactory(new PropertyValueFactory<>("ref"));
        libColumn.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));

        //DataAccessObjectMed dataAccessObjectMed =new DataAccessObjectMed();
        // Instance observableList
        medList = FXCollections.observableArrayList();
        //Chargement les patient dans le table view
        loadMed();
        //Listener pour affichage de donner selectionner depuis le tableau dans la formulaire
        ecouteurs();


    }
    //Methode pour recuperer tous les patient dans la table depuis la base de donnees
    private void loadMed() {
        ResultSet resultSet = dataAccessObjectMed.fetchMeds();
        try {
            while (resultSet.next()) {
                //Requperer les donnee
                int ref = resultSet.getInt("ref");
                String libelle = resultSet.getString("libelle");
                Double prix = resultSet.getDouble("prix");
                Medicament medicament = new Medicament(ref,libelle,prix);
                //Ajout de l'instance medicament a la list
                medList.add(medicament);
            }

            // Ajout les items au table
            medicaTable.setItems(medList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void ecouteurs() {
        //Listener pour le tableau
        medicaTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //Affecte les donnee dans les champs de formulaire
                txtRef.setText(String.valueOf(newSelection.getRef()));
                txtLib.setText(newSelection.getLibelle());
                txtPrix.setText(String.valueOf(newSelection.getPrix()));

            }
        });
    }
   //methode pour rendre les champs editable et efface les champs
    private void enableFormFieldAndClearField() {
        btnEdit.setVisible(false);
        txtRef.setEditable(true);
        txtLib.setEditable(true);
        txtPrix.setEditable(true);
        txtRef.setText("");
        txtLib.setText("");
        txtPrix.setText("");
    }
    public void clearTableView()
    {
        medList.clear();
    }
    private void handelChangeAfIns()
    {
        btnEdit.setVisible(true);
        txtRef.setEditable(false);
        txtLib.setEditable(false);
        txtPrix.setEditable(false);
        txtRef.setText("");
        txtLib.setText("");
        txtPrix.setText("");
    }

    @FXML
    private void handelAddMed(ActionEvent event)
    {
        if(btnEdit.isVisible()) {
            enableFormFieldAndClearField();
            return;
        }
        String ref =txtRef.getText();
        String libelle =txtLib.getText();
        String prix=txtPrix.getText();
        try
        {
            Integer.parseInt(ref);
        }catch (NumberFormatException e)
        {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Ref medicament");
            alert.setContentText("Exception :"+e.getMessage());
            alert.show();
        }

        System.err.println("NumberFormatException est levee !");

        if(dataAccessObjectMed.isAlredyExist(Integer.parseInt(ref)))
        {
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur ref");
            alert.setHeaderText("ref deja exist");
            alert.setContentText("Il est impossible d'inserer ce medicament");
            alert.showAndWait();
            return;
        }
        else
        {

            Alert alert =new Alert(Alert.AlertType.WARNING);

            if(libelle.equals(""))
            {
                alert.setTitle("Erreur libelle");
                alert.setHeaderText("libelle est vide");
                alert.showAndWait();
                return;
            }
            try
            {
                Double.parseDouble(prix);
            }catch (NumberFormatException e)
            {
                alert.setHeaderText("Prix medicament");
                alert.setContentText("Exception :"+e.getMessage());
                alert.show();
            }
        }
        if(dataAccessObjectMed.addMed(Integer.parseInt(ref),libelle,Double.parseDouble(prix)))
        {
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Insertion un nouveau patient");
            alert.setHeaderText("Insertion reussi avec succes ");
            alert.showAndWait();
            handelChangeAfIns();
            clearTableView();
            loadMed();

        }

    }
    public void handleViewSignIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/login-view.fxml"));
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
    private void handelClose(ActionEvent event) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Logout");
        dialog.setHeaderText("Est ce que vous etes sur de deconnecter ?");
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.OK) {
            try {
                ConnectionManager.closeConnectionToDB();
                Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deconnection en cours");
                alert.setHeaderText("Deconnection en cours a bientot");
                alert.showAndWait();
                handleViewSignIn(event);

            } catch (Exception e) {
                System.err.print(e.getMessage());
            }
        }
    }
    private void clearFields()
    {
        txtRef.setText("");
        txtPrix.setText("");
        txtPrix.setText("");
    }
    @FXML
    private void handelDeleteMed(ActionEvent event)
    {
        if(txtRef.getText().equals(""))
        {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression");
            alert.setHeaderText("Attention ! il faut choisir un Med depuis la table");
            alert.showAndWait();
        }
        else
        {
            boolean result =dataAccessObjectMed.deleteMed(Integer.parseInt(txtRef.getText()));
            if(result)
            {
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression reussi");
                alert.setHeaderText("Medicament {"+txtRef.getText()+"} a ete supprimer avec success");
                alert.showAndWait();
                clearTableView();
                clearFields();
                loadMed();
            }
        }
    }
    @FXML
    private void handelEditMed(ActionEvent event)
    {
        if(txtRef.getText().equals(""))
        {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur update : Merci de choisir un Med depuis la table");
            alert.showAndWait();
            return;

        }
        if((btnAdd.isVisible()))
        {
            btnAdd.setVisible(false);
            txtPrix.setEditable(true);
            txtLib.setEditable(true);
            return ;
        }
        String ref =txtRef.getText();
        String libelle =txtLib.getText();
        String prix=txtPrix.getText();
        Alert alert =new Alert(Alert.AlertType.WARNING);

        if(libelle.equals(""))
        {
            alert.setTitle("Erreur libelle");
            alert.setHeaderText("Erreur update : libelle est vide");
            alert.showAndWait();
            return;
        }
        if(prix.equals(""))
        {
            alert.setTitle("Erreur prenom");
            alert.setHeaderText("Erreur update : prix est vide !");
            alert.showAndWait();
            return;
        }


        Alert alert1 =new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Update Patient");
        alert1.setHeaderText("Est ce que vous etes sur d'appliquer cette modification");
        alert1.showAndWait();
        if(alert1.getResult() == ButtonType.OK) {
            if(dataAccessObjectMed.updateMed(Integer.parseInt(ref),libelle,Double.parseDouble(prix))) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("update patient");
                alert2.setHeaderText("Update medicament de ref{" + ref+ "}a ete effectuer avec succes");
                alert2.showAndWait();
                btnAdd.setVisible(true);
                txtPrix.setEditable(false);
                txtLib.setEditable(false);
                clearTableView();
                loadMed();
            }
        }
    }
    @FXML
    private void handelGestPView(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionPatient-view.fxml"));
            Parent root = loader.load();

            Stage patStage = new Stage();
            patStage.setTitle("Gestion de Patient");
            patStage.setScene(new Scene(root));
            patStage.show();
            // Fermer ce stage
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle load exception
        }
    }
    @FXML
    private void handelAffMedView(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionAffMed-view.fxml"));
            Parent root = loader.load();
            Stage patStage = new Stage();
            patStage.setTitle("Gestion Affect Med");
            patStage.setScene(new Scene(root));
            patStage.show();
            // Fermer ce stage
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle load exception
        }
    }
    @FXML
    private void handelPrintData(ActionEvent event)
    {
        if (txtRef.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Impression");
            alert.setHeaderText("Impression impossible : veuillez choisir un medicament");
            alert.showAndWait();
            return;
        }

        // Creation Vbox pour contenir les données
        VBox printableContent = new VBox();
        printableContent.getChildren().addAll(
                new Label("Information sur le medicament : "),
                new Label("Reference medicament : "+ txtRef.getText()),
                new Label("Libelle medicament : "+ txtLib.getText()),
                new Label("Prix de medicament : "+ txtPrix.getText())
        );

        // Create PrinterJob and print content
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(txtRef.getScene().getWindow())) {
            boolean success = printerJob.printPage(printableContent);
            if (success) {
                printerJob.endJob();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Impression");
            alert.setHeaderText("Erreur lors de l'impression");
            alert.setContentText("Impossible de créer le travail d'impression.");
            alert.showAndWait();
        }
    }

}

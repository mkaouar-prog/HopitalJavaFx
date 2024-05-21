package com.projet.miniprojetfx.Controller;
import com.projet.miniprojetfx.Data.Model.ConnectionManager;
import com.projet.miniprojetfx.Data.Model.DataAccessObject;
import com.projet.miniprojetfx.Data.Model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;

import javax.swing.*;

public class PatientController implements Initializable {
    private Stage stage; // Reference pour ce stage
    //--------IMAGES--------
    @FXML
    private ImageView imgP;
    @FXML
    private ImageView imgEdit;
    @FXML
    private ImageView imgAdd;
    @FXML
    private ImageView imgDel;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<Patient> personTable;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> telColumn;
    private ObservableList<Patient> patientList;
    @FXML
    private TextField txtCin;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPren;
    @FXML
    private TextField txtTel;
    @FXML
    private RadioButton rdMas;
    @FXML
    private RadioButton rdFem;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnDel;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnEdit;
    @FXML
    private Label lblNbHF;
    @FXML
    private Label lblTotP;
    @FXML
    private Button btnGestP;
    @FXML
    private Button btnGestM;
    @FXML
    private Button btnAffM;
    @FXML
    private ImageView imgPerson;
    private DataAccessObject dataAccessObject;




    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rdMas.setDisable(true);
        rdFem.setDisable(true);
        dataAccessObject =new DataAccessObject();
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));

        // Instance observableList
        patientList = FXCollections.observableArrayList();
        //Chargement les patient dans le table view
        loadPatients();
        //Listener pour affichage de donner selectionner depuis le tableau dans la formulaire
        ecouteurs();


    }
    private void ecouteurs() {
        //Listener pour le tableau
        personTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                //Affecte les donnee dans les champs de formulaire
                txtCin.setText(newSelection.getCin());
                txtNom.setText(newSelection.getNom());
                txtPren.setText(newSelection.getPrenom());
                txtTel.setText(newSelection.getTel());
                if (newSelection.getSexe().equalsIgnoreCase("H")) {
                    rdMas.setSelected(true);
                    rdFem.setSelected(false);
                }
                else
                {
                    rdMas.setSelected(false);
                    rdFem.setSelected(true);
                }
            }
        });
    }
    //Deux compteurs :
    private int nbHomme;
    private int nbFemme;
    //Somme des patients :
    private int somme;

    //Methode pour recuperer tous les patient dans la table depuis la base de donnees
    private void loadPatients() {
        ResultSet resultSet = dataAccessObject.fetchPatients();
        try {
            nbHomme =0;
            nbFemme =0;
            while (resultSet.next()) {
                //Requperer les donnee
                String cin = resultSet.getString("cin");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String tel = resultSet.getString("tel");
                String sexe =resultSet.getString("sexe");
                if(sexe.equals("H"))
                {
                    nbHomme++;
                }
                if (sexe.equals("F"))
                {
                    nbFemme++;
                }
                Patient patient = new Patient(cin,nom, prenom, tel,sexe);
                //Ajout de l'instance patient a la list
                patientList.add(patient);
            }
             somme =nbHomme+nbFemme;
            lblTotP.setText("Nombre patient(s):"+somme);

            lblNbHF.setText("Nb patient(s) homme :"+nbHomme+"\t Nb patient(s) femme :"+nbFemme+"\n");
            // Ajout les items au table
            personTable.setItems(patientList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //methode pour rendre les champs editable et efface les champs
    private void enableFormFieldAndClearField() {
        txtCin.setEditable(true);
        txtCin.setText("");
        txtNom.setText("");
        txtPren.setText("");
        txtTel.setText("");
        rdMas.setSelected(false);
        rdFem.setSelected(false);
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

    public void clearTableView()
    {

        patientList.clear();
    }
    private void clearFields()
    {
        txtCin.setText("");
        txtNom.setText("");
        txtPren.setText("");
        txtTel.setText("");
        rdMas.setSelected(false);
        rdFem.setSelected(false);
    }
    @FXML
    private void handelDeletePerson(ActionEvent event)
    {
        if(txtCin.getText().equals(""))
        {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression");
            alert.setHeaderText("Attention ! il faut choisir un patient depuis la table");
            alert.showAndWait();
        }
        else
        {
            boolean result =dataAccessObject.deletePatient(txtCin.getText());
            if(result)
            {
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression reussi");
                alert.setHeaderText("Patient de cin {"+txtCin.getText()+"} a ete supprimer avec success");
                alert.showAndWait();
                clearTableView();
                clearFields();
                loadPatients();
            }
        }
    }
    private void handelchange()
    {
        enableFormFieldAndClearField();
        btnEdit.setVisible(false);
        btnPrint.setVisible(false);
        txtNom.setEditable(true);
        txtPren.setEditable(true);
        txtTel.setEditable(true);
        rdMas.setDisable(false);
        rdFem.setDisable(false);
    }

    private void handelChangeAfIns()
    {
        txtCin.setEditable(false);
        btnEdit.setVisible(true);
        btnPrint.setVisible(true);
        txtNom.setEditable(false);
        txtPren.setEditable(false);
        txtTel.setEditable(false);
        rdMas.setDisable(true);
        rdFem.setDisable(true);
    }
    @FXML
    private  void handelNewPerson(ActionEvent event)
    {
        if(btnEdit.isVisible() & btnEdit.isVisible())
        {
            handelchange();
            return;
        }
        if(!(btnEdit.isVisible() & btnEdit.isVisible()))
        {
            String cin =txtCin.getText();
            String nom =txtNom.getText();
            String prenom=txtPren.getText();
            String tel=txtTel.getText();
            String sexe="";
            try
            {
                Integer.parseInt(cin);
            }catch (NumberFormatException e)
            {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Cin");
                alert.setContentText("Exception :"+e.getMessage());
                alert.show();
            }

                System.err.println("NumberFormatException est levee !");
            if(cin.length()<8)
            {
                Alert alert =new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Cin");
                alert.setContentText("Au moins 8 nombre pour la CIN");
                alert.showAndWait();
                return;
            }

            if(dataAccessObject.isAlredyExist(cin))
            {
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur cin");
                alert.setHeaderText("Cin deja exist");
                alert.setContentText("Il est impossible d'inserer ce Patient");
                alert.showAndWait();
                return;
            }
            else
            {
                Alert alert =new Alert(Alert.AlertType.WARNING);

                if(nom.equals(""))
                {
                    alert.setTitle("Erreur nom");
                    alert.setHeaderText("nom est vide");
                    alert.showAndWait();
                    return;
                }
                if(prenom.equals(""))
                {
                    alert.setTitle("Erreur prenom");
                    alert.setHeaderText("prenom est vide !");
                    alert.showAndWait();
                    return;
                }
                if(tel.equals(""))
                {
                    alert.setTitle("Erreur telephone");
                    alert.setHeaderText("telephone est vide !");
                    alert.showAndWait();
                    return;
                }
                if(!(rdMas.isSelected() || rdFem.isSelected()))
                {
                    alert.setTitle("Erreur genre");
                    alert.setHeaderText("genre non selectionner !");
                    alert.showAndWait();
                    return;
                }

                // Determine the selected gender
                if (rdMas.isSelected()) {
                    sexe = "H";
                } else if (rdFem.isSelected()) {
                    sexe = "F";
                }
            }
            if(dataAccessObject.addPatient(cin,nom,prenom,sexe,tel))
            {
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Insertion un nouveau patient");
                alert.setHeaderText("Insertion reussi avec succes ");
                alert.showAndWait();
                handelChangeAfIns();
                clearTableView();
                loadPatients();

            }

        }

    }
    @FXML
    private void handelEditPatient(ActionEvent event)
    {
        if(txtCin.getText().equals(""))
        {
            Alert alert =new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur update : Merci de choisir un patient depuis la table");
            alert.showAndWait();
            return;

        }
        if((btnPrint.isVisible()))
        {
            btnPrint.setVisible(false);
            btnAdd.setVisible(false);
            txtNom.setEditable(true);
            txtPren.setEditable(true);
            txtTel.setEditable(true);
            rdMas.setDisable(false);
            rdFem.setDisable(false);
            return ;
        }

        String cin =txtCin.getText();
        String nom =txtNom.getText();
        String prenom=txtPren.getText();
        String tel=txtTel.getText();
        String sexe="";
        Alert alert =new Alert(Alert.AlertType.WARNING);

        if(nom.equals(""))
        {
            alert.setTitle("Erreur nom");
            alert.setHeaderText("Erreur update : nom est vide");
            alert.showAndWait();
            return;
        }
        if(prenom.equals(""))
        {
            alert.setTitle("Erreur prenom");
            alert.setHeaderText("Erreur update : prenom est vide !");
            alert.showAndWait();
            return;
        }
        if(tel.equals(""))
        {
            alert.setTitle("Erreur telephone");
            alert.setHeaderText("Erreur update : telephone est vide !");
            alert.showAndWait();
            return;
        }
        if(rdMas.isSelected())
        {
            sexe="H";
            rdFem.setSelected(false);
        }
        if(rdFem.isSelected())
        {
            sexe="F";
            rdMas.setSelected(false);
        }

        Alert alert1 =new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Update Patient");
        alert1.setHeaderText("Est ce que vous etes sur d'appliquer cette modification");
        alert1.showAndWait();
        if(alert1.getResult() == ButtonType.OK) {
            if(dataAccessObject.updatePatient(cin,nom,prenom,sexe,tel)) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("update patient");
                alert2.setHeaderText("Update patient de cin{" + cin + "}a ete effectuer avec succes");
                alert2.showAndWait();
                btnPrint.setVisible(true);
                btnAdd.setVisible(true);
                txtNom.setEditable(false);
                txtPren.setEditable(false);
                txtTel.setEditable(false);
                rdMas.setDisable(true);
                rdFem.setDisable(true);
                clearTableView();
                loadPatients();
            }
        }
    }
    @FXML
    private void printPersonData() {
        if (txtCin.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Impression");
            alert.setHeaderText("Impression impossible : veuillez choisir un patient");
            alert.showAndWait();
            return;
        }
        String sexe = rdMas.isSelected() ? "HOMME" : "FEMME";
        JOptionPane.showMessageDialog(null," Impression patient de CIN :"+txtCin.getText());
        // Create a VBox to hold the printable content
        VBox printableContent = new VBox();
        printableContent.getChildren().addAll(
                new Label("Information sur le patient : "),
                new Label("Cin : "+ txtCin.getText()),
                new Label("Nom : "+ txtNom.getText()),
                new Label("Prenom : "+ txtPren.getText()),
                new Label("Telephone : "+ txtTel.getText()),
                new Label("Sexe : "+ sexe)
        );

        // Create PrinterJob and print content
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(txtCin.getScene().getWindow())) {
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
    @FXML
    private void handelGestMnView(ActionEvent event)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projet/miniprojetfx/Views/gestionMed-view.fxml"));
            Parent root = loader.load();

            Stage medStage = new Stage();
            medStage.setTitle("Gestion de medicament");
            medStage.setScene(new Scene(root));
            medStage.show();
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

            Stage medStage = new Stage();
            medStage.setTitle("Gestion de medicament");
            medStage.setScene(new Scene(root));
            medStage.show();
            // Fermer ce stage
            ((Stage) ((Button) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace(); // Handle load exception
        }
    }
}

package application;



import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class listcompany {
    @FXML
    private TableView<Company> TableView;
    

    @FXML
    private TableColumn<Company, Integer> id;

    @FXML
    private TableColumn<Company, String> nom;

    @FXML
    private TableColumn<Company, Integer> id_hotel;

    @FXML
    private TableColumn<Company, String> code;

    @FXML
    private TableColumn<Company, String> pays;

    @FXML
    private TableColumn<Company, String> adresse;

    @FXML
    private TableColumn<Company, String> tel;

    @FXML
    private TableColumn<Company, String> dest;

    @FXML
    private TableColumn<Company, String> periode;

    @FXML
    private TableColumn<Company, Double> prix;

    @FXML
    private TableColumn<Company, String> img;

    @FXML
    private Label Labelinfos;

    @FXML
    private Button insertButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ImageView imageView;

  

    @FXML
    private void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        id_hotel.setCellValueFactory(new PropertyValueFactory<>("idHotel"));
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        pays.setCellValueFactory(new PropertyValueFactory<>("pays"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tel.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        dest.setCellValueFactory(new PropertyValueFactory<>("destination"));
        periode.setCellValueFactory(new PropertyValueFactory<>("periode"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        img.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        // Utilisez la cellule personnalisée pour la colonne d'image
        img.setCellFactory(column -> new ImageTableCell<>());

        // Chargez les données initiales
        afficheButton() ;
    }
    @FXML
    private void afficheButton() {
        ObservableList<Company> companyList = FXCollections.observableArrayList();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM compagny");

            while (resultSet.next()) {
                Company company = new Company(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getInt("id_hotel"),
                        resultSet.getString("codeInternational"),
                        resultSet.getString("pays"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("destination"),
                        resultSet.getString("periodeVole"),
                        resultSet.getDouble("prixVole"),
                        resultSet.getString("image")
                );
                companyList.add(company);
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableView.setItems(companyList);
    }
    @FXML
    void insertButton() {
    	 try {
             // Charger le fichier FXML
             FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutecompagnie.fxml"));
             Parent root = loader.load();
      
             
             // Créer une nouvelle scène
             Scene scene = new Scene(root);
             
             // Créer une nouvelle fenêtre
             Stage stage = new Stage();
             stage.setTitle("Add company");
             stage.setScene(scene);
             
             // Afficher la fenêtre
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
    
    

    private void updateCompany(Company company) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");

            String query = "UPDATE compagny SET nom = ?, id_hotel = ?, codeInternational = ?, pays = ?, adresse = ?, telephone = ?, destination = ?, periodeVole = ?, prixVole = ?, image = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            
            statement.setString(1, company.getNom());
            statement.setInt(2, company.getIdHotel());
            statement.setString(3, company.getCode());
            statement.setString(4, company.getPays());
            statement.setString(5, company.getAdresse());
            statement.setString(6, company.getTelephone());
            statement.setString(7, company.getDestination());
            statement.setString(8, company.getPeriode());
            statement.setDouble(9, company.getPrix());
            statement.setString(10, company.getImageUrl());
            statement.setInt(11, company.getId());
            
            int rowsUpdated = statement.executeUpdate();
            
            if (rowsUpdated > 0) {
                showAlert("Update Successful", "Company Updated", "The company information has been updated successfully.");
                // Afficher l'image mise à jour après la mise à jour réussie
                showUpdatedImage(company.getImageUrl());
            } else {
                showAlert("Update Failed", "Company Not Updated", "Failed to update company information.");
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Error Updating Company", "An error occurred while updating company information.");
        }
        afficheButton();
    }
    // Méthode pour afficher l'image mise à jour
    private void showUpdatedImage(String imageUrl) {
        // Ici, vous devez implémenter la logique pour afficher l'image mise à jour.
        // Cela dépend de la façon dont vous affichez normalement les images dans votre interface utilisateur.
        // Si vous utilisez JavaFX, vous pouvez mettre à jour l'ImageView appropriée avec la nouvelle image.
        // Par exemple :
        // Image updatedImage = new Image(imageUrl);
        // imageView.setImage(updatedImage);
        // Assurez-vous de gérer les exceptions lors du chargement de l'image.
    }

    @FXML
    private void updateButton() {
        // Récupérer l'entreprise sélectionnée dans le TableView
        Company selectedCompany = TableView.getSelectionModel().getSelectedItem();

        // Vérifier si une entreprise est sélectionnée
        if (selectedCompany != null) {
            // Afficher un dialogue de formulaire pour saisir les nouvelles valeurs de l'entreprise
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Update Company");
            dialog.setHeaderText("Enter new values for the company:");

            // Créer les champs de texte pour les attributs de l'entreprise (sauf pour l'URL de l'image)
            TextField nomField = new TextField(selectedCompany.getNom());
            TextField idHotelField = new TextField(String.valueOf(selectedCompany.getIdHotel()));
            TextField codeField = new TextField(selectedCompany.getCode());
            TextField paysField = new TextField(selectedCompany.getPays());
            TextField adresseField = new TextField(selectedCompany.getAdresse());
            TextField telephoneField = new TextField(selectedCompany.getTelephone());
            TextField destinationField = new TextField(selectedCompany.getDestination());
            TextField periodeField = new TextField(selectedCompany.getPeriode());
            TextField prixField = new TextField(String.valueOf(selectedCompany.getPrix()));

            // Créer une image view pour afficher l'image de l'entreprise
            ImageView imageView = new ImageView();
            imageView.setFitWidth(200); // Réglage de la largeur pour l'affichage
            imageView.setPreserveRatio(true);
            // Charger et afficher l'image actuelle de l'entreprise
            Image currentImage = new Image(selectedCompany.getImageUrl());
            imageView.setImage(currentImage);

            // Créer un bouton pour sélectionner une nouvelle image
            Button selectImageButton = new Button("Select Image");
            selectImageButton.setOnAction(e -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select Image");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                File selectedFile = fileChooser.showOpenDialog(dialog.getDialogPane().getScene().getWindow());
                if (selectedFile != null) {
                    // Charger et afficher la nouvelle image sélectionnée
                    Image newImage = new Image(selectedFile.toURI().toString());
                    imageView.setImage(newImage);
                }
            });

            // Créer les labels correspondants
            Label nomLabel = new Label("Nom:");
            Label idHotelLabel = new Label("ID Hotel:");
            Label codeLabel = new Label("Code:");
            Label paysLabel = new Label("Pays:");
            Label adresseLabel = new Label("Adresse:");
            Label telephoneLabel = new Label("Téléphone:");
            Label destinationLabel = new Label("Destination:");
            Label periodeLabel = new Label("Période:");
            Label prixLabel = new Label("Prix:");

            // Ajouter les champs de texte et les labels au dialogue
            GridPane grid = new GridPane();
            grid.add(nomLabel, 0, 0);
            grid.add(nomField, 1, 0);
            grid.add(idHotelLabel, 0, 1);
            grid.add(idHotelField, 1, 1);
            grid.add(codeLabel, 0, 2);
            grid.add(codeField, 1, 2);
            grid.add(paysLabel, 0, 3);
            grid.add(paysField, 1, 3);
            grid.add(adresseLabel, 0, 4);
            grid.add(adresseField, 1, 4);
            grid.add(telephoneLabel, 0, 5);
            grid.add(telephoneField, 1, 5);
            grid.add(destinationLabel, 0, 6);
            grid.add(destinationField, 1, 6);
            grid.add(periodeLabel, 0, 7);
            grid.add(periodeField, 1, 7);
            grid.add(prixLabel, 0, 8);
            grid.add(prixField, 1, 8);
            grid.add(imageView, 0, 9); // Ajouter l'image view à la grille
            grid.add(selectImageButton, 1, 9); // Ajouter le bouton de sélection d'image à la grille
            dialog.getDialogPane().setContent(grid);

            // Ajouter les boutons de confirmation et d'annulation au dialogue
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Attendre que l'utilisateur confirme ou annule
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {
                    // Mettre à jour l'entreprise avec les nouvelles valeurs
                    selectedCompany.setNom(nomField.getText());
                    selectedCompany.setIdHotel(Integer.parseInt(idHotelField.getText()));
                    selectedCompany.setCode(codeField.getText());
                    selectedCompany.setPays(paysField.getText());
                    selectedCompany.setAdresse(adresseField.getText());
                    selectedCompany.setTelephone(telephoneField.getText());
                    selectedCompany.setDestination(destinationField.getText());
                    selectedCompany.setPeriode(periodeField.getText());
                    selectedCompany.setPrix(Double.parseDouble(prixField.getText()));
                    // Mettre à jour l'URL de l'image avec l'URL de la nouvelle image sélectionnée
                    selectedCompany.setImageUrl(imageView.getImage().getUrl());
                    // Mettre à jour l'entreprise dans la base de données
                    updateCompany(selectedCompany);
                }
            });
        } else {
            showAlert("No Selection", "No Company Selected", "Please select a company to update.");
        }
    }

    @FXML
    private void deleteButton() {
        // Obtenez l'élément sélectionné dans la table
        Company selectedCompany = TableView.getSelectionModel().getSelectedItem();

        if (selectedCompany != null) {
            // Afficher une boîte de dialogue de confirmation de suppression
            Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer l'entreprise sélectionnée ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer l'entreprise sélectionnée ?");
            
            // Attendre la réponse de l'utilisateur
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Supprimer l'entreprise sélectionnée de la base de données
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agence", "root", "");
                        String query = "DELETE FROM  compagny WHERE id = ?";
                        PreparedStatement statement = conn.prepareStatement(query);
                        statement.setInt(1, selectedCompany.getId());
                        int rowsDeleted = statement.executeUpdate();
                        conn.close();
                        if (rowsDeleted > 0) {
                            // Si la suppression réussit, supprimez également l'élément de la table
                            TableView.getItems().remove(selectedCompany);
                            showAlert("Suppression réussie", "Entreprise supprimée avec succès", "L'entreprise a été supprimée de la base de données et de la liste.");
                        } else {
                            showAlert("Erreur de suppression", "Échec de la suppression", "Impossible de supprimer l'entreprise de la base de données.");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Erreur de base de données", "Erreur de suppression", "Une erreur s'est produite lors de la suppression de l'entreprise de la base de données.");
                    }
                }
            });
        } else {
            showAlert("Aucune sélection", "Aucune entreprise sélectionnée", "Veuillez sélectionner une entreprise à supprimer.");
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    
}

    // Ajoutez les autres méthodes de votre contrôleur ici


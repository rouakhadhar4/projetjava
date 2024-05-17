package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ajoutcompany {

  

    @FXML
    private Button btnChooseImage;

    @FXML
    private TextField nom;

    @FXML
    private TextField adresse;

    @FXML
    private TextField pays;

    @FXML
    private TextField code;

    @FXML
    private TextField telephone;

    @FXML
    private TextField destination;

    @FXML
    private TextField periode;

    @FXML
    private TextField prix;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private Label Labelinfos;

    @FXML
    private ComboBox<String> idHotelComboBox;

    @FXML
    private ImageView imageView;

    @FXML
    private void onBtnChooseImageAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Débogage : Affiche le chemin du fichier sélectionné
            System.out.println(selectedFile.toURI().toString());
            
            // Mettez à jour l'image de l'imageView avec l'image sélectionnée
            imageView.setImage(new Image(selectedFile.toURI().toString()));
        }
    }
    @FXML
    private void onBtnCancelAction() {
        // Réinitialisez tous les champs texte à vide
        
    }

    @FXML
    void onBtnSavecAction() {
        // Récupérez les valeurs des champs de l'interface utilisateur
        String nomValue = nom.getText();
        String idHotelValue = idHotelComboBox.getValue(); // Assurez-vous que idHotelComboBox contient les ID des hôtels
        String codeValue = code.getText();
        String paysValue = pays.getText();
        String adresseValue = adresse.getText();
        String telephoneValue = telephone.getText();
        String destinationValue = destination.getText();
        String periodeValue = periode.getText();
        String prixValue = prix.getText();

        // Obtenez l'URL de l'image depuis l'ImageView
        String imageUrl = imageView.getImage().getUrl();

        try (Connection conn = MySQLConnection.getConnection()) {
            // Requête SQL d'insertion avec le champ image_url
            String query = "INSERT INTO compagny (nom, id_hotel, codeInternational, pays, adresse, telephone, destination, periodeVole, prixVole, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                // Définissez les valeurs des paramètres de la requête d'insertion
                statement.setString(1, nomValue);
                statement.setString(2, idHotelValue);
                statement.setString(3, codeValue);
                statement.setString(4, paysValue);
                statement.setString(5, adresseValue);
                statement.setString(6, telephoneValue);
                statement.setString(7, destinationValue);
                statement.setString(8, periodeValue);
                statement.setString(9, prixValue);
                statement.setString(10, imageUrl); // Assignez l'URL de l'image au paramètre correspondant
                

                // Exécutez la requête d'insertion
                int rowsInserted = statement.executeUpdate();
                
                if (rowsInserted > 0) {
                    // L'insertion a réussi, redirigez vers la liste des compagnies
                    redirectToCompanyList();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void redirectToCompanyList() {
        try {
            // Chargez la vue listecompagny.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listecompagny.fxml"));
            Parent root = loader.load();
            
            // Obtenez la scène actuelle à partir du bouton 'Save' ou d'un autre nœud de l'interface utilisateur
            Scene currentScene = btnSave.getScene();
            
            // Définissez la scène avec la nouvelle vue chargée
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void initialize() {
        // Initialisez la liste des ID d'hôtels dans le ComboBox
        initHotelIdComboBox();

        // Ajoutez un écouteur pour détecter les changements de sélection dans le ComboBox
        idHotelComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Chargez l'image de l'hôtel correspondant à l'ID sélectionné
                loadHotelImage(newValue);
            }
        });
    }
    private void loadHotelImage(String hotelId) {
        try (Connection conn = MySQLConnection.getConnection()) {
            // Requête SQL pour récupérer l'URL de l'image de l'hôtel en fonction de l'ID
            String query = "SELECT image_url FROM hotel WHERE id_hotel = ?";
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.setString(1, hotelId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Charger l'image depuis l'URL et l'afficher dans l'ImageView
                        String imageUrl = resultSet.getString("image_url");
                        Image image = new Image(imageUrl);
                        imageView.setImage(image);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initHotelIdComboBox() {
        try (Connection conn = MySQLConnection.getConnection()) {
            String query = "SELECT id_hotel FROM hotel";
            try (PreparedStatement statement = conn.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                ObservableList<String> hotelIds = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    hotelIds.add(resultSet.getString("id_hotel"));
                }
                idHotelComboBox.setItems(hotelIds);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
}

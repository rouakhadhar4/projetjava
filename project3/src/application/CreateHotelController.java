package application;




import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateHotelController {
    @FXML
    private TextField nom;

    @FXML
    private TextField adresse;

    @FXML
    private TextField ville;

    @FXML
    private TextField pays;
    @FXML
    private Button btnChooseImage;

    // Méthode appelée lorsque le bouton est cliqué
 

    @FXML
    private TextField etoiles;

    @FXML
    private TextField description;

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    private void initialize() {
       
    }


    @FXML
    private void onBtnSaveAction() throws IOException {
        try {
            String nom = this.nom.getText();
            String adresse = this.adresse.getText();
            String ville = this.ville.getText();
            String pays = this.pays.getText();
            int etoiles = Integer.parseInt(this.etoiles.getText());
            String description = this.description.getText();

            // Obtenez l'URL de l'image à partir de l'imageView
            String image_url = imageView.getImage().getUrl();

            Hotel hotel = new Hotel();
            hotel.setNom(nom);
            hotel.setAdresse(adresse);
            hotel.setVille(ville);
            hotel.setPays(pays);
            hotel.setEtoiles(etoiles);
            hotel.setDescription(description);

            // Définir l'URL de l'image pour l'objet Hotel
            hotel.setImageUrl(image_url);

            // Enregistrez l'objet Hotel dans la base de données
            saveHotel(hotel);

            // Charger la vue de la page de liste
            FXMLLoader loader = new FXMLLoader(getClass().getResource("list.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la liste et mettre à jour la liste des hôtels
            List listController = loader.getController();
            listController.loadHotels(); // Assurez-vous que la méthode loadHotels est publique

            // Créer une nouvelle scène avec la vue de la page de liste
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton "Save"
            Stage stage = (Stage) btnSave.getScene().getWindow();

            // Définir la nouvelle scène comme scène principale
            stage.setScene(scene);
            stage.show();
        } catch (NumberFormatException e) {
            // Gérez l'exception si la conversion en entier échoue
            e.printStackTrace();
        }
    }

  


    @FXML
    private void onBtnCancelAction() {
        // Réinitialisez tous les champs texte à vide
        nom.setText("");
        adresse.setText("");
        ville.setText("");
        pays.setText("");
        etoiles.setText("");
        description.setText("");
        // Ne réinitialisez pas l'image de l'imageView ici, laissez-la telle quelle
    }

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

   


    private void saveHotel(Hotel hotel) {
        try {
            Connection conn = MySQLConnection.getConnection();
            String query = "INSERT INTO hotel (nom, adresse, ville, pays, etoiles, description, image_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, hotel.getNom());
            statement.setString(2, hotel.getAdresse());
            statement.setString(3, hotel.getVille());
            statement.setString(4, hotel.getPays());
            statement.setInt(5, hotel.getEtoiles());
            statement.setString(6, hotel.getDescription());
            statement.setString(7, hotel.getImageUrl());
            statement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            // Gérer l'exception SQL ici
            e.printStackTrace();
        }
    }

}

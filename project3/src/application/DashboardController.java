package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label dashboardLabel;

    // Cette méthode sera appelée automatiquement après le chargement du fichier FXML
    public void initialize() {
        dashboardLabel.setText("Bienvenue sur le Tableau de Bord !");
    }

    // Méthode pour gérer l'événement de gestion des voyages
    @FXML
    private void handleManageTrips() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("list.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    }

    // Méthode pour gérer l'événement de gestion des réservations
    @FXML
    private void handleManageBookings() {
    	try {
    	    // Charger le fichier FXML correspondant
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("listecompagny.fxml"));
    	    Parent root = loader.load();
    	    root.getStylesheets().add(getClass().getResource("styles1.css").toExternalForm());
    	    
    	    // Obtenir la référence de la scène actuelle
    	    Scene currentScene = dashboardLabel.getScene();
    	    
    	    // Remplacer le contenu de la scène actuelle par le nouveau contenu
    	    currentScene.setRoot(root);
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    
        System.out.println("Gestion des compagnie");
    }
}


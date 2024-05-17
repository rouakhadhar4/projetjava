package application;
	
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	 public void start(Stage primaryStage) {
	        try {
	            // Connexion à la base de données
	            Connection conn = MySQLConnection.getConnection();

	            // Récupération du nom de la base de données
	            String databaseName = getDatabaseName(conn);
	            System.out.println("Connexion à la base de données " + databaseName + " établie.");

	            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
	            primaryStage.setTitle("Application de Gestion d'Agence de Voyage");
	            primaryStage.setScene(new Scene(root, 600, 400));
	            primaryStage.show();

	            
	        } catch (SQLException e) {
	            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private String getDatabaseName(Connection conn) throws SQLException {
	        Statement statement = conn.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT DATABASE()");
	        if (resultSet.next()) {
	            return resultSet.getString(1);
	        } else {
	            throw new SQLException("Impossible de récupérer le nom de la base de données.");
	        }
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
}
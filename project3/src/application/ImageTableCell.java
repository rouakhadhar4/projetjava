package application;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<S> extends TableCell<S, String> {
    private final ImageView imageView;

    public ImageTableCell() {
        this.imageView = new ImageView();
        this.imageView.setFitWidth(100); // Définir la largeur de l'image
        this.imageView.setFitHeight(100); // Définir la hauteur de l'image
    }

    @Override
    protected void updateItem(String imageUrl, boolean empty) {
        super.updateItem(imageUrl, empty);

        if (empty || imageUrl == null || imageUrl.isEmpty()) {
            setGraphic(null);
        } else {
            try {
                // Load and display the image from the URL
                Image image = new Image(imageUrl);
                imageView.setImage(image);
                setGraphic(imageView);
            } catch (Exception e) {
                // Handle invalid URL or resource not found
                System.err.println("Error loading image from URL: " + imageUrl);
                e.printStackTrace();
                // You can optionally set a placeholder image or display an error icon
                // Example:
                // Image errorImage = new Image(getClass().getResourceAsStream("/path/to/error.png"));
                // imageView.setImage(errorImage);
                // setGraphic(imageView);
            }
        }
    }


}


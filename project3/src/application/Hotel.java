package application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hotel {
	private int id_hotel;
    private String nom;
    private String adresse;
    private String ville;
    private String pays;
    private int etoiles;
    private String description;
    private String image_url; // Champ pour stocker l'URL de l'image

    // Constructeur par défaut
    public Hotel() {
       
    }

    // Constructeur avec des paramètres
    public Hotel(String nom, String adresse, String ville, String pays, int etoiles, String description, String image_url) {
        this.nom = nom;
        this.adresse = adresse;
        this.ville = ville;
        this.pays = pays;
        this.etoiles = etoiles;
        this.description = description;
        this.image_url = image_url;
    }

    // Getters et setters pour tous les champs
    public int getId() {
        return id_hotel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getEtoiles() {
        return etoiles;
    }

    public void setEtoiles(int etoiles) {
        this.etoiles = etoiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String imageUrl) {
        this.image_url = imageUrl;
    }
    
    

    // Méthode toString() pour afficher les informations de l'hôtel
    @Override
    public String toString() {
        return "Hotel [nom=" + nom + ", adresse=" + adresse + ", ville=" + ville + ", pays=" + pays + ", etoiles="
                + etoiles + ", description=" + description + "]";
    }
    public void setId(int id_hotel) {
        this.id_hotel = id_hotel;
    }
}

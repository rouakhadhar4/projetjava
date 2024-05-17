package application;



public class Company {
    private int id;
    private String nom;
    private int idHotel;
    private String code;
    private String pays;
    private String adresse;
    private String telephone;
    private String destination;
    private String periode;
    private double prix;
    private String image;

    // Constructeur
    public Company(int id, String nom, int idHotel, String code, String pays, String adresse, String telephone, String destination, String periode, double prix, String image) {
        this.id = id;
        this.nom = nom;
        this.idHotel = idHotel;
        this.code = code;
        this.pays = pays;
        this.adresse = adresse;
        this.telephone = telephone;
        this.destination = destination;
        this.periode = periode;
        this.prix = prix;
        this.image = image;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }
}

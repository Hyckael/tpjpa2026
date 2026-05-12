package dto;

public class ArtistDto {
    private Long id;
    private String name;
    private String genre;
    private String biography;
    private String country;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
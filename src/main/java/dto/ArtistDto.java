package dto;

public class ArtistDto {
    private String name;
    private String genre;
    private String biography;
    private String country;
    private Long eventId; // ← lier à un event

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getBiography() { return biography; }
    public void setBiography(String biography) { this.biography = biography; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
}
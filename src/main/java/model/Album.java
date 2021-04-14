package model;

public class Album {
    private String title;
    private String nameArtist;
    private String releaseDate;
    private int totalTracks;
    private String urlSpotify;

    public Album(String name, String nameArtist, String releaseDate, int totalTracks, String urlSpotify) {
        this.title = name;
        this.nameArtist = nameArtist;
        this.releaseDate = releaseDate;
        this.totalTracks = totalTracks;
        this.urlSpotify = urlSpotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getTotalTracks() {
        return totalTracks;
    }

    @Override
    public String toString() {
        return "Название релиза: " + this.title + "\n" +
                "Артист(ы): " + this.nameArtist + "\n" +
                "Дата релиза: " + this.releaseDate + "\n" +
                "Кол-во треков: " + this.totalTracks + "\n" +
                this.urlSpotify;
    }

    public void setTotalTracks(int totalTracks) {
        this.totalTracks = totalTracks;
    }

    public String getUrlSpotify() {
        return urlSpotify;
    }

    public void setUrlSpotify(String urlSpotify) {
        this.urlSpotify = urlSpotify;
    }
}
